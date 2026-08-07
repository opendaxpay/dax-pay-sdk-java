package cn.daxpay.open.examples;

import cn.daxpay.open.sdk.net.DaxPayClient;
import cn.daxpay.open.sdk.net.DaxPayConfig;
import cn.daxpay.open.sdk.param.PayParam;
import cn.daxpay.open.sdk.response.DaxResult;
import cn.daxpay.open.sdk.result.NormalPayResult;

/// # 支付下单示例 — Java
///
/// 运行前：将 SDK `mvn install` 到本地仓库，启动后端（daxpay-start，端口 9999），
/// 并将下方密钥替换为真实商户密钥。
public class PayExample {

    public static void main(String[] args) {
        // 商户私钥 + 平台公钥（PEM 文本，生产环境从环境变量/配置中心读取，切勿硬编码）
        String privateKey = "-----BEGIN PRIVATE KEY-----\n（替换为你的商户私钥）\n-----END PRIVATE KEY-----";
        String publicKey = "-----BEGIN PUBLIC KEY-----\n（替换为平台公钥）\n-----END PUBLIC KEY-----";

        DaxPayConfig config = new DaxPayConfig()
                .setServiceUrl("http://127.0.0.1:9999")
                .setMchNo("M200000001")
                .setAppId("APP001")
                .setPrivateKey(privateKey)
                .setPublicKey(publicKey);
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
}
