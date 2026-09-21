package cn.daxpay.open.sdk.param;

/// # 退款请求参数
///
/// 对照契约 6.3 节（tradeNo 与 bizOrderNo 至少传一个，优先 tradeNo；公共字段由 DaxPayClient 注入）
public class RefundParam {

    /// 原支付资金交易号（优先）
    private String tradeNo;
    /// 原支付商户业务订单号
    private String bizOrderNo;
    /// 退款金额，分（必填，>0，支持部分退款）
    private Long amount;
    /// 退款原因
    private String reason;
    /// 商户退款号（不传则系统生成）
    private String bizRefundNo;

    public String getTradeNo() {
        return tradeNo;
    }

    public RefundParam setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public RefundParam setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public RefundParam setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public RefundParam setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getBizRefundNo() {
        return bizRefundNo;
    }

    public RefundParam setBizRefundNo(String bizRefundNo) {
        this.bizRefundNo = bizRefundNo;
        return this;
    }
}
