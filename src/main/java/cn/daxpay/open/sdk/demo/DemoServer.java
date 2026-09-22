package cn.daxpay.open.sdk.demo;

import cn.daxpay.open.sdk.net.DaxPayClient;
import cn.daxpay.open.sdk.net.DaxPayConfig;
import cn.daxpay.open.sdk.net.DaxPayObserver;
import cn.daxpay.open.sdk.param.AllocParam;
import cn.daxpay.open.sdk.param.AllocQueryParam;
import cn.daxpay.open.sdk.param.AllocSyncParam;
import cn.daxpay.open.sdk.param.CloseParam;
import cn.daxpay.open.sdk.param.GatewayOrderQueryParam;
import cn.daxpay.open.sdk.param.GatewayPrePayParam;
import cn.daxpay.open.sdk.param.PayParam;
import cn.daxpay.open.sdk.param.PayQueryParam;
import cn.daxpay.open.sdk.param.PaySyncParam;
import cn.daxpay.open.sdk.param.PingParam;
import cn.daxpay.open.sdk.param.RefundParam;
import cn.daxpay.open.sdk.param.RefundQueryParam;
import cn.daxpay.open.sdk.param.RefundSyncParam;
import cn.daxpay.open.sdk.param.TransferParam;
import cn.daxpay.open.sdk.param.TransferQueryParam;
import cn.daxpay.open.sdk.param.TransferSyncParam;
import cn.daxpay.open.sdk.response.DaxResult;
import cn.daxpay.open.sdk.result.PingResult;
import cn.daxpay.open.sdk.util.PaySignUtil;
import cn.daxpay.open.sdk.util.RsaSignUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/// # SDK 联调 Demo 服务
///
/// 单命令启动的本地联调工具，**所有交易调用都经 [DaxPayClient] 走 SDK 真实调用链**（签名/请求/验签），
/// 同时验证 SDK 与平台 unipay 接口两侧：
///
/// - `GET /` 内嵌调试页（resources/demo/index.html）
/// - `GET|POST /demo/config` 连接配置：页面弹窗内直接填写服务地址/商户号/密钥，
///   配置保存在**浏览器 localStorage**（服务端只存内存、不落盘），页面配置优先于启动时的配置文件
/// - `POST /demo/ping` 连通性自检：服务端代调 `GET /unipay/callback/ping` 探针（浏览器直连平台会跨域）
/// - `POST /demo/signed-ping` 签名链路自检：服务端代调 `POST /unipay/ping` 签名探针（「测试连接」第二段，
///   判定当前配置的商户号/应用/商户私钥是否正确、能否发起真实调用）
/// - `POST /demo/{action}` 调 SDK 发起真实请求，回显「签名后请求体 + 平台原始响应 + 响应验签结果」；
///   支持全部 15 个开放接口，action 取值见 [DemoServer#ACTIONS]
/// - `POST /callback/{pay|refund|alloc|transfer}`（及通用 `/callback`）接收平台异步通知，用平台公钥验签后暂存
/// - `GET /demo/callbacks` 回调记录（页面轮询）；`POST /demo/callbacks/clear` 清空
///
/// 启动（仓根执行，配置文件与密钥均**可选**）：
/// `mvn compile exec:java -Dexec.mainClass=cn.daxpay.open.sdk.demo.DemoServer`
///
/// 可选命令行参数：`--port=9799` 监听端口；`--config=路径` 指定初始配置文件
/// （默认查找 `config.local.json` / `examples/config.local.json`，都不存在则完全依赖页面内配置）。
///
/// HTTP 层直接用 JDK 内置 HttpServer，不给 SDK 引入任何 Web 框架依赖；
/// 每次交易调用创建带独立 observer 的 client 实例，无线程共享状态。
public class DemoServer {

