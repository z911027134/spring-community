package com.spring.community.tool;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {

    private String mode;

    /**
     * 定义加密方式
     * MAC算法可选以下多种算法
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     */
    public HMAC(HMACEnum hmacEnum) {
        this.mode = hmacEnum.getMode();
    }

    /**
     * 生成签名数据_HmacSHA1加密
     * @param data 待加密的数据
     * @param key 加密使用的key
     */
    public String getSignatureDigest(String data, String key) throws Exception {
        byte[] rawHmac = getSignatureRaw(data, key);
        return byte2hex(rawHmac);
    }

    public byte[] getSignatureRaw(String data, String key) throws Exception {
        byte[] keyBytes = key.getBytes();
        // 根据给定的字节数组构造一个密钥。
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, this.mode);
        Mac mac = Mac.getInstance(this.mode);
        mac.init(signingKey);
        return mac.doFinal(data.getBytes());
    }

    private static String byte2hex(final byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp = "";

        for (byte i:b) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式。
            stmp = (java.lang.Integer.toHexString(i & 0xFF));
            if (stmp.length() == 1) {
                hs.append("0");
                hs.append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }
}
