package cn.daxpay.open.sdk.result;

/// # 退款订单查询结果
///
/// 对照契约 6.5 节 RefundOrderResult（finishTime 为北京时间 yyyy-MM-dd HH:mm:ss 字面量）
public class RefundOrderResult {

    /// 平台退款号
    private String refundNo;
    /// 商户退款号
    private String bizRefundNo;
    /// 原支付资金交易号
    private String tradeNo;
    /// 原支付商户业务订单号
    private String bizOrderNo;
    /// 通道退款流水号
    private String outRefundNo;
    /// 退款金额（分）
    private Long amount;
    /// 订单总金额（分）
    private Long orderAmount;
    /// 退款状态
    private String status;
    /// 退款原因
    private String reason;
    /// 退款完成时间
    private String finishTime;
    /// 错误信息
    private String errorMsg;

    public String getRefundNo() {
        return refundNo;
    }

    public RefundOrderResult setRefundNo(String refundNo) {
        this.refundNo = refundNo;
        return this;
    }

    public String getBizRefundNo() {
        return bizRefundNo;
    }

    public RefundOrderResult setBizRefundNo(String bizRefundNo) {
        this.bizRefundNo = bizRefundNo;
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public RefundOrderResult setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public RefundOrderResult setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public RefundOrderResult setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public RefundOrderResult setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public RefundOrderResult setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public RefundOrderResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public RefundOrderResult setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public RefundOrderResult setFinishTime(String finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public RefundOrderResult setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
