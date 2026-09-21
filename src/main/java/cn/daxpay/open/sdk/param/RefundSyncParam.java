package cn.daxpay.open.sdk.param;

/// # 退款订单同步参数
///
/// 对照契约 6.11 节。主动向通道拉取退款单最新状态并回写本地。
/// refundNo 与 bizRefundNo 至少传一个，优先 refundNo。
public class RefundSyncParam {

    /// 平台退款号（优先）
    private String refundNo;
    /// 商户退款号
    private String bizRefundNo;

    public String getRefundNo() {
        return refundNo;
    }

    public RefundSyncParam setRefundNo(String refundNo) {
        this.refundNo = refundNo;
        return this;
    }

    public String getBizRefundNo() {
        return bizRefundNo;
    }

    public RefundSyncParam setBizRefundNo(String bizRefundNo) {
        this.bizRefundNo = bizRefundNo;
        return this;
    }
}
