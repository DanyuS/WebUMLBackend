package nju.edu.uml.webumldesigner.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");//获取MD5实例
            md.update(str.getBytes());//传入要加密的byte类型值
            byte[] digest = md.digest();//得到md5加密后的byte类型值

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                int temp = digest[i];
                if (temp < 0) {
                    temp += 256;
                }
                if (temp < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(temp));//转化为16进制
            }
            return sb.toString().substring(0, 32);//从下标0开始截取长度为32的值
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
