package cn.daxpay.open.sdk.result;

/// # 支付下单响应结果
///
/// 对照契约 6.1 节 NormalPayResult
public class NormalPayResult {

    /// 订单 ID
    private Long orderId;
    /// 商户订单号
    private String bizOrderNo;
    /// 平台业务单号
    private String orderNo;
    /// 资金交易号
    private String tradeNo;
    /// 支付状态：wait/progress/success/close/cancel/fail/timeout
    private String status;
    /// 支付参数体（二维码链接/调起参数/跳转 URL）
    private String payBody;
    /// 支付参数体类型：code_url/pay_info/redirect_url
    private String payBodyType;

    public Long getOrderId() {
        return orderId;
    }

    public NormalPayResult setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public NormalPayResult setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public NormalPayResult setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public NormalPayResult setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public NormalPayResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getPayBody() {
        return payBody;
    }

    public NormalPayResult setPayBody(String payBody) {
        this.payBody = payBody;
        return this;
    }

    public String getPayBodyType() {
        return payBodyType;
    }

    public NormalPayResult setPayBodyType(String payBodyType) {
        this.payBodyType = payBodyType;
        return this;
    }
}
