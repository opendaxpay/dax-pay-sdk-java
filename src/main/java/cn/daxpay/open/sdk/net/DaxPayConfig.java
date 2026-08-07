package cn.daxpay.open.sdk.net;

import cn.hutool.core.util.StrUtil;

/// # SDK 配置
///
/// 移植自商业版 DaxPayConfig，对照 sdk-contract.md 第十节
public class DaxPayConfig {

    /// 服务地址（自动去尾斜杠）
    private String serviceUrl;
    /// 商户号
    private String mchNo;
    /// 应用号（可选）
    private String appId;
    /// 商户私钥 PEM（PKCS#8）
    private String privateKey;
    /// 平台公钥 PEM（X.509）
    private String publicKey;
    /// 请求超时毫秒，默认 30000
    private int reqTimeout = 30000;

    public String getServiceUrl() {
        return StrUtil.removeSuffix(serviceUrl, "/");
    }

    public DaxPayConfig setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
        return this;
    }

    public String getMchNo() {
        return mchNo;
    }

    public DaxPayConfig setMchNo(String mchNo) {
        this.mchNo = mchNo;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public DaxPayConfig setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public DaxPayConfig setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public DaxPayConfig setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public int getReqTimeout() {
        return reqTimeout;
    }

    public DaxPayConfig setReqTimeout(int reqTimeout) {
        this.reqTimeout = reqTimeout;
        return this;
    }
}
