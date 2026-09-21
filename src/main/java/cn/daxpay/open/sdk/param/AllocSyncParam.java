package cn.daxpay.open.sdk.param;

/// # 分账订单同步参数
///
/// 对照契约 6.11 节。主动向通道拉取分账单最新状态并回写本地。
/// allocNo 与 bizAllocNo 至少传一个，优先 allocNo。
public class AllocSyncParam {

    /// 平台分账单号（优先）
    private String allocNo;
    /// 商户分账单号
    private String bizAllocNo;

    public String getAllocNo() {
        return allocNo;
    }

    public AllocSyncParam setAllocNo(String allocNo) {
        this.allocNo = allocNo;
        return this;
    }

    public String getBizAllocNo() {
        return bizAllocNo;
    }

    public AllocSyncParam setBizAllocNo(String bizAllocNo) {
        this.bizAllocNo = bizAllocNo;
        return this;
    }
}
