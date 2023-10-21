package com.yupi.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yapi.yapicommon.model.entity.InterfaceInfo;

/**
* @author YUE
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-08-28 13:19:46
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add 是否为创建校验
     */
    void validPost(InterfaceInfo interfaceInfo, boolean add);

}
