package cn.daxpay.open.sdk.result;

import java.util.List;

/// # 分账订单查询结果
///
/// 对照契约 6.9 节 AllocOrderResult（时间字段为北京时间 yyyy-MM-dd HH:mm:ss 字面量）。
public class AllocOrderResult {

    /// 平台分账单号
    private String allocNo;
    /// 商户分账单号
    private String bizAllocNo;
    /// 原支付资金交易号
    private String tradeNo;
    /// 商户业务订单号
    private String bizOrderNo;
    /// 通道分账单号
    private String outAllocNo;
    /// 分账总金额（分）
    private Long amount;
    /// 分账状态
    private String status;
    /// 分账完成时间
    private String finishTime;
    /// 支付通道
    private String channel;
    /// 商户扩展参数（原样返回）
    private String attach;
    /// 错误信息
    private String errorMsg;
    /// 分账接收方明细列表
    private List<AllocDetail> details;

    public String getAllocNo() {
        return allocNo;
    }

    public AllocOrderResult setAllocNo(String allocNo) {
        this.allocNo = allocNo;
        return this;
    }

    public String getBizAllocNo() {
        return bizAllocNo;
    }

    public AllocOrderResult setBizAllocNo(String bizAllocNo) {
        this.bizAllocNo = bizAllocNo;
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public AllocOrderResult setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public AllocOrderResult setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getOutAllocNo() {
        return outAllocNo;
    }

    public AllocOrderResult setOutAllocNo(String outAllocNo) {
        this.outAllocNo = outAllocNo;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public AllocOrderResult setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AllocOrderResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public AllocOrderResult setFinishTime(String finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public AllocOrderResult setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getAttach() {
        return attach;
    }

    public AllocOrderResult setAttach(String attach) {
        this.attach = attach;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public AllocOrderResult setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public List<AllocDetail> getDetails() {
        return details;
    }

    public AllocOrderResult setDetails(List<AllocDetail> details) {
        this.details = details;
        return this;
    }

    /// # 分账接收方明细（单个）
    public static class AllocDetail {

        /// 接收方类型
        private String receiverType;
        /// 接收方账号
        private String receiverAccount;
        /// 接收方姓名
        private String receiverName;
        /// 分账金额（分）
        private Long amount;
        /// 该接收方分账结果
        private String result;
        /// 错误信息
        private String errorMsg;
        /// 完成时间
        private String finishTime;

        public String getReceiverType() {
            return receiverType;
        }

        public AllocDetail setReceiverType(String receiverType) {
            this.receiverType = receiverType;
            return this;
        }

        public String getReceiverAccount() {
            return receiverAccount;
        }

        public AllocDetail setReceiverAccount(String receiverAccount) {
            this.receiverAccount = receiverAccount;
            return this;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public AllocDetail setReceiverName(String receiverName) {
            this.receiverName = receiverName;
            return this;
        }

        public Long getAmount() {
            return amount;
        }

        public AllocDetail setAmount(Long amount) {
            this.amount = amount;
            return this;
        }

        public String getResult() {
            return result;
        }

        public AllocDetail setResult(String result) {
            this.result = result;
            return this;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public AllocDetail setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public AllocDetail setFinishTime(String finishTime) {
            this.finishTime = finishTime;
            return this;
        }
    }
}
