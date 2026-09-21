package cn.daxpay.open.sdk.param;

/// # 网关订单查询参数
///
/// 对照契约 6.13 节。与其它接口不同，本参数继承**平台公共参数**（非商户公共参数）：
/// mchNo / appId 是参数自身的可选字段，用于网关侧按应用定位订单（缺省时由 SDK 注入默认值）。
public class GatewayOrderQueryParam {

    /// 平台网关单号
    private String orderNo;
    /// 商户业务单号
    private String bizOrderNo;
    /// 应用号
    private String appId;
    /// 商户号
    private String mchNo;

    public String getOrderNo() {
        return orderNo;
    }

    public GatewayOrderQueryParam setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public GatewayOrderQueryParam setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public GatewayOrderQueryParam setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getMchNo() {
        return mchNo;
    }

    public GatewayOrderQueryParam setMchNo(String mchNo) {
        this.mchNo = mchNo;
        return this;
    }
}
