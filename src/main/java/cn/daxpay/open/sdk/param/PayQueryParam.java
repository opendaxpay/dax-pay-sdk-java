package cn.daxpay.open.sdk.param;

/// # 查询支付订单请求参数
///
/// 对照契约 6.4 节（orderNo 与 bizOrderNo 至少传一个，优先 orderNo；公共字段由 DaxPayClient 注入）
public class PayQueryParam {

    /// 平台业务单号（优先）
    private String orderNo;
    /// 商户订单号
    private String bizOrderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public PayQueryParam setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public PayQueryParam setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }
}
