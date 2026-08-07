package cn.daxpay.open.sdk.util;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/// # RSA 签名工具类
///
/// 移植自后端 [cn.daxpay.open.platform.core.util.RsaSignUtil]。
/// SHA256withRSA / PKCS#8 私钥 / X.509 公钥 / Base64 输出。
/// 与后端差异：显式使用 UTF-8（后端 data.getBytes() 依赖平台默认，生产 Linux 为 UTF-8）。
public final class RsaSignUtil {

    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    private RsaSignUtil() {
    }

    /// 读取 PKCS#8 私钥（兼容旧 RSA PRIVATE KEY 头）
    public static PrivateKey loadPrivateKeyFromPem(String pemContent) {
        String privateKeyPEM = pemContent
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
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
        String publicKeyPEM = pemContent
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
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
