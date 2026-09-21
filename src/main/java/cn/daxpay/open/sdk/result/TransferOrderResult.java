package cn.daxpay.open.sdk.result;

/// # 转账订单查询结果
///
/// 对照契约 6.10 节 TransferOrderResult（时间字段为北京时间 yyyy-MM-dd HH:mm:ss 字面量）。
public class TransferOrderResult {

    /// 平台转账单号
    private String transferNo;
    /// 商户转账号
    private String bizTransferNo;
    /// 通道转账单号
    private String outTransferNo;
    /// 关联单号（如转账来源业务单号）
    private String relationNo;
    /// 转账金额（分）
    private Long amount;
    /// 币种 ISO 4217
    private String currency;
    /// 转账通道
    private String channel;
    /// 支付渠道（微信/支付宝/抖音）
    private String provider;
    /// 转账状态
    private String status;
    /// 转账标题
    private String title;
    /// 转账完成时间
    private String finishTime;
    /// 错误信息
    private String errorMsg;

    public String getTransferNo() {
        return transferNo;
    }

    public TransferOrderResult setTransferNo(String transferNo) {
        this.transferNo = transferNo;
        return this;
    }

    public String getBizTransferNo() {
        return bizTransferNo;
    }

    public TransferOrderResult setBizTransferNo(String bizTransferNo) {
        this.bizTransferNo = bizTransferNo;
        return this;
    }

    public String getOutTransferNo() {
        return outTransferNo;
    }

    public TransferOrderResult setOutTransferNo(String outTransferNo) {
        this.outTransferNo = outTransferNo;
        return this;
    }

    public String getRelationNo() {
        return relationNo;
    }

    public TransferOrderResult setRelationNo(String relationNo) {
        this.relationNo = relationNo;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public TransferOrderResult setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public TransferOrderResult setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public TransferOrderResult setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public TransferOrderResult setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TransferOrderResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TransferOrderResult setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public TransferOrderResult setFinishTime(String finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public TransferOrderResult setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
