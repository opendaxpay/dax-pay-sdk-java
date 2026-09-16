package cn.daxpay.open.sdk.response;

/// # 统一响应参数
///
/// 对照后端 [cn.daxpay.open.payment.common.result.DaxResult]。
/// 注意字段名 msg（非 message，后者属管理 API 的 Result）。
public class DaxResult<T> {

    /// 状态码，0 成功
    private int code;

    /// 提示信息
    private String msg;

    /// 业务内容
    private T data;

    /// 签名（Base64）
    private String sign;

    /// 响应时间（北京时间 yyyy-MM-dd HH:mm:ss）
    private String resTime;

    /// 请求 ID（回显入参 reqId）
    private String reqId;

    public DaxResult() {
    }

    public int getCode() {
        return code;
    }

    public DaxResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public DaxResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public DaxResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public DaxResult<T> setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public String getResTime() {
        return resTime;
    }

    public DaxResult<T> setResTime(String resTime) {
        this.resTime = resTime;
        return this;
    }

    public String getReqId() {
        return reqId;
    }

    public DaxResult<T> setReqId(String reqId) {
        this.reqId = reqId;
        return this;
    }
}
