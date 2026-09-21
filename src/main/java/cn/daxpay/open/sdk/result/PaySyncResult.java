package cn.daxpay.open.sdk.result;

/// # 支付订单同步结果
///
/// 对照契约 6.11 节。同步接口统一返回「同步后的订单状态 + 是否发生状态订正」两个字段。
public class PaySyncResult {

    /// 同步后的支付订单状态
    private String orderStatus;
    /// 本次同步是否订正了本地状态（true=本地状态被通道结果修正）
    private boolean adjust;

    public String getOrderStatus() {
        return orderStatus;
    }

    public PaySyncResult setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public boolean isAdjust() {
        return adjust;
    }

    public PaySyncResult setAdjust(boolean adjust) {
        this.adjust = adjust;
        return this;
    }
}
