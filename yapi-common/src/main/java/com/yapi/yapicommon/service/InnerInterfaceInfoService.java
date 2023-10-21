package com.yapi.yapicommon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yapi.yapicommon.model.entity.InterfaceInfo;

/**
* @author YUE
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-08-28 13:19:46
*/
public interface InnerInterfaceInfoService {


    /**
     * 从数据库中查询接口是否存在
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterface(String path,String method);
}
