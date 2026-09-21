# DaxPay Open SDK for Java

DaxPay 开放支付平台 Java SDK，封装支付/退款/转账/分账/查询/同步/网关共 15 个开放接口与回调验签。

> **适配 DaxPay Open ≥ 1.0** · **Java 8+** · Apache-2.0

## 功能

- RSA 双向签名（SHA256withRSA），自动签名请求 / 验签响应与回调
- 走 JSON 签名路径，与开源版后端 `reqTime`（北京时间字面量）契约对齐
- 开放接口全覆盖（15 个）：
  - **支付**：`pay` / `close` / `queryPayOrder` / `syncPayOrder`
  - **退款**：`refund` / `queryRefundOrder` / `syncRefundOrder`
  - **转账**：`transfer` / `queryTransferOrder` / `syncTransferOrder`
  - **分账**：`alloc` / `queryAllocOrder` / `syncAllocOrder`
  - **网关**：`gatewayPrePay` / `gatewayQuery`
- 异步回调验签 `verifyNotice(rawBody)`；部署自检探针 `ping()`

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
DaxPayConfig config = new DaxPayConfig()
        .setServiceUrl("https://sandbox.daxpay.cn")
        .setMchNo("M200000001")
        .setAppId("APP001")
        .setPrivateKey(privateKeyPem)
        .setPublicKey(platformPublicKeyPem);

DaxPayClient client = new DaxPayClient(config);

// 支付下单
PayParam param = new PayParam()
        .setBizOrderNo("PAY20250805001")
        .setTitle("测试商品")
        .setAmount(100L)            // 分
        .setMethod("wechat_qr")
        .setNotifyUrl("https://example.com/notify");
DaxResult<NormalPayResult> result = client.pay(param);

// 关闭 / 退款 / 查询：client.close(param) / client.refund(param)
//                          / client.queryPayOrder(param) / client.queryRefundOrder(param)

// 转账（金额单位：分；幂等维度 = 通道 + 商户转账号 + 商户号）
TransferParam transfer = new TransferParam()
        .setChannel("wechat")
        .setChannelMchNo("CM001")
        .setBizTransferNo("TR20250805001")
        .setAmount(100L)
        .setPayeeType("openid")
        .setPayeeAccount("oXyz123");
DaxResult<TransferResult> transferResult = client.transfer(transfer);

// 分账（原支付单须下单时声明 allocation=true）
AllocParam alloc = new AllocParam()
        .setBizAllocNo("AL20250805001")
        .setBizOrderNo("PAY20250805001")
        .setReceivers(Arrays.asList(
                new AllocParam.Receiver().setReceiverType("MERCHANT_ID")
                        .setReceiverAccount("M300000001").setAmount(60L),
                new AllocParam.Receiver().setReceiverType("PERSONAL_OPENID")
                        .setReceiverAccount("oXyz123").setAmount(40L)));
DaxResult<AllocResult> allocResult = client.alloc(alloc);

// 网关支付（平台收银台承接支付项选择，商户侧拿跳转地址）
GatewayPrePayParam gateway = new GatewayPrePayParam()
        .setBizOrderNo("GW20250805001")
        .setTitle("网关支付")
        .setAmount(100L)
        .setGatewayPayType("cashier");
DaxResult<GatewayPrePayResult> gwResult = client.gatewayPrePay(gateway);
// gwResult.getData().getH5Url() / getMiniUrl()

// 回调验签
// String rawBody = ...; // HTTP 原始请求体
// boolean ok = client.verifyNotice(rawBody);

// 部署自检探针（免签名）：可达即代表「通道回调」接口组已放行
// String marker = client.ping();
```

> 完整可运行示例见 [`examples/PayExample.java`](examples/PayExample.java)（配置驱动，见下节）。

## 联调 Demo（推荐入门方式）

仓内自带一个**单命令启动的联调 Demo**：内嵌调试页 + 全接口表单 + 回调接收，
所有交易调用都经 `DaxPayClient` 真实签名发出，同时验证 SDK 与平台接口两侧。

### 1. 启动（无需任何配置文件）

```bash
mvn compile exec:java -Dexec.mainClass=cn.daxpay.open.sdk.demo.DemoServer
```

浏览器打开 <http://127.0.0.1:9799>，页面为**三栏布局**：左侧接口导航（5 个业务域 / 15 个接口）、
中间表单与结果、右侧**随表单实时生成的 SDK 调用代码**（可直接复制到项目里用）。

点击右上角 **「连接配置」** 打开弹窗填写参数，保存即生效：

| 配置项 | 说明 |
|--------|------|
| 平台服务地址 | 如 `http://127.0.0.1:9999` |
| 商户号 / 应用号 | 应用号可空（回落平台默认应用） |
| 商户私钥 | PKCS#8 PEM，粘贴后即时校验格式 |
| 平台公钥 | X.509 PEM，用于响应与回调验签 |

弹窗内 **「测试连接」** 按钮会经服务端中转调用平台自检探针 `GET /unipay/callback/ping`
（浏览器直连平台地址会跨域），可快速区分「地址写错」「网关未放行 /unipay/callback 前缀」「后端未启动」。
注意探针需平台版本包含该端点（`UnipayPingController`），旧版本会返回 401。

配置保存在**浏览器 localStorage**（仅本机、服务端不落盘），服务重启后打开页面自动恢复；
页面内配置**优先于**启动时的配置文件。

可选命令行参数：`--port=9799` 更换监听端口；`--config=路径` 指定初始配置文件。
也兼容配置文件方式（无人值守场景）：复制 `examples/config.example.json` 为
`examples/config.local.json` 填好即可（密钥支持内嵌 PEM 或相对配置目录的文件路径）。

### 2. 使用

- 左侧导航切换 **15 个开放接口**（支付 / 退款 / 转账 / 分账 / 网关五族），
  每个接口的表单都带必填校验与类型校验，嵌套结构（商品明细 / 终端信息 / 转账报备 / 分账接收方）
  用可增删的行编辑器填写
- 结果区回显「SDK 签名后的完整请求体 + 平台原始响应 + 响应验签结果 + 耗时」，
  支付类接口单独透出 `payBody` / `h5Url` / `confirmUrl` 等跳转地址
- **回调通知记录** 区实时轮询展示平台异步通知（自动验签）；
  表单 `notifyUrl` 填 Demo 提示的回调地址（默认已填好）即可完成「支付 → 回调 → 验签」全链路闭环

## 接口文档

- [接入准备](https://doc.open.daxpay.cn/api/getting-started) · [签名规则](https://doc.open.daxpay.cn/api/signature)
- 黄金测试向量：见 [`GoldenVectorTest.java`](src/test/java/cn/daxpay/open/sdk/GoldenVectorTest.java)（与后端签名契约同源断言）

## License

Apache-2.0，可自由用于商业项目与闭源集成，协议全文见 [LICENSE](LICENSE)。主仓库 [DaxPay Open](https://gitee.com/dromara/dax-pay) 核心为 LGPL-3.0-or-later，本 SDK 作为独立仓按 Apache-2.0 单独发布。
