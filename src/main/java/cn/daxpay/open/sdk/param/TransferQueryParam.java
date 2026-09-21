package cn.daxpay.open.sdk.param;

/// # 转账订单查询参数
///
/// 对照契约 6.10 节。定位方式二选一：平台转账单号（transferNo）单独可查；
/// 商户转账号（bizTransferNo）**须配转账通道（channel）**，与发起幂等维度（通道+商户转账号+商户号）保持一致。
/// 仅查询本地转账单，不调用通道；需实时通道状态走转账同步接口。
public class TransferQueryParam {

    /// 平台转账单号（优先）
    private String transferNo;
    /// 转账通道（与商户转账号配对使用）
    private String channel;
    /// 商户转账号（与转账通道配对使用）
    private String bizTransferNo;

    public String getTransferNo() {
        return transferNo;
    }

    public TransferQueryParam setTransferNo(String transferNo) {
        this.transferNo = transferNo;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public TransferQueryParam setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getBizTransferNo() {
        return bizTransferNo;
    }

    public TransferQueryParam setBizTransferNo(String bizTransferNo) {
        this.bizTransferNo = bizTransferNo;
        return this;
    }
}
