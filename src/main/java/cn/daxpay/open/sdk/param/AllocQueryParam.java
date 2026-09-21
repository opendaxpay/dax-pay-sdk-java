package cn.daxpay.open.sdk.param;

/// # 分账订单查询参数
///
/// 对照契约 6.9 节（allocNo 与 bizAllocNo 至少传一个，优先 allocNo）。
/// 仅查询本地分账单，不调用通道；需实时通道状态走分账同步接口。
public class AllocQueryParam {

    /// 平台分账单号（优先）
    private String allocNo;
    /// 商户分账单号
    private String bizAllocNo;

    public String getAllocNo() {
        return allocNo;
    }

    public AllocQueryParam setAllocNo(String allocNo) {
        this.allocNo = allocNo;
        return this;
    }

    public String getBizAllocNo() {
        return bizAllocNo;
    }

    public AllocQueryParam setBizAllocNo(String bizAllocNo) {
        this.bizAllocNo = bizAllocNo;
        return this;
    }
}
