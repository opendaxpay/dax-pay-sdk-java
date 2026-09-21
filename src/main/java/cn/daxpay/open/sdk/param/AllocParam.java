package cn.daxpay.open.sdk.param;

import java.util.List;

/// # 分账请求参数
///
/// 对照契约 6.8 节。原支付订单须在**下单时声明** `allocation=true`（分账订单），否则通道拒绝分账。
/// 接收方列表直接传入完整明细（极简模式，接收方绑定由调用方提前在通道侧完成）。
/// 金额单位为**分**，公共字段由 DaxPayClient 注入。
public class AllocParam {

    /// 商户分账单号（幂等键，同一应用下唯一，必填）
    private String bizAllocNo;
    /// 原支付资金交易号（与 bizOrderNo 二选一，优先本字段）
    private String tradeNo;
    /// 原支付商户业务订单号
    private String bizOrderNo;
    /// 分账标题
    private String title;
    /// 分账描述
    private String description;
    /// 接收方列表（至少一个，必填）
    private List<Receiver> receivers;
    /// 商户扩展参数，回调时原样返回
    private String attach;
    /// 异步通知地址
    private String notifyUrl;

    public String getBizAllocNo() {
        return bizAllocNo;
    }

    public AllocParam setBizAllocNo(String bizAllocNo) {
        this.bizAllocNo = bizAllocNo;
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public AllocParam setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public AllocParam setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AllocParam setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AllocParam setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Receiver> getReceivers() {
        return receivers;
    }

    public AllocParam setReceivers(List<Receiver> receivers) {
        this.receivers = receivers;
        return this;
    }

    public String getAttach() {
        return attach;
    }

    public AllocParam setAttach(String attach) {
        this.attach = attach;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public AllocParam setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    /// # 分账接收方（单个）
    ///
    /// 接收方类型取值见平台 `AllocReceiverTypeEnum`：
    /// MERCHANT_ID（商户号）/ PERSONAL_OPENID（个人 openid）/ PERSONAL_SUB_OPENID（子商户 openid）/
    /// USER_ID（支付宝用户 ID）/ LOGIN_NAME（支付宝登录号）。
    public static class Receiver {

        /// 接收方类型（必填）
        private String receiverType;
        /// 接收方账号（必填）
        private String receiverAccount;
        /// 接收方姓名（部分通道/类型必填）
        private String receiverName;
        /// 分账金额（分，必填）
        private Long amount;

        public String getReceiverType() {
            return receiverType;
        }

        public Receiver setReceiverType(String receiverType) {
            this.receiverType = receiverType;
            return this;
        }

        public String getReceiverAccount() {
            return receiverAccount;
        }

        public Receiver setReceiverAccount(String receiverAccount) {
            this.receiverAccount = receiverAccount;
            return this;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public Receiver setReceiverName(String receiverName) {
            this.receiverName = receiverName;
            return this;
        }

        public Long getAmount() {
            return amount;
        }

        public Receiver setAmount(Long amount) {
            this.amount = amount;
            return this;
        }
    }
}
