package cn.daxpay.open.sdk.result;

/// # 退款响应结果
///
/// 对照契约 6.3 节 RefundResult
public class RefundResult {

    /// 平台退款号
    private String refundNo;
    /// 商户退款号
    private String bizRefundNo;
    /// 退款状态
    private String status;
    /// 错误信息（失败时返回）
    private String errorMsg;

    public String getRefundNo() {
        return refundNo;
    }

    public RefundResult setRefundNo(String refundNo) {
        this.refundNo = refundNo;
        return this;
    }

    public String getBizRefundNo() {
        return bizRefundNo;
    }

    public RefundResult setBizRefundNo(String bizRefundNo) {
        this.bizRefundNo = bizRefundNo;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public RefundResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public RefundResult setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
