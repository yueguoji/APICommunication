package com.yapi.yapi0clientsdk.utils;

import com.google.gson.Gson;
import com.yapi.yapi0clientsdk.model.User;
import org.springframework.util.DigestUtils;

/**
 * @author Yuuue
 * creat by 2023-08-29
 */
public class SignUtils {

    public static String getSign(User user, String secretKey){

        String s = user.toString()+secretKey;
        return DigestUtils.md5DigestAsHex(s.getBytes());

    }

    public static String getSign1(String body, String secretKey){
        Gson gson = new Gson();
        User user = gson.fromJson(body, User.class);
        String s = user.getName()+secretKey;
        return DigestUtils.md5DigestAsHex(s.getBytes());

    }
}
