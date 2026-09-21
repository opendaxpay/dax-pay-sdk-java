package cn.daxpay.open.sdk.result;

/// # 退款订单同步结果
///
/// 对照契约 6.11 节。
public class RefundSyncResult {

    /// 同步后的退款订单状态
    private String orderStatus;
    /// 本次同步是否订正了本地状态
    private boolean adjust;

    public String getOrderStatus() {
        return orderStatus;
    }

    public RefundSyncResult setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public boolean isAdjust() {
        return adjust;
    }

    public RefundSyncResult setAdjust(boolean adjust) {
        this.adjust = adjust;
        return this;
    }
}
