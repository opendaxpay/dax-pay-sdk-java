package cn.daxpay.open.examples;

import cn.daxpay.open.sdk.net.DaxPayClient;
import cn.daxpay.open.sdk.net.DaxPayConfig;
import cn.daxpay.open.sdk.param.PayParam;
import cn.daxpay.open.sdk.response.DaxResult;
import cn.daxpay.open.sdk.result.NormalPayResult;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.File;

/// # 支付下单示例 — Java
///
/// 配置驱动：复制 `config.example.json` 为 `config.local.json`（不入库）并填入真实参数，
/// 密钥支持两种方式：`privateKey`/`publicKey` 内嵌 PEM 字符串（优先），
/// 或 `privateKeyPath`/`publicKeyPath` 指向 PEM 文件（相对配置文件所在目录）。
///
/// 运行前：将 SDK `mvn install` 到本地仓库，启动后端（daxpay-start，端口 9999）。
public class PayExample {

    public static void main(String[] args) {
        JSONObject conf = loadConfig("examples/config.local.json");
        DaxPayConfig config = new DaxPayConfig()
                .setServiceUrl(conf.getStr("serviceUrl"))
                .setMchNo(conf.getStr("mchNo"))
                .setAppId(conf.getStr("appId"))
                .setPrivateKey(readKey(conf, "privateKey", "privateKeyPath"))
                .setPublicKey(readKey(conf, "publicKey", "publicKeyPath"));
        DaxPayClient client = new DaxPayClient(config);

        PayParam param = new PayParam()
                .setBizOrderNo("PAY_" + System.currentTimeMillis())
                .setTitle("测试商品")
                .setAmount(100L) // 分
                .setMethod("wechat_qr")
                .setNotifyUrl("https://example.com/notify");

        DaxResult<NormalPayResult> result = client.pay(param);
        NormalPayResult data = result.getData();
        System.out.println("订单号: " + data.getOrderNo());
        System.out.println("交易号: " + data.getTradeNo());
        System.out.println("状态: " + data.getStatus());
        System.out.println("支付参数体: " + data.getPayBody());
        System.out.println("支付参数体类型: " + data.getPayBodyType());
    }

    /** 读取配置文件（UTF-8） */
    private static JSONObject loadConfig(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new IllegalStateException("配置文件不存在: " + path + "，请复制 config.example.json 为 config.local.json 并填写");
        }
        return JSONUtil.parseObj(FileUtil.readUtf8String(file));
    }

    /** 密钥读取：内嵌字符串优先，其次相对配置目录的 PEM 文件路径 */
    private static String readKey(JSONObject conf, String inlineKey, String pathKey) {
        String inline = conf.getStr(inlineKey);
        if (inline != null && !inline.trim().isEmpty()) {
            return inline;
        }
        String path = conf.getStr(pathKey);
        if (path != null && !path.trim().isEmpty()) {
            return FileUtil.readUtf8String(new File("examples", path));
        }
        throw new IllegalStateException("缺少密钥: " + inlineKey + " 或 " + pathKey + " 至少配置一项");
    }
}
