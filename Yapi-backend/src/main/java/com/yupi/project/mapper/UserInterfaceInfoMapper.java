package com.yupi.project.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yapi.yapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author YUE
* @description 针对表【user_interface_info(接口用户信息)】的数据库操作Mapper
* @createDate 2023-08-31 16:37:50
* @Entity com.yupi.project.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> getCount(int limit);
}




