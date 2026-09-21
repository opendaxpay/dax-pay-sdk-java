package cn.daxpay.open.sdk.param;

import java.util.List;

/// # 网关预下单参数
///
/// 对照契约 6.12 节。产品语义「网关支付」：由平台收银台承接支付项选择与调起，
/// 商户侧只需拿到跳转地址（h5Url / miniUrl）。
public class GatewayPrePayParam {

    /// 商户订单号（必填）
    private String bizOrderNo;
    /// 支付标题（必填）
    private String title;
    /// 支付描述
    private String description;
    /// 支付金额，分（必填）
    private Long amount;
    /// 币种 ISO 4217，缺省 cny
    private String currency;
    /// 网关支付类型：cashier（统一收银台）/ aggregate（聚合扫码一码多付）
    private String gatewayPayType;
    /// 异步通知地址
    private String notifyUrl;
    /// 同步跳转地址
    private String returnUrl;
    /// 商户扩展参数，回调原样返回
    private String attach;
    /// 支付扩展参数（JSON 字符串，通道特有长尾参数）
    private String extraParam;
    /// 过期时间（GMT+8 yyyy-MM-dd HH:mm:ss）
    private String expiredTime;
    /// 门店编号
    private String storeNo;
    /// 订单商品明细（用于单品营销/电子发票）
    private List<GoodsDetail> goodsDetail;
    /// 是否为分账订单（分账链路前置条件）
    private Boolean allocation;

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public GatewayPrePayParam setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GatewayPrePayParam setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GatewayPrePayParam setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public GatewayPrePayParam setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public GatewayPrePayParam setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getGatewayPayType() {
        return gatewayPayType;
    }

    public GatewayPrePayParam setGatewayPayType(String gatewayPayType) {
        this.gatewayPayType = gatewayPayType;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public GatewayPrePayParam setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public GatewayPrePayParam setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    public String getAttach() {
        return attach;
    }

    public GatewayPrePayParam setAttach(String attach) {
        this.attach = attach;
        return this;
    }

    public String getExtraParam() {
        return extraParam;
    }

    public GatewayPrePayParam setExtraParam(String extraParam) {
        this.extraParam = extraParam;
        return this;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public GatewayPrePayParam setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
        return this;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public GatewayPrePayParam setStoreNo(String storeNo) {
        this.storeNo = storeNo;
        return this;
    }

    public List<GoodsDetail> getGoodsDetail() {
        return goodsDetail;
    }

    public GatewayPrePayParam setGoodsDetail(List<GoodsDetail> goodsDetail) {
        this.goodsDetail = goodsDetail;
        return this;
    }

    public Boolean getAllocation() {
        return allocation;
    }

    public GatewayPrePayParam setAllocation(Boolean allocation) {
        this.allocation = allocation;
        return this;
    }
}
