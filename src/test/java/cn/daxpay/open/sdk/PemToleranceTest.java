package cn.daxpay.open.sdk;

import cn.daxpay.open.sdk.util.RsaSignUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/// # 密钥文本容错测试
///
/// 各种「能看不能用」的 PEM 粘贴形态（换行丢失 / NBSP / 零宽字符 / 裸 Base64）都应解析出同一把密钥，
/// 与 Go/Node/Python/PHP 版同名用例行为一致。
class PemToleranceTest {

    /// 拆出 PEM 的头行 / 正文 / 尾行
    private static String[] splitPem(String pem) {
        String[] lines = pem.trim().split("\n");
        String[] parts = new String[3];
        parts[0] = lines[0];
        parts[2] = lines[lines.length - 1];
        StringBuilder body = new StringBuilder();
        for (int i = 1; i < lines.length - 1; i++) {
            if (body.length() > 0) {
                body.append('\n');
            }
            body.append(lines[i]);
        }
        parts[1] = body.toString();
        return parts;
    }

    /// 把正文按 64 字符重新分行，行间以 sep 连接（sep 为空表示整段一行）
    private static String rewrap(String body, String sep) {
        String flat = body.replace("\n", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < flat.length(); i += 64) {
            if (sb.length() > 0) {
                sb.append(sep);
            }
            sb.append(flat, i, Math.min(i + 64, flat.length()));
        }
        return sb.toString();
    }

    @Test
    @DisplayName("公钥：各种粘贴形态都解析出同一把公钥")
    void publicKeyVariants() {
        String[] pub = splitPem(GoldenVectorTest.PUBLIC_KEY);
        String head = pub[0];
        String body = pub[1];
        String foot = pub[2];
        String flat = rewrap(body, "");
        byte[] want = RsaSignUtil.loadPublicKeyFromPem(GoldenVectorTest.PUBLIC_KEY).getEncoded();

        Map<String, String> cases = new LinkedHashMap<>();
        cases.put("标准 PEM", GoldenVectorTest.PUBLIC_KEY);
        cases.put("正文单行不换行", head + "\n" + flat + "\n" + foot);
        cases.put("正文换行→空格", head + "\n" + rewrap(body, " ") + "\n" + foot);
        cases.put("全文压成一行", head + " " + rewrap(body, " ") + " " + foot);
        cases.put("BEGIN 行尾换行→空格", head + " " + rewrap(body, "\n") + "\n" + foot);
        cases.put("END 前换行→空格", head + "\n" + rewrap(body, "\n") + " " + foot);
        cases.put("正文含 NBSP", head + "\n" + flat.substring(0, 64) + "\u00a0" + flat.substring(64) + "\n" + foot);
        cases.put("NBSP 当换行分隔符", head + "\n" + rewrap(body, "\u00a0") + "\n" + foot);
        cases.put("正文含零宽空格", head + "\n" + flat.substring(0, 64) + "\u200b" + flat.substring(64) + "\n" + foot);
        cases.put("正文含 BOM", head + "\n" + flat.substring(0, 64) + "\ufeff" + flat.substring(64) + "\n" + foot);
        cases.put("裸 Base64（无头尾标记）", flat);
        cases.put("CRLF 换行", GoldenVectorTest.PUBLIC_KEY.replace("\n", "\r\n"));
        cases.put("CR 换行", GoldenVectorTest.PUBLIC_KEY.trim().replace("\n", "\r"));
        cases.put("前后带说明文字", "这是平台公钥：\n" + GoldenVectorTest.PUBLIC_KEY + "\n请妥善保管");

        for (Map.Entry<String, String> entry : cases.entrySet()) {
            byte[] got = RsaSignUtil.loadPublicKeyFromPem(entry.getValue()).getEncoded();
            assertArrayEquals(want, got, "公钥形态解析不一致：" + entry.getKey());
        }
    }

    @Test
    @DisplayName("私钥：变形形态同样解析出同一把私钥")
    void privateKeyVariants() {
        String[] priv = splitPem(GoldenVectorTest.PRIVATE_KEY);
        String flat = rewrap(priv[1], "");
        byte[] want = RsaSignUtil.loadPrivateKeyFromPem(GoldenVectorTest.PRIVATE_KEY).getEncoded();

        Map<String, String> cases = new LinkedHashMap<>();
        cases.put("标准 PEM", GoldenVectorTest.PRIVATE_KEY);
        cases.put("全文压成一行", priv[0] + " " + rewrap(priv[1], " ") + " " + priv[2]);
        cases.put("正文含 NBSP",
                priv[0] + "\n" + flat.substring(0, 64) + "\u00a0" + flat.substring(64) + "\n" + priv[2]);
        cases.put("裸 Base64（无头尾标记）", flat);

        for (Map.Entry<String, String> entry : cases.entrySet()) {
            byte[] got = RsaSignUtil.loadPrivateKeyFromPem(entry.getValue()).getEncoded();
            assertArrayEquals(want, got, "私钥形态解析不一致：" + entry.getKey());
        }
    }

    @Test
    @DisplayName("变形公钥仍可完成验签")
    void flattenedPublicKeyStillVerifies() {
        String[] pub = splitPem(GoldenVectorTest.PUBLIC_KEY);
        String flattened = pub[0] + " " + rewrap(pub[1], " ") + " " + pub[2];
        String signStr = "amount=100&appId=APP001";
        String sign = RsaSignUtil.sign(signStr, GoldenVectorTest.PRIVATE_KEY);
        assertTrue(RsaSignUtil.verify(signStr, sign, flattened));
    }

    @Test
    @DisplayName("非法输入给出可定位的报错")
    void errorMessages() {
        assertEquals("公钥内容为空",
                assertThrows(IllegalArgumentException.class, () -> RsaSignUtil.loadPublicKeyFromPem("   "))
                        .getMessage());
        assertTrue(assertThrows(IllegalArgumentException.class,
                () -> RsaSignUtil.loadPublicKeyFromPem("这不是密钥")).getMessage().contains("不是合法的 Base64"));
        assertTrue(assertThrows(IllegalArgumentException.class,
                        () -> RsaSignUtil.loadPublicKeyFromPem("-----BEGIN PUBLIC KEY-----"))
                .getMessage().contains("未找到密钥内容"));
        assertTrue(assertThrows(IllegalArgumentException.class,
                () -> RsaSignUtil.loadPublicKeyFromPem("YWJjZGVmZ2g=")).getMessage().contains("需为 X.509"));
    }
}
