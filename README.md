# DaxPay Open SDK for Java

DaxPay 开放支付平台 Java SDK，封装支付下单、关闭、退款、订单查询与回调验签。

> **适配 DaxPay Open ≥ 1.0** · **Java 8+** · LGPL-3.0

## 功能

- RSA 双向签名（SHA256withRSA），自动签名请求 / 验签响应与回调
- 走 JSON 签名路径，与开源版后端 `reqTime`（北京时间字面量）契约对齐
- 核心支付接口：pay / close / refund / query pay-order / query refund-order
- 异步回调验签

## 安装（源码引入）

```bash
git clone https://github.com/opendaxpay/dax-pay-sdk-java.git
cd dax-pay-sdk-java
mvn clean install -Dmaven.test.skip=true
```

```xml
<dependency>
    <groupId>cn.daxpay.open</groupId>
    <artifactId>daxpay-open-sdk</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## 快速开始

```java
// 配置（商户私钥 + 平台公钥，PEM 文本）
DaxPayConfig config = DaxPayConfig.builder()
        .serviceUrl("https://sandbox.daxpay.cn")
        .mchNo("M200000001")
        .appId("APP001")
        .privateKey(privateKeyPem)
        .publicKey(platformPublicKeyPem)
        .build();

DaxPayClient client = new DaxPayClient(config);

// 支付下单
PayParam param = new PayParam()
        .setBizOrderNo("PAY20250805001")
        .setTitle("测试商品")
        .setAmount(100L)            // 分
        .setMethod("wechat_qr")
        .setNotifyUrl("https://example.com/notify");
DaxResult<PayResult> result = client.execute(PayRequest.pay(param));

// 回调验签
// String rawBody = ...; // HTTP 原始请求体
// boolean ok = client.verifyNotice(rawBody);
```

> 完整可运行示例见 [`examples/PayExample.java`](examples/PayExample.java)。

## 接口文档

- [接入准备](https://doc.open.daxpay.cn/api/getting-started) · [签名规则](https://doc.open.daxpay.cn/api/signature)
- 黄金测试向量：见 [`GoldenVectorTest.java`](src/test/java/cn/daxpay/open/sdk/GoldenVectorTest.java)（与后端签名契约同源断言）

## License

LGPL-3.0，与主仓库 [DaxPay Open](https://gitee.com/dromara/dax-pay) 同协议。
