package cn.daxpay.open.sdk.result;

/// # 分账订单同步结果
///
/// 对照契约 6.11 节。
public class AllocSyncResult {

    /// 同步后的分账订单状态
    private String orderStatus;
    /// 本次同步是否订正了本地状态
    private boolean adjust;

    public String getOrderStatus() {
        return orderStatus;
    }

    public AllocSyncResult setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public boolean isAdjust() {
        return adjust;
    }

    public AllocSyncResult setAdjust(boolean adjust) {
        this.adjust = adjust;
        return this;
    }
}
