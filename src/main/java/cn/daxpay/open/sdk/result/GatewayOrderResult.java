package cn.daxpay.open.sdk.result;

/// # 网关订单查询结果
///
/// 对照契约 6.13 节（时间字段为北京时间 yyyy-MM-dd HH:mm:ss 字面量）。
public class GatewayOrderResult {

    /// 平台网关单号
    private String orderNo;
    /// 商户业务单号
    private String bizOrderNo;
    /// 网关支付类型（cashier/aggregate）
    private String gatewayType;
    /// 支付标题
    private String title;
    /// 支付描述
    private String description;
    /// 金额（分）
    private Long amount;
    /// 币种 ISO 4217
    private String currency;
    /// 订单状态
    private String status;
    /// 过期时间
    private String expiredTime;
    /// 支付时间
    private String payTime;
    /// 支付通道
    private String channel;
    /// 支付方式
    private String method;
    /// 支付产品编码
    private String product;
    /// 资金交易号
    private String tradeNo;
    /// 通道系统交易号
    private String outOrderNo;
    /// 资金状态
    private String fundStatus;
    /// 商户扩展参数（原样返回）
    private String attach;
    /// 同步跳转地址
    private String returnUrl;

    public String getOrderNo() {
        return orderNo;
    }

    public GatewayOrderResult setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public GatewayOrderResult setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getGatewayType() {
        return gatewayType;
    }

    public GatewayOrderResult setGatewayType(String gatewayType) {
        this.gatewayType = gatewayType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GatewayOrderResult setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GatewayOrderResult setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public GatewayOrderResult setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public GatewayOrderResult setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public GatewayOrderResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public GatewayOrderResult setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
        return this;
    }

    public String getPayTime() {
        return payTime;
    }

    public GatewayOrderResult setPayTime(String payTime) {
        this.payTime = payTime;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public GatewayOrderResult setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public GatewayOrderResult setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public GatewayOrderResult setProduct(String product) {
        this.product = product;
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public GatewayOrderResult setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public GatewayOrderResult setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
        return this;
    }

    public String getFundStatus() {
        return fundStatus;
    }

    public GatewayOrderResult setFundStatus(String fundStatus) {
        this.fundStatus = fundStatus;
        return this;
    }

    public String getAttach() {
        return attach;
    }

    public GatewayOrderResult setAttach(String attach) {
        this.attach = attach;
        return this;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public GatewayOrderResult setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }
}
