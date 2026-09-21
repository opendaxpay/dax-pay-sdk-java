package cn.daxpay.open.sdk.param;

/// # 查询退款订单请求参数
///
/// 对照契约 6.5 节（refundNo 与 bizRefundNo 至少传一个，优先 refundNo；公共字段由 DaxPayClient 注入）
public class RefundQueryParam {

    /// 平台退款号（优先）
    private String refundNo;
    /// 商户退款号
    private String bizRefundNo;

    public String getRefundNo() {
        return refundNo;
    }

    public RefundQueryParam setRefundNo(String refundNo) {
        this.refundNo = refundNo;
        return this;
    }

    public String getBizRefundNo() {
        return bizRefundNo;
    }

    public RefundQueryParam setBizRefundNo(String bizRefundNo) {
        this.bizRefundNo = bizRefundNo;
        return this;
    }
}
