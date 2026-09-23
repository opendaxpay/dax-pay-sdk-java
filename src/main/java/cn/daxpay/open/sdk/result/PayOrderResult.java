package cn.daxpay.open.sdk.result;

/// # 支付订单查询结果
///
/// 对照契约 6.4 节 NormalPayOrderResult（时间字段为北京时间 yyyy-MM-dd HH:mm:ss 字面量）
public class PayOrderResult {

    /// 商户订单号
    private String bizOrderNo;
    /// 平台业务单号
    private String orderNo;
    /// 资金交易号
    private String tradeNo;
    /// 通道系统交易号
    private String outOrderNo;
    /// 支付标题
    private String title;
    /// 支付描述
    private String description;
    /// 支付通道
    private String channel;
    /// 支付方式
    private String method;
    /// 限制用户支付类型
    private String limitPay;
    /// 金额（分）
    private Long amount;
    /// 币种 ISO 4217（如 cny/usd，缺省 cny）
    private String currency;
    /// 实收金额（分）
    private Long realAmount;
    /// 可退款余额（分）
    private Long refundableBalance;
    /// 支付状态
    private String status;
    /// 退款状态
    private String refundStatus;
    /// 支付渠道（微信/支付宝/银联）
    private String provider;
    /// 支付时间
    private String payTime;
    /// 关闭时间
    private String closeTime;
    /// 过期时间
    private String expiredTime;
    /// 终端设备编码
    private String terminalNo;
    /// 门店号
    private String storeNo;
    /// 付款用户 ID
    private String buyerId;
    /// 商户扩展参数（原样返回）
    private String attach;
    /// 错误信息
    private String errorMsg;

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public PayOrderResult setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public PayOrderResult setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public PayOrderResult setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public PayOrderResult setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PayOrderResult setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PayOrderResult setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public PayOrderResult setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public PayOrderResult setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public PayOrderResult setLimitPay(String limitPay) {
        this.limitPay = limitPay;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public PayOrderResult setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public PayOrderResult setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Long getRealAmount() {
        return realAmount;
    }

    public PayOrderResult setRealAmount(Long realAmount) {
        this.realAmount = realAmount;
        return this;
    }

    public Long getRefundableBalance() {
        return refundableBalance;
    }

    public PayOrderResult setRefundableBalance(Long refundableBalance) {
        this.refundableBalance = refundableBalance;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public PayOrderResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public PayOrderResult setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public PayOrderResult setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getPayTime() {
        return payTime;
    }

    public PayOrderResult setPayTime(String payTime) {
        this.payTime = payTime;
        return this;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public PayOrderResult setCloseTime(String closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public PayOrderResult setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
        return this;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public PayOrderResult setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
        return this;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public PayOrderResult setStoreNo(String storeNo) {
        this.storeNo = storeNo;
        return this;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public PayOrderResult setBuyerId(String buyerId) {
        this.buyerId = buyerId;
        return this;
    }

    public String getAttach() {
        return attach;
    }

    public PayOrderResult setAttach(String attach) {
        this.attach = attach;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public PayOrderResult setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
