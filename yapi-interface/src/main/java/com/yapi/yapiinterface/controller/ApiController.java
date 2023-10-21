package com.yapi.yapiinterface.controller;

import cn.hutool.json.JSONUtil;
import com.yapi.yapiinterface.model.User;
import com.yapi.yapiinterface.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

/**
 * @author Yuuue
 * creat by 2023-08-28
 */
@RestController
@RequestMapping("/user")
public class ApiController {

    @GetMapping("/")
    public String getByName(String name){
        return "get name"+name;
    }


    @PostMapping("/name")
    public String getByNamePost(@RequestBody User user, HttpServletRequest request){
//        String accessKey = request.getHeader("accessKey");
//        String body = request.getHeader("body");
//        String nonce = request.getHeader("nonce");
//        String timestamp = request.getHeader("timestamp");
//        String sign = request.getHeader("sign");
//
//        if (!request.getHeader("accessKey").equals("yupi")){
//            throw new RuntimeException("无权限");
//        }
//
//        if (!JSONUtil.toBean(body, User.class).equals(user))
//        {
//            throw new RuntimeException("无权限");
//        }
//        if (!sign.equals(SignUtils.getSign1(body,"qwertyuiop"))){
//            throw new RuntimeException("无权限");
//        }
        return "post user"+user.getName();
    }

    @PostMapping("/list/max")
    public String getListMax(@RequestBody List<Integer> list){
        int a=list.get(0);
        for (int i = 1; i < list.size()-1; i++) {
            if (a<list.get(i)){
                a= list.get(i);
            }
        }
//        String accessKey = request.getHeader("accessKey");
//        String body = request.getHeader("body");
//        String nonce = request.getHeader("nonce");
//        String timestamp = request.getHeader("timestamp");
//        String sign = request.getHeader("sign");
//
//        if (!request.getHeader("accessKey").equals("yupi")){
//            throw new RuntimeException("无权限");
//        }
//
//        if (!JSONUtil.toBean(body, User.class).equals(user))
//        {
//            throw new RuntimeException("无权限");
//        }
//        if (!sign.equals(SignUtils.getSign1(body,"qwertyuiop"))){
//            throw new RuntimeException("无权限");
//        }
        return "max"+ a;
    }
}
