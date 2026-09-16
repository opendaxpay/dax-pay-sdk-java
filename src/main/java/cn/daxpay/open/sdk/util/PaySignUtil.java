package cn.daxpay.open.sdk.util;

import java.util.TreeMap;
import java.util.Objects;

/// # 签名/验签入口
///
/// 统一走 JSON 路径（对照后端 [PaySignUtil] 的 verify(json)）。
/// 平台按报文规范字面量构造签名串：时间字段为北京时间 `yyyy-MM-dd HH:mm:ss`，
/// 故 SDK 必须对**将要发送的 JSON 字符串**签名，保证签名串与发送报文逐字节一致。
/// 请勿先反序列化为对象再签名——对象扁平化会丢失原始字面量（数字精度、时间格式）。
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
        if (Objects.isNull(sign) || sign.isEmpty()) {
            return false;
        }
        String signStr = JsonSignStrUtil.buildSignStr(map);
        return RsaSignUtil.verify(signStr, sign, publicKeyContent);
    }
}