    /// 回调记录保留上限（超出丢弃最老记录）
    private static final int MAX_CALLBACKS = 200;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /// action → SDK 调用映射表（15 个业务接口 + 签名自检探针），新增接口只需在此登记一行
    private static final Map<String, BiConsumer<DaxPayClient, JSONObject>> ACTIONS = new LinkedHashMap<>();

    static {
        // 支付族
        ACTIONS.put("pay", (client, p) -> client.pay(p.toBean(PayParam.class)));
        ACTIONS.put("close", (client, p) -> client.close(p.toBean(CloseParam.class)));
        ACTIONS.put("query-pay-order", (client, p) -> client.queryPayOrder(p.toBean(PayQueryParam.class)));
        ACTIONS.put("sync-pay-order", (client, p) -> client.syncPayOrder(p.toBean(PaySyncParam.class)));
        // 退款族
        ACTIONS.put("refund", (client, p) -> client.refund(p.toBean(RefundParam.class)));
        ACTIONS.put("query-refund-order", (client, p) -> client.queryRefundOrder(p.toBean(RefundQueryParam.class)));
        ACTIONS.put("sync-refund-order", (client, p) -> client.syncRefundOrder(p.toBean(RefundSyncParam.class)));
        // 转账族
        ACTIONS.put("transfer", (client, p) -> client.transfer(p.toBean(TransferParam.class)));
        ACTIONS.put("query-transfer-order", (client, p) -> client.queryTransferOrder(p.toBean(TransferQueryParam.class)));
        ACTIONS.put("sync-transfer-order", (client, p) -> client.syncTransferOrder(p.toBean(TransferSyncParam.class)));
        // 分账族
        ACTIONS.put("alloc", (client, p) -> client.alloc(p.toBean(AllocParam.class)));
        ACTIONS.put("query-alloc-order", (client, p) -> client.queryAllocOrder(p.toBean(AllocQueryParam.class)));
        ACTIONS.put("sync-alloc-order", (client, p) -> client.syncAllocOrder(p.toBean(AllocSyncParam.class)));
        // 网关族
        ACTIONS.put("gateway-pre-pay", (client, p) -> client.gatewayPrePay(p.toBean(GatewayPrePayParam.class)));
        ACTIONS.put("gateway-query", (client, p) -> client.gatewayQuery(p.toBean(GatewayOrderQueryParam.class)));
        // 自检族：探针非 0 码在此转成异常，与其它接口的失败回显行为一致（完整诊断走 /demo/signed-ping）
        ACTIONS.put("signed-ping", (client, p) -> {
            DaxResult<PingResult> r = client.signedPing(p.toBean(PingParam.class));
            if (r.getCode() != 0) {
                throw new RuntimeException("[" + r.getCode() + "] " + r.getMsg());
            }
        });
    }

    /// 当前生效配置（页面保存时整体替换引用，volatile 保证多线程可见性）
    private volatile DaxPayConfig daxConfig;
    /// 对外可达的回调基址（生成默认 notifyUrl 用，跨机联调时显式配置）
    private String callbackBase;

    private final ConcurrentLinkedDeque<CallbackRecord> callbacks = new ConcurrentLinkedDeque<>();

    public static void main(String[] args) throws IOException {
        new DemoServer().start(args);
    }

