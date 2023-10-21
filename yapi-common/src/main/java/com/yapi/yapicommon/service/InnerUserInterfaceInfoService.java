package com.yapi.yapicommon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yapi.yapicommon.model.entity.UserInterfaceInfo;

/**
* @author YUE
* @description 针对表【user_interface_info(接口用户信息)】的数据库操作Service
* @createDate 2023-08-31 16:37:50
*/
public interface InnerUserInterfaceInfoService {

    boolean invokeCount(Long interfaceId,Long userId);

    boolean canInvoke(Long interfaceId,Long userId);




}
