package cn.daxpay.open.sdk.param;

import java.util.List;

/// # 转账请求参数
///
/// 对照契约 6.7 节。按通道直连发起转账（商户转账到余额/银行卡/OpenId 等）。
/// 幂等维度为 通道 + 商户转账号 + 商户号：同组合重复发起会拦截，失败单可复用原单号重试。
/// 金额单位为**分**（与 unipay 契约统一），公共字段由 DaxPayClient 注入。
public class TransferParam {

    /// 转账通道（wechat/alipay/douyin，必填）
    private String channel;
    /// 通道商户号（转账凭证组装与通道路由用，必填）
    private String channelMchNo;
    /// 商户转账号（幂等键，同一商户同一通道下唯一；失败后可复用原单号重试）
    private String bizTransferNo;
    /// 转账金额（分，必填）
    private Long amount;
    /// 转账标题
    private String title;
    /// 转账原因/备注
    private String reason;
    /// 收款人账号类型（微信=openid；支付宝=user_id/open_id/login_name；抖音=openid/phone）
    private String payeeType;
    /// 收款人账号
    private String payeeAccount;
    /// 收款人姓名（微信：小于 0.3 元禁填，大于等于 2000 元必填）
    private String payeeName;
    /// 商户扩展参数，回调原样返回
    private String attach;
    /// 异步通知地址
    private String notifyUrl;
    /// 转账场景报备信息（微信转账场景必填，各场景要求不同，留空由通道兜底）
    private List<ReportInfo> reportInfos;
    /// 转账场景标识（支付宝=转账场景配置 ID，抖音=主数据枚举码如 1001；微信不传，用通道商户配置场景）
    private String transferScene;

    public String getChannel() {
        return channel;
    }

    public TransferParam setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getChannelMchNo() {
        return channelMchNo;
    }

    public TransferParam setChannelMchNo(String channelMchNo) {
        this.channelMchNo = channelMchNo;
        return this;
    }

    public String getBizTransferNo() {
        return bizTransferNo;
    }

    public TransferParam setBizTransferNo(String bizTransferNo) {
        this.bizTransferNo = bizTransferNo;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public TransferParam setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TransferParam setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public TransferParam setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getPayeeType() {
        return payeeType;
    }

    public TransferParam setPayeeType(String payeeType) {
        this.payeeType = payeeType;
        return this;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public TransferParam setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
        return this;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public TransferParam setPayeeName(String payeeName) {
        this.payeeName = payeeName;
        return this;
    }

    public String getAttach() {
        return attach;
    }

    public TransferParam setAttach(String attach) {
        this.attach = attach;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public TransferParam setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    public List<ReportInfo> getReportInfos() {
        return reportInfos;
    }

    public TransferParam setReportInfos(List<ReportInfo> reportInfos) {
        this.reportInfos = reportInfos;
        return this;
    }

    public String getTransferScene() {
        return transferScene;
    }

    public TransferParam setTransferScene(String transferScene) {
        this.transferScene = transferScene;
        return this;
    }

    /// # 转账场景报备信息（单个）
    ///
    /// 微信转账场景报备，字段含义由通道场景定义决定（如 1000 现金营销场景需报备活动名称）。
    public static class ReportInfo {

        /// 报备信息类型
        private String infoType;
        /// 报备信息内容
        private String infoContent;

        public String getInfoType() {
            return infoType;
        }

        public ReportInfo setInfoType(String infoType) {
            this.infoType = infoType;
            return this;
        }

        public String getInfoContent() {
            return infoContent;
        }

        public ReportInfo setInfoContent(String infoContent) {
            this.infoContent = infoContent;
            return this;
        }
    }
}