    /// 启动：配置文件与密钥均为可选（页面内配置为推荐方式），边界宽松、不阻断启动
    private void start(String[] args) throws IOException {
        int port = 9799;
        for (String arg : args) {
            if (arg.startsWith("--port=")) {
                port = Integer.parseInt(arg.substring("--port=".length()));
            }
        }
        // 配置文件可选：存在则作为初始值，缺失则完全依赖页面内配置
        File configFile = resolveConfigFile(args);
        JSONObject conf = new JSONObject();
        File configDir = new File(".");
        if (configFile != null) {
            conf = JSONUtil.parseObj(FileUtil.readUtf8String(configFile));
            configDir = configFile.getParentFile() != null ? configFile.getParentFile() : configDir;
            System.out.println("已加载初始配置: " + configFile.getPath());
        } else {
            System.out.println("未找到配置文件，请打开页面在「连接配置」中填写参数（保存在浏览器本地，不落盘）");
        }
        if (conf.containsKey("port")) {
            port = conf.getInt("port");
        }
        this.daxConfig = buildConfig(conf, configDir);
        this.callbackBase = conf.getStr("callbackBase", "http://127.0.0.1:" + port);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/", this::dispatch);
        server.start();

        System.out.println("DaxPay Java SDK 联调 Demo 已启动");
        System.out.println("  调试页面 : http://127.0.0.1:" + port);
        System.out.println("  平台地址 : " + daxConfig.getServiceUrl() + "  (商户 " + daxConfig.getMchNo() + ")");
        System.out.println("  密钥状态 : 商户私钥 " + (StrUtil.isNotBlank(daxConfig.getPrivateKey()) ? "已配置" : "未配置")
                + " / 平台公钥 " + (StrUtil.isNotBlank(daxConfig.getPublicKey()) ? "已配置" : "未配置")
                + "  (可在页面「连接配置」中随时修改)");
        System.out.println("  回调基址 : " + callbackBase + "  (支付通知可填 " + callbackBase + "/callback/pay)");
    }

