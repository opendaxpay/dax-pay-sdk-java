package cn.daxpay.open.sdk.util;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/// # Json 签名字符串工具类
///
/// 移植自后端 [cn.daxpay.open.payment.common.util.JsonSignStrUtil]。
/// 输入 JSON 字符串 → 扁平化排序Map → 签名字符串
public final class JsonSignStrUtil {

    private JsonSignStrUtil() {
    }

    /// 生成待签名字符串
    public static String buildSignStr(String jsonStr) {
        return buildSignStr(buildSortedMap(jsonStr));
    }

    /// 将 JSON 字符串扁平化为 TreeMap（已按 ASCII 排序）
    public static TreeMap<String, String> buildSortedMap(String jsonStr) {
        if (StrUtil.isBlank(jsonStr)) {
            throw new IllegalArgumentException("JSON 不能为空");
        }
        // hutool 默认将数字解析为 BigDecimal，与后端一致
        Map<String, Object> root = JSONUtil.toBean(jsonStr, new TypeReference<Map<String, Object>>() {
        }, true);
        Map<String, String> flatMap = new LinkedHashMap<>();
        flatten("", root, flatMap);
        return new TreeMap<>(flatMap);
    }

    /// 递归扁平化（对照后端 JsonSignStrUtil#flatten）
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void flatten(String prefix, Object value, Map<String, String> result) {
        if (value == null) {
            return;
        }
        // BigDecimal 去尾零（避免 100.00 vs 100）
        if (value instanceof BigDecimal) {
            result.put(prefix, ((BigDecimal) value).stripTrailingZeros().toPlainString());
            return;
        }
        // Number（Integer/Long/Double 等）toString
        if (value instanceof Number) {
            result.put(prefix, value.toString());
            return;
        }
        // Boolean
        if (value instanceof Boolean) {
            result.put(prefix, value.toString());
            return;
        }
        // String 原样
        if (value instanceof String) {
            result.put(prefix, (String) value);
            return;
        }
        // List 用 [i]
        if (value instanceof List) {
            List<Object> list = (List<Object>) value;
            for (int i = 0; i < list.size(); i++) {
                flatten(prefix + "[" + i + "]", list.get(i), result);
            }
            return;
        }
        // Map 用 .key
        if (value instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) value;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = StrUtil.isBlank(prefix) ? entry.getKey() : prefix + "." + entry.getKey();
                flatten(key, entry.getValue(), result);
            }
            return;
        }
        result.put(prefix, value.toString());
    }

    /// 生成待签名字符串（排除 sign 字段，大小写不敏感）
    public static String buildSignStr(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if ("sign".equalsIgnoreCase(entry.getKey())) {
                continue;
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
