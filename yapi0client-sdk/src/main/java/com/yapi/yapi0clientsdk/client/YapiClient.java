package com.yapi.yapi0clientsdk.client;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yapi.yapi0clientsdk.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.yapi.yapi0clientsdk.utils.SignUtils.getSign;
import static com.yapi.yapi0clientsdk.utils.SignUtils.getSign1;


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
        hashMap.put("sign",getSign1(JSONUtil.toJsonStr(user),secretKey));
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

        String result2 = HttpRequest.post("http://localhost:8090/api/user/name")
                .addHeaders(getSignKey(user))
                .body(userStr)
                .execute().body();
        System.out.println(result2);
        return result2;
    }

    public String getListMax(List<Integer> list,User user){
        System.out.println(user);
        String userStr = JSONUtil.toJsonStr(user);
        System.out.println(userStr);

        String result2 = HttpRequest.post("http://localhost:8090/api/list/max")
                .addHeaders(getSignKey(user))
                .body(list.toString())
                .execute().body();
        System.out.println(result2);
        return result2;
    }

}
