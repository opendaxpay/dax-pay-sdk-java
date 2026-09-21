package cn.daxpay.open.sdk.param;

import java.util.List;

/// # 支付下单请求参数
///
/// 对照契约 6.1 节（公共字段由 DaxPayClient 注入）。
/// 嵌套类型 [GoodsDetail] / [TerminalInfo] 已建模；通道特有长尾参数仍可走 extraParam。
public class PayParam {

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
    /// 支付产品编码
    private String product;
    /// 支付方式编码
    private String method;
    /// 支付能力编码
    private String capability;
    /// 用户 OpenId（微信 jsapi/mini 场景）
    private String openId;
    /// 通道应用 AppId（微信 wxAppId 等）
    private String channelAppId;
    /// 付款码（被扫支付）
    private String authCode;
    /// 限制支付类型（如 ["no_credit"]）
    private List<String> limitPay;
    /// 支付扩展参数（JSON 字符串，通道特有长尾参数）
    private String extraParam;
    /// 异步通知地址
    private String notifyUrl;
    /// 同步跳转地址
    private String returnUrl;
    /// 商户扩展参数，回调原样返回
    private String attach;
    /// 过期时间（GMT+8 yyyy-MM-dd HH:mm:ss）
    private String expiredTime;
    /// 订单商品明细（用于单品营销/电子发票）
    private List<GoodsDetail> goodsDetail;
    /// 终端信息（线下 POS/收银台场景）
    private TerminalInfo terminal;
    /// 订单来源标识
    private String source;
    /// 是否为分账订单（分账链路前置条件：下单未声明则通道拒绝后续分账）
    private Boolean allocation;

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public PayParam setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PayParam setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PayParam setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public PayParam setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public PayParam setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public PayParam setProduct(String product) {
        this.product = product;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public PayParam setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getCapability() {
        return capability;
    }

    public PayParam setCapability(String capability) {
        this.capability = capability;
        return this;
    }

    public String getOpenId() {
        return openId;
    }

    public PayParam setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getChannelAppId() {
        return channelAppId;
    }

    public PayParam setChannelAppId(String channelAppId) {
        this.channelAppId = channelAppId;
        return this;
    }

    public String getAuthCode() {
        return authCode;
    }

    public PayParam setAuthCode(String authCode) {
        this.authCode = authCode;
        return this;
    }

    public List<String> getLimitPay() {
        return limitPay;
    }

    public PayParam setLimitPay(List<String> limitPay) {
        this.limitPay = limitPay;
        return this;
    }

    public String getExtraParam() {
        return extraParam;
    }

    public PayParam setExtraParam(String extraParam) {
        this.extraParam = extraParam;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public PayParam setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public PayParam setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    public String getAttach() {
        return attach;
    }

    public PayParam setAttach(String attach) {
        this.attach = attach;
        return this;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public PayParam setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
        return this;
    }

    public List<GoodsDetail> getGoodsDetail() {
        return goodsDetail;
    }

    public PayParam setGoodsDetail(List<GoodsDetail> goodsDetail) {
        this.goodsDetail = goodsDetail;
        return this;
    }

    public TerminalInfo getTerminal() {
        return terminal;
    }

    public PayParam setTerminal(TerminalInfo terminal) {
        this.terminal = terminal;
        return this;
    }

    public String getSource() {
        return source;
    }

    public PayParam setSource(String source) {
        this.source = source;
        return this;
    }

    public Boolean getAllocation() {
        return allocation;
    }

    public PayParam setAllocation(Boolean allocation) {
        this.allocation = allocation;
        return this;
    }
}
