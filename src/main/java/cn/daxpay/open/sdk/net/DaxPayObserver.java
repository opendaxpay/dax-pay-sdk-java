package cn.daxpay.open.sdk.net;

/// # SDK 调用观测接口
///
/// 联调/排障场景使用：挂在 [DaxPayClient] 上可拿到每次调用
/// 「签名后的完整请求体」与「平台原始响应体」，便于与后端日志逐字对照。
/// 不设置则零开销，不影响正常调用链。
public interface DaxPayObserver {

    /// 请求已签名待发出（signedJson 为含 sign 字段的完整请求 JSON）
    void onRequest(String signedJson);

    /// 收到平台原始响应体（在响应验签**之前**回调，验签失败时也可拿到原文）
    void onResponse(String rawBody);
}
