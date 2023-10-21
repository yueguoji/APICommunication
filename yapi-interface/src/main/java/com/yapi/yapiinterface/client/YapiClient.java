package com.yapi.yapiinterface.client;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import com.yapi.yapiinterface.model.User;
import org.apache.tomcat.util.digester.Digester;
import org.springframework.util.DigestUtils;


import java.util.HashMap;
import java.util.Map;

import java.util.Random;

import static com.yapi.yapiinterface.utils.SignUtils.getSign;

/**
 * @author Yuuue
 * creat by 2023-08-29
 */
public class YapiClient {
    private String accessKey ;

    private String secretKey;

    public YapiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public YapiClient() {
    }

    public String getByName(String name){
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        String s = HttpUtil.get("http://localhost:8123/api/user/",map);
        System.out.println(s);
        return s;
    }
    public Map<String,String> getSignKey(User user){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        hashMap.put("body",JSONUtil.toJsonStr(user));
        hashMap.put("nonce", new Random(5).toString());
//        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        hashMap.put("sign",getSign(user,secretKey));
        return hashMap;
    }



    public Map<String,String> getKey(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        hashMap.put("secretKey",secretKey);

        return hashMap;
    }

    public String getByNamePost(User user){
        System.out.println(user);
        String userStr = JSONUtil.toJsonStr(user);
        System.out.println(userStr);

        String result2 = HttpRequest.post("http://localhost:8123/api/user/name")
                .addHeaders(getSignKey(user))
                .body(userStr)
                .execute().body();
        System.out.println(result2);
        return result2;
    }




}
