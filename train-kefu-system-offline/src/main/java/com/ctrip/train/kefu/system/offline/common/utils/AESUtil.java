package com.ctrip.train.kefu.system.offline.common.utils;

import common.log.CLogger;
import common.util.StringUtils;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * AES加密
 */
public class AESUtil {
    private static final String defaultCharset = "UTF-8";
    private static final String KEY_AES = "AES";
    private static final String strVector = "09,.34ajoydfuEEi";

//    public static String encrypt(String data, String key) {
//        return doAES(data, key, Cipher.ENCRYPT_MODE);
//    }
//
//    public static String decrypt(String data, String key) {
//        return doAES(data, key, Cipher.DECRYPT_MODE);
//    }

    public static  String aesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * 加解密
     *
     * @param data
     * @param key
     * @param mode
     * @return
     */
    private static String doAES(String data, String key, int mode) {
        try {
            if (StringUtils.isBlank(data) || StringUtils.isBlank(key)) {
                return null;
            }

            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            if (encrypt) {
                content = data.getBytes(defaultCharset);
            } else {
                content = Base64.decodeBase64(data.getBytes(defaultCharset));
            }
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            // 创建密钥
            SecretKeySpec keySpec = new SecretKeySpec(md5Digest.digest(key.getBytes(defaultCharset)), KEY_AES);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(KEY_AES);
            // 初始化
            cipher.init(mode, keySpec);

            byte[] result = cipher.doFinal(content);

            if (encrypt) {
                return new String(Base64.encodeBase64(result));
            } else {
                return new String(result, defaultCharset);
            }
        } catch (Exception e) {
            CLogger.error("doAES exception", e);
        }
        return null;
    }

    /**
     * AES解密
     * @param encrypted
     * @param key
     * @return
     */
    public static String decrypt(String encrypted, String key) {
        try {
            byte[] e = key.getBytes("utf-8");
            System.out.println("[decrypt].net使用秘钥：" + EncryptUtil.base64Encrypt(e));
            SecretKeySpec skeySpec = new SecretKeySpec(e, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(strVector.getBytes("utf-8")));
            byte[] decrypted = cipher.doFinal(EncryptUtil.getFromBase64(encrypted).getBytes());
            String content=new String(decrypted);
            content = content.replaceAll(System.getProperty("line.separator"), "");
            return content.trim();
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }


    /**
     * AES加密
     * @param text
     * @param key
     * @return
     */
    public static String encrypt(String text, String key) {
        try {
            // byte[] e = getRawKey(key.getBytes("utf-8"));
            byte[] e = key.getBytes("utf-8");
            System.out.println("[encrypt].net使用秘钥：" + EncryptUtil.base64Encrypt(e));
            SecretKeySpec skeySpec = new SecretKeySpec(e, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = new byte[cipher.getBlockSize()];
            cipher.init(1, skeySpec, new IvParameterSpec(strVector.getBytes("utf-8")));
            byte[] encrypted = cipher.doFinal(text.getBytes("utf-8"));
            String content = EncryptUtil.base64Encrypt(encrypted);
            content = content.replaceAll(System.getProperty("line.separator"), "");
            return content.trim();
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }
//
//    public static String encrypt(String text, String key) {
//        try {
//            // byte[] e = getRawKey(key.getBytes("utf-8"));
//            byte[] e = key.getBytes("utf-8");
//            System.out.println("[encrypt].net使用秘钥：" + base64Encode(e));
//            SecretKeySpec skeySpec = new SecretKeySpec(e, "AES");
//            // 创建密码器
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            byte[] bytes = new byte[cipher.getBlockSize()];
//            cipher.init(1, skeySpec, new IvParameterSpec(strVector.getBytes("utf-8")));
//            byte[] encrypted = cipher.doFinal(text.getBytes("utf-8"));
//            String content = base64Encode(encrypted);
//            content = content.replaceAll(System.getProperty("line.separator"), "");
//            return content.trim();
//        } catch (Exception var5) {
//            var5.printStackTrace();
//            return null;
//        }
//    }



}
