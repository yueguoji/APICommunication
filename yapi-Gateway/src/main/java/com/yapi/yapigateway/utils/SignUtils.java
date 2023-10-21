package com.yapi.yapigateway.utils;

import com.yapi.yapi0clientsdk.model.User;
import org.springframework.util.DigestUtils;

public class SignUtils {

    public static String getSign(String body, String secretKey){


        String s = body+secretKey;
        return DigestUtils.md5DigestAsHex(body.getBytes());

    }
}
