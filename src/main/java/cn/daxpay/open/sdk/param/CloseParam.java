package cn.daxpay.open.sdk.param;

/// # 关闭/撤销订单请求参数
///
/// 对照契约 6.2 节（orderNo 与 bizOrderNo 至少传一个，优先 orderNo；公共字段由 DaxPayClient 注入）
public class CloseParam {

    /// 平台支付订单号（tradeNo）或网关订单号（优先）
    private String orderNo;
    /// 商户订单号
    private String bizOrderNo;
    /// 是否使用撤销方式（部分通道支持，不支持则忽略）
    private Boolean useCancel;

    public String getOrderNo() {
        return orderNo;
    }

    public CloseParam setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public CloseParam setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public Boolean getUseCancel() {
        return useCancel;
    }

    public CloseParam setUseCancel(Boolean useCancel) {
        this.useCancel = useCancel;
        return this;
    }
}
