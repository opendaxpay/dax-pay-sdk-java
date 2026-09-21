package cn.daxpay.open.sdk.result;

/// # 网关预下单结果
///
/// 对照契约 6.12 节。商户侧拿到跳转地址后引导用户进入平台收银台/聚合码页。
public class GatewayPrePayResult {

    /// 平台网关单号
    private String orderNo;
    /// 商户订单号
    private String bizOrderNo;
    /// 订单状态
    private String status;
    /// 网关支付类型（cashier/aggregate）
    private String gatewayType;
    /// H5 收银台跳转地址
    private String h5Url;
    /// 小程序收银台跳转地址
    private String miniUrl;
    /// 过期时间（北京时间 yyyy-MM-dd HH:mm:ss 字面量）
    private String expiredTime;

    public String getOrderNo() {
        return orderNo;
    }

    public GatewayPrePayResult setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public GatewayPrePayResult setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public GatewayPrePayResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getGatewayType() {
        return gatewayType;
    }

    public GatewayPrePayResult setGatewayType(String gatewayType) {
        this.gatewayType = gatewayType;
        return this;
    }

    public String getH5Url() {
        return h5Url;
    }

    public GatewayPrePayResult setH5Url(String h5Url) {
        this.h5Url = h5Url;
        return this;
    }

    public String getMiniUrl() {
        return miniUrl;
    }

    public GatewayPrePayResult setMiniUrl(String miniUrl) {
        this.miniUrl = miniUrl;
        return this;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public GatewayPrePayResult setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
        return this;
    }
}
