package com.ctrip.train.kefu.system.offline.common.utils;
import common.log.CLogger;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.Charsets;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:常用加密算法帮助类
 * @Author:xiao_f
 * @Date:2017/11/29
 **/
public class EncryptUtil {

    private static final String KEY = "77ab70151a0502fe454a5927098615e6";

    private static final int KEY_LENGTH = 24;

    /**
     * MD5加密算法加密
     *
     * @param strSrc
     * @return
     */
    public static String md5Encrypt(String strSrc) {
        return encrypt(strSrc, "MD5");
    }

    /**
     * SHA1加密算法加密
     *
     * @param strSrc
     * @return
     */
    public static String sha1Encrypt(String strSrc) {
        return encrypt(strSrc, "SHA-1");
    }

    /**
     * SHA256加密算法加密
     *
     * @param strSrc
     * @return
     */
    public static String sha256Encrypt(String strSrc) {
        return encrypt(strSrc, "SHA-256");
    }

    /**
     * SHA512加密算法加密
     *
     * @param strSrc
     * @return
     */
    public static String sha512Encrypt(String strSrc) {
        return encrypt(strSrc, "SHA-512");
    }

    /**
     * Base64加密算法加密
     *
     * @param strSrc
     * @return
     */
    public static String base64Encrypt(String strSrc) {
        if (StringUtils.isEmpty(strSrc))
            return null;
        byte[] bytes = null;
        String des = null;
        try {
            bytes = strSrc.getBytes("utf-8");
            if (null != bytes) {
                des = new BASE64Encoder().encode(bytes);
            }
        } catch (UnsupportedEncodingException e) {
            CLogger.warn("Base64加密出错", "待加密字符串" + strSrc);
        }
        return des;
    }

    /**
     * Base64加密算法
     *
     * @param bytes 字节流
     * @return
     */
    public static String base64Encrypt(byte[] bytes) {
        String des = null;
        if (null != bytes) {
            des = new BASE64Encoder().encode(bytes);
        }
        return des;
    }

    /**
     * MD5加密时候再进行Base64加密
     *
     * @param strSrc
     * @return
     */
    public static String md5ToBase64String(String strSrc) {
        String res = "";
        byte[] bytesMd5 = DigestUtils.md5(strSrc);
        Base64 base64 = new Base64();
        byte[] byteBase64 = base64.encode(bytesMd5);
        res = new String(byteBase64);
        return res;
    }


    /**
     * Base64加密算法解密
     *
     * @param encryptStr 加密的字符串
     * @return
     */
    public static String getFromBase64(String encryptStr) {
        byte[] bytes = null;
        String result = null;
        if (null != encryptStr) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                bytes = decoder.decodeBuffer(encryptStr);
                result = new String(bytes, "utf-8");
            } catch (IOException e) {
                CLogger.warn("Base64加密算法解密出错", "待解密字符串" + encryptStr);
            }
        }
        return result;
    }

    /**
     * DES3 CBC加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptDESCBC(String data, String key) {
        if (StringUtils.isEmpty(key)) {
            key = KEY;
        }

        String md5 = md5Encrypt(key).toLowerCase();
        byte[] bufKey = md5.substring(0, KEY_LENGTH).getBytes(Charsets.US_ASCII);
        byte[] bufIV = md5.substring(KEY_LENGTH).getBytes(Charsets.US_ASCII);

        try {
            DESedeKeySpec spec = new DESedeKeySpec(bufKey);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key desKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bufIV);
            cipher.init(Cipher.ENCRYPT_MODE, desKey, ivParameterSpec);
            return base64Encrypt(cipher.doFinal(data.getBytes(Charsets.US_ASCII)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            CLogger.warn("加密错误", "加密错误" + e);
            return "";
        }
    }

    public static String decryptDESCBC(String data, String key) {
        if (StringUtils.isEmpty(key)) {
            key = KEY;
        }


        String md5 = md5Encrypt(key).toLowerCase();
        byte[] bufKey = md5.substring(0, KEY_LENGTH).getBytes(Charsets.US_ASCII);
        byte[] bufIV = md5.substring(KEY_LENGTH).getBytes(Charsets.US_ASCII);

        try {

            byte [] dStr = new BASE64Decoder().decodeBuffer(data);
            DESedeKeySpec spec = new DESedeKeySpec(bufKey);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key desKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bufIV);
            cipher.init(Cipher.DECRYPT_MODE, desKey, ivParameterSpec);
            return new String(cipher.doFinal(dStr));
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | IOException e) {
            CLogger.warn("解密错误", "解密错误" + e);
            return "";
        }
    }


    /**
     * 私有加密方法
     *
     * @param strSrc  源字符串
     * @param encName 加密算法名称
     * @return
     */
    private static String encrypt(String strSrc, String encName) {
        if (StringUtils.isEmpty(strSrc))
            return null;
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            if (null == encName || encName.equals("")) {
                encName = "MD5";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            Map<String, String> map = new HashMap<>();
            map.put("strSrc", strSrc);
            map.put("encName", encName);
            CLogger.error("加密出错:", e, map);
        }
        return strDes;
    }

    /**
     * 字节数组转Hex
     *
     * @param bytes
     * @return
     */
    public static String bytes2Hex(byte[] bytes) {
        String des = null;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hexString = (Integer.toHexString(bytes[i] & 0xFF));
            if (hexString.length() == 1) {
                stringBuffer.append('0');
            }
            stringBuffer.append(hexString);
        }
        des = stringBuffer.toString();
        return des;
    }


}
