package com.website.weily.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.Random;

/**
 * @Description 加密
 * @Author youjianzhao
 * @Date 2020/1/4 15:32
 * @Version
 */
@Slf4j
public class EncryptionUtil {


    /**
     * 注册用户时，获取随机参数的盐值
     *
     * @return
     */
    public static String getMd5HashSalt(int length) {
        String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        //2.  由Random生成随机数
        Random random = new Random();
        //3.  长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //从62个的数字或字母中选择
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    /**
     * 注册用户时使用该方法
     *
     * @param password
     * @return
     */
    public static String md5Hash(String password, String salt) {
        String md5Password = new Md5Hash(password, salt, 2).toString();
        return md5Password;
    }
}
