package com.example.back.utils;

import java.util.UUID;

/**
 *
 * @项目: mail--cc.ccoder.mail.utils
 * @TODO： 生成随机字符串的工具类 uuid
 */
public class UuidUtil {

    public static String getUUID(){
         return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println("格式前的UUID ： " + UUID.randomUUID().toString());
        System.out.println("格式化后的UUID ：" + getUUID());
    }
}
