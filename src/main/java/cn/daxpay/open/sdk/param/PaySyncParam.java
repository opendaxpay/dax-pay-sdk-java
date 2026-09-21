package cn.daxpay.open.sdk.param;

/// # 支付订单同步参数
///
/// 对照契约 6.11 节。主动向通道拉取支付单最新状态并回写本地（用于回调丢失的兜底补偿）。
/// orderNo / bizOrderNo / outOrderNo 至少传一个。
public class PaySyncParam {

    /// 平台业务单号
    private String orderNo;
    /// 商户订单号
    private String bizOrderNo;
    /// 通道系统交易号
    private String outOrderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public PaySyncParam setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public PaySyncParam setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public PaySyncParam setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
        return this;
    }
}
