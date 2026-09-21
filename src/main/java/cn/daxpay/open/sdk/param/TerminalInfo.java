package cn.daxpay.open.sdk.param;

/// # 终端信息
///
/// 对照契约 6.6 节。线下 POS / 收银台场景上报的设备与门店上下文，挂在 PayParam 上。
public class TerminalInfo {

    /// 终端设备号
    private String terminalNo;
    /// 门店编号
    private String storeNo;
    /// 操作员号
    private String operatorId;
    /// 设备名称
    private String deviceName;
    /// 设备 IP 地址
    private String deviceIp;
    /// 经度
    private Double longitude;
    /// 纬度
    private Double latitude;

    public String getTerminalNo() {
        return terminalNo;
    }

    public TerminalInfo setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
        return this;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public TerminalInfo setStoreNo(String storeNo) {
        this.storeNo = storeNo;
        return this;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public TerminalInfo setOperatorId(String operatorId) {
        this.operatorId = operatorId;
        return this;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public TerminalInfo setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public TerminalInfo setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public TerminalInfo setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public TerminalInfo setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }
}
