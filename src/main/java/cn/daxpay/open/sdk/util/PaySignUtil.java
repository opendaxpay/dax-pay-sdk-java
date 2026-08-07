package cn.daxpay.open.sdk.util;

import java.util.TreeMap;

/// # 签名/验签入口
///
/// 统一走 JSON 路径（对照后端 [PaySignUtil] 的 verify(json)）。
/// 开源版 reqTime 为 OffsetDateTime，对象扁平化得 UTC ISO，与 JSON 序列化的 GMT+8 字面量不一致，
/// 故 SDK 必须走 JSON 签名路径，保证签名串与发送 JSON 一致（详见 sdk-test-vectors.md 第二节陷阱）。
public final class PaySignUtil {

    private PaySignUtil() {
    }

    /// 签名（对 JSON 字符串签名）
    public static String sign(String json, String privateKeyContent) {
        String signStr = JsonSignStrUtil.buildSignStr(json);
        return RsaSignUtil.sign(signStr, privateKeyContent);
    }

    /// 验签（对 JSON 字符串验签）
    public static boolean verify(String json, String publicKeyContent) {
        TreeMap<String, String> map = JsonSignStrUtil.buildSortedMap(json);
        String sign = map.remove("sign");
        if (sign == null || sign.isEmpty()) {
            return false;
        }
        String signStr = JsonSignStrUtil.buildSignStr(map);
        return RsaSignUtil.verify(signStr, sign, publicKeyContent);
    }
}
