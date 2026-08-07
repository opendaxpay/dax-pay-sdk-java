package cn.daxpay.open.sdk.net;

import cn.daxpay.open.sdk.param.PayParam;
import cn.daxpay.open.sdk.response.DaxResult;
import cn.daxpay.open.sdk.result.NormalPayResult;
import cn.daxpay.open.sdk.util.PaySignUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/// # DaxPay SDK 客户端
///
/// 对照 sdk-contract.md 第十节。走 JSON 签名路径（reqTime 序列化为 GMT+8 字面量），与后端验签一致。
public class DaxPayClient {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final DaxPayConfig config;

    public DaxPayClient(DaxPayConfig config) {
        this.config = config;
    }

    /// 通用执行：自动填充公共参数 → JSON 签名 → POST → 验签 → 按 dataClass 解析 data
    public <T> DaxResult<T> execute(String path, Object param, Class<T> dataClass) {
        // param → JSONObject（保留字段），注入公共字段
        JSONObject json = JSONUtil.parseObj(JSONUtil.toJsonStr(param));
        json.putIfAbsent("mchNo", config.getMchNo());
        if (config.getAppId() != null) {
            json.putIfAbsent("appId", config.getAppId());
        }
        json.putIfAbsent("reqId", UUID.randomUUID().toString());
        json.putIfAbsent("reqTime", nowGmt8());
        json.putIfAbsent("nonceStr", UUID.randomUUID().toString().replace("-", ""));

        // 走 JSON 签名路径：对序列化后的 JSON 签名 → 注入 sign → 重新序列化发送
        String jsonForSign = json.toString();
        String sign = PaySignUtil.sign(jsonForSign, config.getPrivateKey());
        json.put("sign", sign);
        String body = json.toString();

        // POST
        String url = config.getServiceUrl() + path;
        String responseBody;
        try (HttpResponse resp = HttpUtil.createPost(url)
                .body(body, ContentType.JSON.getValue())
                .timeout(config.getReqTimeout())
                .execute()) {
            if (resp.getStatus() != HttpStatus.HTTP_OK) {
                throw new RuntimeException("请求失败: HTTP " + resp.getStatus());
            }
            responseBody = resp.body();
        }

        // 验签（原始 body 字符串）
        if (!PaySignUtil.verify(responseBody, config.getPublicKey())) {
            throw new RuntimeException("响应验签失败");
        }

        // 解析 + 业务码检查
        DaxResult<JSONObject> raw = JSONUtil.toBean(responseBody,
                new TypeReference<DaxResult<JSONObject>>() {
                }, true);
        if (raw.getCode() != 0) {
            throw new RuntimeException("[" + raw.getCode() + "] " + raw.getMsg());
        }
        T data = null;
        if (raw.getData() != null) {
            data = raw.getData().toBean(dataClass);
        }
        DaxResult<T> result = new DaxResult<>();
        result.setCode(raw.getCode()).setMsg(raw.getMsg()).setData(data)
                .setSign(raw.getSign()).setResTime(raw.getResTime()).setReqId(raw.getReqId());
        return result;
    }

    /// 支付下单便捷方法 — POST /unipay/pay
    public DaxResult<NormalPayResult> pay(PayParam param) {
        return execute("/unipay/pay", param, NormalPayResult.class);
    }

    /// 回调验签（原始 HTTP body 字符串）— 对照契约第八节
    public boolean verifyNotice(String rawBody) {
        return PaySignUtil.verify(rawBody, config.getPublicKey());
    }

    private String nowGmt8() {
        return OffsetDateTime.now(ZoneOffset.ofHours(8)).format(FMT);
    }
}
