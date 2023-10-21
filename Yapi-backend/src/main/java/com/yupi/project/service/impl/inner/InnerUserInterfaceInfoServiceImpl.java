package com.yupi.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yapi.yapicommon.model.entity.UserInterfaceInfo;
import com.yapi.yapicommon.service.InnerUserInterfaceInfoService;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author Yuuue
 * creat by 2023-09-06
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 更新接口使用次数
     * @param interfaceId
     * @param userId
     * @return
     */
    @Override
    public boolean invokeCount(Long interfaceId, Long userId) {
        return userInterfaceInfoService.invokeCount(interfaceId, userId);

    }

    @Override
    public boolean canInvoke(Long interfaceId, Long userId) {
        return userInterfaceInfoService.canInvoke(interfaceId, userId);
    }

}
