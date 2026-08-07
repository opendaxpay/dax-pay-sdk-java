package cn.daxpay.open.sdk.param;

/// # 支付下单请求参数
///
/// 对照契约 6.1 节（核心字段，公共字段由 DaxPayClient 注入）
public class PayParam {

    /// 商户订单号（必填）
    private String bizOrderNo;
    /// 支付标题（必填）
    private String title;
    /// 支付描述
    private String description;
    /// 支付金额，分（必填）
    private Long amount;
    /// 币种 ISO 4217，缺省 cny
    private String currency;
    /// 支付产品编码
    private String product;
    /// 支付方式编码
    private String method;
    /// 用户 OpenId（微信 jsapi/mini 场景）
    private String openId;
    /// 付款码（被扫支付）
    private String authCode;
    /// 异步通知地址
    private String notifyUrl;
    /// 同步跳转地址
    private String returnUrl;
    /// 商户扩展参数，回调原样返回
    private String attach;
    /// 过期时间（GMT+8 yyyy-MM-dd HH:mm:ss）
    private String expiredTime;

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public PayParam setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PayParam setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PayParam setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public PayParam setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public PayParam setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public PayParam setProduct(String product) {
        this.product = product;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public PayParam setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getOpenId() {
        return openId;
    }

    public PayParam setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getAuthCode() {
        return authCode;
    }

    public PayParam setAuthCode(String authCode) {
        this.authCode = authCode;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public PayParam setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public PayParam setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    public String getAttach() {
        return attach;
    }

    public PayParam setAttach(String attach) {
        this.attach = attach;
        return this;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public PayParam setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
        return this;
    }
}
