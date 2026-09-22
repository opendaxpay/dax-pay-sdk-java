package cn.daxpay.open.sdk.result;

/// # 签名自检探针结果
/// 对照契约 6.14 节（回显平台侧解析结果，供对接方核对商户身份与签名串构造）
public class PingResult {
    private String mchNo;
    private String appId;
    private boolean appFromDefault;
    private String serverSignStr;

    public String getMchNo() { return mchNo; }
    public PingResult setMchNo(String mchNo) { this.mchNo = mchNo; return this; }
    public String getAppId() { return appId; }
    public PingResult setAppId(String appId) { this.appId = appId; return this; }
    public boolean isAppFromDefault() { return appFromDefault; }
    public PingResult setAppFromDefault(boolean appFromDefault) { this.appFromDefault = appFromDefault; return this; }
    public String getServerSignStr() { return serverSignStr; }
    public PingResult setServerSignStr(String serverSignStr) { this.serverSignStr = serverSignStr; return this; }
}
