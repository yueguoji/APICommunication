package com.yupi.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yapi.yapicommon.model.entity.UserInterfaceInfo;


/**
* @author YUE
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-08-28 13:19:46
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 校验
     *
     * @param userInterfaceInfo
     * @param add 是否为创建校验
     */
    void validPost(UserInterfaceInfo userInterfaceInfo, boolean add);


    boolean invokeCount(Long interfaceId, Long userId);

    boolean canInvoke(Long interfaceId, Long userId);

}