    /// 配置文件定位（可选）：--config= 显式指定 > 工作目录 config.local.json > examples/config.local.json；都不存在返回 null
    private File resolveConfigFile(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--config=")) {
                return new File(arg.substring("--config=".length()));
            }
        }
        File local = new File("config.local.json");
        if (local.exists()) {
            return local;
        }
        File examples = new File("examples/config.local.json");
        return examples.exists() ? examples : null;
    }

    /// 由配置对象构建 DaxPayConfig（密钥缺失不报错，仅提示，页面内可随时补齐）
    private DaxPayConfig buildConfig(JSONObject conf, File configDir) {
        return new DaxPayConfig()
                .setServiceUrl(StrUtil.blankToDefault(conf.getStr("serviceUrl"), "http://127.0.0.1:9999"))
                .setMchNo(conf.getStr("mchNo"))
                .setAppId(conf.getStr("appId"))
                .setPrivateKey(readKey(conf, configDir, "privateKey", "privateKeyPath"))
                .setPublicKey(readKey(conf, configDir, "publicKey", "publicKeyPath"));
    }

    /// 密钥读取：内嵌字符串优先，其次相对配置目录的 PEM 文件路径；缺失或读不到返回 null（不阻断启动）
    private String readKey(JSONObject conf, File configDir, String inlineKey, String pathKey) {
        String inline = conf.getStr(inlineKey);
        if (StrUtil.isNotBlank(inline)) {
            return inline;
        }
        String path = conf.getStr(pathKey);
        if (StrUtil.isNotBlank(path)) {
            File file = new File(configDir, path);
            if (file.exists()) {
                return FileUtil.readUtf8String(file);
            }
            System.out.println("  提示: 密钥文件不存在 " + file.getPath() + "，可在页面「连接配置」中补充");
        }
        return null;
    }

    // ==================================================================
    // HTTP 分发
    // ==================================================================

    private void dispatch(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        try {
            // 调试页与静态资源
            if ("GET".equals(method) && ("/".equals(path) || "/index.html".equals(path))) {
                sendResource(exchange, "/demo/index.html", "text/html; charset=utf-8");
                return;
            }
            // 连接配置：GET 读取脱敏状态 / POST 页面保存（配置存浏览器，服务端仅内存）
            if ("/demo/config".equals(path) && ("GET".equals(method) || "POST".equals(method))) {
                handleConfig(exchange, method);
                return;
            }
            // 回调记录（须在 /demo/* 交易路由之前匹配，避免被当作交易 action）
            if ("GET".equals(method) && "/demo/callbacks".equals(path)) {
                java.util.List<Map<String, Object>> list = new java.util.ArrayList<>();
                for (CallbackRecord record : callbacks) {
                    list.add(record.toMap());
                }
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("count", list.size());
                body.put("records", list);
                sendJson(exchange, 200, new JSONObject(body));
                return;
            }
            if ("POST".equals(method) && "/demo/callbacks/clear".equals(path)) {
                callbacks.clear();
                sendJson(exchange, 200, new JSONObject("{\"ok\":true}"));
                return;
            }
            // 连通性自检：服务端代调平台探针（浏览器直连平台地址会跨域，故由本服务中转）
            if ("POST".equals(method) && "/demo/ping".equals(path)) {
                handlePing(exchange);
                return;
            }
            // 签名链路自检：服务端代调签名自检探针 POST /unipay/ping（「测试连接」第二段）
            if ("POST".equals(method) && "/demo/signed-ping".equals(path)) {
                handleSignedPing(exchange);
                return;
            }
            // 交易调试（经 SDK 真实调用链）
            if ("POST".equals(method) && path.startsWith("/demo/")) {
                handleTrade(exchange, path.substring("/demo/".length()));
                return;
            }
            // 平台异步通知接收端点（返回固定 SUCCESS，平台要求 HTTP 2xx 且 body 为 SUCCESS）
            if ("POST".equals(method) && path.startsWith("/callback/")) {
                handleCallback(exchange, path.substring("/callback/".length()));
                return;
            }
            sendJson(exchange, 404, new JSONObject("{\"error\":\"not found: " + path + "\"}"));
        } catch (Exception e) {
            try {
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("error", String.valueOf(e.getMessage()));
                sendJson(exchange, 500, new JSONObject(body));
            } catch (IOException ignored) {
                // 响应已提交，无法回写
            }
        }
    }

    // ==================================================================
    // /demo/config 连接配置（页面内配置，服务端只存内存不落盘）
    // ==================================================================

    private void handleConfig(HttpExchange exchange, String method) throws IOException {
        if ("GET".equals(method)) {
            sendJson(exchange, 200, configInfo());
            return;
        }
        JSONObject body = JSONUtil.parseObj(readBody(exchange));
        String serviceUrl = StrUtil.trimToEmpty(body.getStr("serviceUrl"));
        String mchNo = StrUtil.trimToEmpty(body.getStr("mchNo"));
        if (serviceUrl.isEmpty() || mchNo.isEmpty()) {
            sendJson(exchange, 400, new JSONObject().set("error", "服务地址与商户号不能为空"));
            return;
        }
        // 密钥字段语义：缺省=保持原值；空串=清空；非空=替换（先做 PEM 解析校验，即时反馈格式错误）
        DaxPayConfig current = daxConfig;
        String privateKey = current.getPrivateKey();
        if (body.containsKey("privateKey")) {
            String value = StrUtil.trimToEmpty(body.getStr("privateKey"));
            if (value.isEmpty()) {
                privateKey = null;
            } else {
                try {
                    RsaSignUtil.loadPrivateKeyFromPem(value);
                } catch (Exception e) {
                    sendJson(exchange, 400, new JSONObject().set("error", "商户私钥无效: " + e.getMessage()));
                    return;
                }
                privateKey = value;
            }
        }
        String publicKey = current.getPublicKey();
        if (body.containsKey("publicKey")) {
            String value = StrUtil.trimToEmpty(body.getStr("publicKey"));
            if (value.isEmpty()) {
                publicKey = null;
            } else {
                try {
                    RsaSignUtil.loadPublicKeyFromPem(value);
                } catch (Exception e) {
                    sendJson(exchange, 400, new JSONObject().set("error", "平台公钥无效: " + e.getMessage()));
                    return;
                }
                publicKey = value;
            }
        }
        // 整体替换配置引用（volatile 写，读方每次取最新快照）
        this.daxConfig = new DaxPayConfig()
                .setServiceUrl(serviceUrl)
                .setMchNo(mchNo)
                .setAppId(StrUtil.trimToNull(body.getStr("appId")))
                .setPrivateKey(privateKey)
                .setPublicKey(publicKey);
        System.out.println("[配置] 页面更新连接配置: " + serviceUrl + " 商户 " + mchNo);
        sendJson(exchange, 200, configInfo());
    }

    /// 当前配置的脱敏状态（不返回密钥内容，仅返回是否已配置）
    private JSONObject configInfo() {
        DaxPayConfig cfg = daxConfig;
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("serviceUrl", cfg.getServiceUrl());
        info.put("mchNo", cfg.getMchNo());
        info.put("appId", cfg.getAppId());
        info.put("callbackBase", callbackBase);
        info.put("privateKeySet", StrUtil.isNotBlank(cfg.getPrivateKey()));
        info.put("publicKeySet", StrUtil.isNotBlank(cfg.getPublicKey()));
        return new JSONObject(info);
    }

    // ==================================================================
    // /demo/ping 连通性自检（服务端中转，规避浏览器跨域）
    // ==================================================================

    /// 代调平台自检探针 `GET /unipay/callback/ping`，供页面「测试连接」按钮使用
    private void handlePing(HttpExchange exchange) throws IOException {
        DaxPayConfig cfg = daxConfig;
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("serviceUrl", cfg.getServiceUrl());
        long begin = System.currentTimeMillis();
        try {
            String body = new DaxPayClient(cfg).ping();
            result.put("success", true);
            result.put("marker", body);
            result.put("durationMs", System.currentTimeMillis() - begin);
        } catch (Exception e) {
            result.put("success", false);
            String msg = String.valueOf(e.getMessage());
            // 401/404 是探针链路上最常见的两种情况，直接给出可操作的排查方向
            if (msg.contains("HTTP 401") || msg.contains("HTTP 404")) {
                msg += "（需平台版本包含部署自检探针 /unipay/callback/ping，且网关放行该前缀）";
            }
            result.put("error", msg);
            result.put("durationMs", System.currentTimeMillis() - begin);
        }
        sendJson(exchange, 200, new JSONObject(result));
    }

    // ==================================================================
    // /demo/signed-ping 签名链路自检（服务端中转，规避浏览器跨域）
    // ==================================================================

    /// 代调签名自检探针 `POST /unipay/ping`，供页面「测试连接」第二段使用：
    /// 判定当前配置的商户号/应用/商户私钥/签名串构造是否正确、能否发起真实调用
    private void handleSignedPing(HttpExchange exchange) throws IOException {
        DaxPayConfig cfg = daxConfig;
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("serviceUrl", cfg.getServiceUrl());
        long begin = System.currentTimeMillis();
        if (StrUtil.isBlank(cfg.getPrivateKey()) || StrUtil.isBlank(cfg.getPublicKey())) {
            result.put("success", false);
            result.put("hint", StrUtil.isBlank(cfg.getPrivateKey())
                    ? "尚未配置商户私钥，请先在「连接配置」中填写"
                    : "尚未配置平台公钥（响应无法验签），请先在「连接配置」中填写");
            sendJson(exchange, 200, new JSONObject(result));
            return;
        }
        // observer 捕获发出报文与原始响应，供页面比对签名串（发出 JSON vs 服务端待签串）
        final String[] captured = new String[2];
        DaxPayClient client = new DaxPayClient(cfg).setObserver(new DaxPayObserver() {
            @Override
            public void onRequest(String signedJson) {
                captured[0] = signedJson;
            }

            @Override
            public void onResponse(String rawBody) {
                captured[1] = rawBody;
            }
        });
        try {
            DaxResult<PingResult> r = client.signedPing(new PingParam());
            result.put("success", r.getCode() == 0);
            result.put("code", r.getCode());
            result.put("msg", r.getMsg());
            result.put("data", Objects.isNull(r.getData()) ? null : JSONUtil.parseObj(JSONUtil.toJsonStr(r.getData())));
            if (r.getCode() != 0) {
                result.put("hint", classifyProbeError(r.getCode()));
            }
        } catch (Exception e) {
            // 走到异常只会是硬错误：网络不通 / HTTP 非 200 / 响应验签失败（平台公钥问题）
            String msg = String.valueOf(e.getMessage());
            result.put("success", false);
            result.put("error", msg);
            if (msg.contains("响应验签失败")) {
                result.put("hint", "平台响应验签失败：请核对「连接配置」中的平台公钥");
            } else if (msg.contains("HTTP 404")) {
                result.put("hint", "网关未放行「商户开放 API」(/unipay) 接口组，需在部署面板开启");
            }
        } finally {
            result.put("requestBody", captured[0]);
            result.put("responseBody", captured[1]);
            result.put("durationMs", System.currentTimeMillis() - begin);
        }
        sendJson(exchange, 200, new JSONObject(result));
    }

    /// 探针错误码分类提示（对照契约 6.14 诊断表）
    private String classifyProbeError(int code) {
        if (code == 20052) {
            return "验签失败：商户私钥与平台上配置的公钥不配对，或签名串构造不一致——比对「发出报文」与响应 msg 中的服务端待签串";
        }
        if (code == 10408 || code == 10409) {
            return "Nonce 防重放拦截：请勿复用请求（每次点击都会生成新 nonce）";
        }
        if (code == 10410 || code == 10411) {
            return "请求时间超窗：本机时钟偏差过大，或 reqTime 未按 GMT+8 yyyy-MM-dd HH:mm:ss 字面量";
        }
        return "商户号/应用类错误（code " + code + "）：核对 mchNo 与 appId 是否存在且启用";
    }

    // ==================================================================
    // /demo/* 交易调试
    // ==================================================================

    private void handleTrade(HttpExchange exchange, String action) throws IOException {
        String reqBody = readBody(exchange);
        JSONObject paramJson = JSONUtil.parseObj(reqBody);

        // 配置快照（一次读取，保证单次调用内一致）
        DaxPayConfig cfg = daxConfig;
        if (StrUtil.isBlank(cfg.getPrivateKey())) {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("success", false);
            result.put("requestBody", null);
            result.put("responseBody", null);
            result.put("durationMs", 0);
            result.put("signVerified", null);
            result.put("result", null);
            result.put("error", "尚未配置商户私钥，请点击右上角「连接配置」填写后重试");
            sendJson(exchange, 200, new JSONObject(result));
            return;
        }

        // observer 捕获本次调用的请求体/响应体（每次调用独立实例，线程安全）
        final String[] captured = new String[2];
        DaxPayObserver observer = new DaxPayObserver() {
            @Override
            public void onRequest(String signedJson) {
                captured[0] = signedJson;
            }

            @Override
            public void onResponse(String rawBody) {
                captured[1] = rawBody;
            }
        };
        DaxPayClient client = new DaxPayClient(cfg).setObserver(observer);

        long begin = System.currentTimeMillis();
        String error = null;
        BiConsumer<DaxPayClient, JSONObject> action0 = ACTIONS.get(action);
        if (action0 == null) {
            sendJson(exchange, 404, new JSONObject("{\"error\":\"unknown action: " + action + "\"}"));
            return;
        }
        try {
            action0.accept(client, paramJson);
        } catch (RuntimeException e) {
            // SDK 抛出（业务失败/验签失败/网络异常）也属联调有效结果，回显给页面
            error = e.getMessage();
        }
        long durationMs = System.currentTimeMillis() - begin;

        // 组装统一回显结构：SDK 实际发出的签名请求 + 平台原始响应 + 解析结果 + 验签
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", error == null);
        result.put("requestBody", captured[0]);
        result.put("responseBody", captured[1]);
        result.put("durationMs", durationMs);
        result.put("signVerified", verifyResponse(captured[1], cfg));
        result.put("result", parseResult(captured[1]));
        result.put("error", error);
        sendJson(exchange, 200, new JSONObject(result));
    }

    /// 响应验签（demo 层独立复核，便于对照 SDK 内部验签行为）
    ///
    /// 返回 null 表示响应不带签名——平台自 2026-09-21 起失败响应也已带签名，
    /// 无签名只会出现在旧版平台（全局异常处理器返回 Result 形状）或切面之前的报文解析失败，
    /// 页面据此显示"未签名"而非"验签失败"。
    private Boolean verifyResponse(String responseBody, DaxPayConfig cfg) {
        if (responseBody == null || responseBody.isEmpty()) {
            return null;
        }
        try {
            if (StrUtil.isBlank(JSONUtil.parseObj(responseBody).getStr("sign"))) {
                return null;
            }
            return PaySignUtil.verify(responseBody, cfg.getPublicKey());
        } catch (Exception e) {
            return false;
        }
    }

    /** 从原始响应解析 DaxResult 展示字段（业务失败时不抛异常，原样透出 code/msg） */
    private JSONObject parseResult(String responseBody) {
        if (responseBody == null || responseBody.isEmpty()) {
            return null;
        }
        try {
            return JSONUtil.parseObj(responseBody);
        } catch (Exception e) {
            return new JSONObject().set("parseError", responseBody);
        }
    }

    // ==================================================================
    // /callback/* 异步通知接收
    // ==================================================================

    private void handleCallback(HttpExchange exchange, String type) throws IOException {
        String body = readBody(exchange);
        DaxPayConfig cfg = daxConfig;
        CallbackRecord record = new CallbackRecord();
        record.time = OffsetDateTime.now(ZoneOffset.ofHours(8)).format(FMT);
        record.type = type;
        record.body = body;
        if (StrUtil.isBlank(cfg.getPublicKey())) {
            record.signVerified = false;
            record.msg = "平台公钥未配置，无法验签（请在页面「连接配置」中补充）";
        } else {
            try {
                record.signVerified = PaySignUtil.verify(body, cfg.getPublicKey());
                JSONObject json = JSONUtil.parseObj(body);
                record.code = json.getInt("code");
                record.msg = json.getStr("msg");
            } catch (Exception e) {
                record.signVerified = false;
                record.msg = "解析失败: " + e.getMessage();
            }
        }
        callbacks.addFirst(record);
        while (callbacks.size() > MAX_CALLBACKS) {
            callbacks.pollLast();
        }
        System.out.println("[回调] " + record.time + " " + record.type + " 验签=" + record.signVerified);
        // 平台要求 HTTP 2xx 且 body 等于 SUCCESS（忽略大小写）
        sendText(exchange, 200, "SUCCESS");
    }

    // ==================================================================
    // HTTP 基础设施
    // ==================================================================

    private String readBody(HttpExchange exchange) throws IOException {
        return new String(IoUtil.readBytes(exchange.getRequestBody()), StandardCharsets.UTF_8);
    }

    private void sendResource(HttpExchange exchange, String classpath, String contentType) throws IOException {
        byte[] bytes = IoUtil.readBytes(Objects.requireNonNull(DemoServer.class.getResourceAsStream(classpath),
                "资源不存在: " + classpath));
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }

    private void sendJson(HttpExchange exchange, int status, JSONObject body) throws IOException {
        byte[] bytes = body.toString().getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }

    private void sendText(HttpExchange exchange, int status, String text) throws IOException {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }

    /// 回调记录（内存暂存，重启即清）
    private static class CallbackRecord {
        String time;
        String type;
        Boolean signVerified;
        Integer code;
        String msg;
        String body;

        Map<String, Object> toMap() {
            // 展示用 Map，body 存原文由页面按需解析
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("time", time);
            map.put("type", type);
            map.put("signVerified", signVerified);
            map.put("code", code);
            map.put("msg", msg);
            map.put("body", body);
            return map;
        }
    }
}
