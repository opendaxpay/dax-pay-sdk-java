package cn.daxpay.open.sdk.util;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.regex.Pattern;

/// # RSA 签名工具类
///
/// 移植自后端 [cn.daxpay.open.platform.core.util.RsaSignUtil]。
/// SHA256withRSA / PKCS#8 私钥 / X.509 公钥 / Base64 输出。
/// 与后端差异：显式使用 UTF-8（后端 data.getBytes() 依赖平台默认，生产 Linux 为 UTF-8）。
public final class RsaSignUtil {

    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /// 全部空白与不可见字符。UNICODE_CHARACTER_CLASS 让 \s 覆盖 Unicode 空白（含 NBSP U+00A0）；
    /// 零宽空格 / 零宽连接符 / 词连接符 / BOM / 软连字符不属于空白，需单独列出
    private static final Pattern INVISIBLE = Pattern.compile(
            "[\\s\\u200b\\u200c\\u200d\\u2060\\ufeff\\u00ad]+", Pattern.UNICODE_CHARACTER_CLASS);

    /// Base64 字母表
    private static final Pattern BASE64_BODY = Pattern.compile("[A-Za-z0-9+/]+");

    private RsaSignUtil() {
    }

    /// 剥掉 -----BEGIN xxx----- / -----END xxx----- 标记，返回中间内容
    /// （标记之外的前后说明文字一并丢弃，与其它语言版本一致）
    private static String stripArmor(String text) {
        String body = text;
        int begin = body.indexOf("-----BEGIN");
        if (begin >= 0) {
            body = body.substring(begin + "-----BEGIN".length());
            // 跳过 " xxx-----" 到起始标记结束
            int close = body.indexOf("-----");
            if (close >= 0) {
                body = body.substring(close + "-----".length());
            }
        }
        int end = body.indexOf("-----END");
        if (end >= 0) {
            body = body.substring(0, end);
        }
        return body;
    }

    /// 从任意形态的密钥文本中提取 DER。
    ///
    /// 从网页 / 聊天窗口 / PDF / IDE 复制 PEM 时，正文换行常被替换成空格或不换行空格（NBSP），
    /// 或整段压成一行、混入零宽字符，甚至只剩裸 Base64。这里统一归一：剥掉头尾标记、
    /// 剔除全部空白与不可见字符，再按 Base64（含无填充变体）解出 DER
    /// （与 Go/Node/Python/PHP 版同一套行为，见 Go 版 `daxpay/pem.go`）。
    private static byte[] decodeKeyContent(String pemContent, String label) {
        if (pemContent == null || pemContent.trim().isEmpty()) {
            throw new IllegalArgumentException(label + "内容为空");
        }
        String body = INVISIBLE.matcher(stripArmor(pemContent)).replaceAll("");
        if (body.isEmpty()) {
            throw new IllegalArgumentException("未找到密钥内容，请确认已完整复制 PEM（含 BEGIN / END 两行）");
        }
        String unpadded = body.replaceAll("=+$", "");
        if (!BASE64_BODY.matcher(unpadded).matches() || unpadded.length() % 4 == 1) {
            throw new IllegalArgumentException("密钥内容不是合法的 Base64，请确认复制完整且未混入其它字符");
        }
        // 部分来源会去掉 Base64 末尾的填充等号，这里补齐后再解码
        return Base64.getDecoder().decode(unpadded + "=".repeat((4 - unpadded.length() % 4) % 4));
    }

    /// 读取 PKCS#8 私钥（兼容旧 RSA PRIVATE KEY 头）
    public static PrivateKey loadPrivateKeyFromPem(String pemContent) {
        byte[] decoded = decodeKeyContent(pemContent, "私钥");
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("私钥解析失败，需为 PKCS#8 格式的 RSA 私钥", e);
        }
    }

    /// 读取 X.509 公钥
    public static PublicKey loadPublicKeyFromPem(String pemContent) {
        byte[] decoded = decodeKeyContent(pemContent, "公钥");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(keySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("公钥解析失败，需为 X.509 格式的 RSA 公钥", e);
        }
    }

    /// 私钥签名（SHA256withRSA, UTF-8, Base64）
    public static String sign(String data, String privateKeyContent) {
        try {
            PrivateKey privateKey = loadPrivateKeyFromPem(privateKeyContent);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signedBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signedBytes);
        } catch (Exception e) {
            throw new RuntimeException("RSA 签名失败", e);
        }
    }

    /// 公钥验签
    public static boolean verify(String data, String sign, String publicKeyContent) {
        try {
            PublicKey publicKey = loadPublicKeyFromPem(publicKeyContent);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] sigBytes = Base64.getDecoder().decode(sign);
            return signature.verify(sigBytes);
        } catch (Exception e) {
            return false;
        }
    }
}
