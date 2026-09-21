package cn.daxpay.open.sdk.result;

/// # 分账响应结果
///
/// 对照契约 6.8 节 AllocResult。
public class AllocResult {

    /// 平台分账单号
    private String allocNo;
    /// 商户分账单号
    private String bizAllocNo;
    /// 分账状态
    private String status;
    /// 错误信息（失败时返回）
    private String errorMsg;

    public String getAllocNo() {
        return allocNo;
    }

    public AllocResult setAllocNo(String allocNo) {
        this.allocNo = allocNo;
        return this;
    }

    public String getBizAllocNo() {
        return bizAllocNo;
    }

    public AllocResult setBizAllocNo(String bizAllocNo) {
        this.bizAllocNo = bizAllocNo;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AllocResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public AllocResult setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
