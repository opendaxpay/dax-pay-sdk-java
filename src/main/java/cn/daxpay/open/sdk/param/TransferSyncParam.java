package cn.daxpay.open.sdk.param;

/// # 转账订单同步参数
///
/// 对照契约 6.11 节。主动向通道拉取转账单最新状态并回写本地。
/// transferNo 单独可查；bizTransferNo 须配 channel（与发起幂等维度一致）。
public class TransferSyncParam {

    /// 平台转账单号（优先）
    private String transferNo;
    /// 转账通道（与商户转账号配对使用）
    private String channel;
    /// 商户转账号（与转账通道配对使用）
    private String bizTransferNo;

    public String getTransferNo() {
        return transferNo;
    }

    public TransferSyncParam setTransferNo(String transferNo) {
        this.transferNo = transferNo;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public TransferSyncParam setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getBizTransferNo() {
        return bizTransferNo;
    }

    public TransferSyncParam setBizTransferNo(String bizTransferNo) {
        this.bizTransferNo = bizTransferNo;
        return this;
    }
}
