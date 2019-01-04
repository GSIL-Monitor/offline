package common.util;

import common.log.CLogger;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Utils {
    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return  MD5加密后的字符串
     */
    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            CLogger.error("getMD5",e);
            return null;
        }
    }
}
