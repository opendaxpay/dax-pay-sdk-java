package cn.daxpay.open.sdk.result;

/// # 转账响应结果
///
/// 对照契约 6.7 节 TransferCreateResult。
public class TransferResult {

    /// 平台转账单号
    private String transferNo;
    /// 商户转账号
    private String bizTransferNo;
    /// 转账状态
    private String status;
    /// 确认收款跳转地址（部分通道需收款人确认收款）
    private String confirmUrl;

    public String getTransferNo() {
        return transferNo;
    }

    public TransferResult setTransferNo(String transferNo) {
        this.transferNo = transferNo;
        return this;
    }

    public String getBizTransferNo() {
        return bizTransferNo;
    }

    public TransferResult setBizTransferNo(String bizTransferNo) {
        this.bizTransferNo = bizTransferNo;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TransferResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getConfirmUrl() {
        return confirmUrl;
    }

    public TransferResult setConfirmUrl(String confirmUrl) {
        this.confirmUrl = confirmUrl;
        return this;
    }
}
