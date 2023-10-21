package com.yapi.yapiinterface;

import com.yapi.yapiinterface.client.YapiClient;
import com.yapi.yapiinterface.model.User;

/**
 * @author Yuuue
 * creat by 2023-08-29
 */
public class Main {
    public static String aKey="yupi";

    public static String sKey="qwertyuiop";

    public static void main(String[] args) {
        YapiClient yapiClient = new YapiClient(aKey,sKey);
//        String byName = yapiClient.getByName("11");
//        System.out.println(byName);
        User user = new User();
        user.setName("siv");
        String byNamePost = yapiClient.getByNamePost(user);
//        System.out.println(byNamePost);
    }
}
