package com.yupi.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yapi.yapicommon.model.entity.UserInterfaceInfo;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserInterfaceInfoMapper;
import com.yupi.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author YUE
* @description 针对表【user_interface_info(接口用户信息)】的数据库操作Service实现
* @createDate 2023-08-31 16:37:50
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    /**
     * 校验
     *
     * @param userInterfaceInfo
     * @param add  是否为创建校验
     */
    @Override
    public void validPost(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }



        Integer id = userInterfaceInfo.getId();
        Long userId = userInterfaceInfo.getUserId();
        Long interfaceId = userInterfaceInfo.getInterfaceId();
        Integer totalNum = userInterfaceInfo.getTotalNum();
        Integer leftNum = userInterfaceInfo.getLeftNum();
        Integer status = userInterfaceInfo.getStatus();
        Date create_time = userInterfaceInfo.getCreate_time();
        Date update_time = userInterfaceInfo.getUpdate_time();
        Integer is_deleted = userInterfaceInfo.getIs_deleted();

        //        Integer age = post.getAge();
//        Integer gender = post.getGender();
//        String content = post.getContent();
//        String job = post.getJob();
//        String place = post.getPlace();
//        String education = post.getEducation();
//        String loveExp = post.getLoveExp();
//        Integer reviewStatus = post.getReviewStatus();
        // 创建时，所有参数必须非空
        if (add) {
//            if (StringUtils.isAnyBlank() ) {
//                throw new BusinessException(ErrorCode.PARAMS_ERROR);
//            }
        }
//        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
//        }
//        if (reviewStatus != null && !PostReviewStatusEnum.getValues().contains(reviewStatus)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        if (age != null && (age < 18 || age > 100)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "年龄不符合要求");
//        }
//        if (gender != null && !PostGenderEnum.getValues().contains(gender)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "性别不符合要求");
//        }
    }

//    @Override
//    public boolean getLoginUser(String accessKey, String secretKey) {
//        return false;
//    }



    @Override
    public boolean invokeCount(Long interfaceId, Long userId) {
        if (interfaceId<0||userId<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<UserInterfaceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInterfaceInfo::getInterfaceId,interfaceId)
                .eq(UserInterfaceInfo::getUserId,userId);
        UserInterfaceInfo userInterface = this.getOne(lambdaQueryWrapper);
        //case 1
//        LambdaUpdateWrapper<UserInterfaceInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//        lambdaUpdateWrapper.eq(UserInterfaceInfo::getInterfaceId,interfaceId)
//                .eq(UserInterfaceInfo::getUserId,userId).setSql("leftNum = leftNum+1,totalNum = totalNum+1");
        //case 2
        LambdaUpdateWrapper<UserInterfaceInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(UserInterfaceInfo::getInterfaceId,interfaceId)
                .eq(UserInterfaceInfo::getUserId,userId).set(UserInterfaceInfo::getLeftNum,userInterface.getLeftNum()-1).set(UserInterfaceInfo::getTotalNum,userInterface.getTotalNum()+1);
        return this.update(lambdaUpdateWrapper);

    }

    @Override
    public boolean canInvoke(Long interfaceId, Long userId) {
        //参数校验
        if (interfaceId<0||userId<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<UserInterfaceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInterfaceInfo::getInterfaceId,interfaceId)
                .eq(UserInterfaceInfo::getUserId,userId);
        UserInterfaceInfo userInterface = this.getOne(lambdaQueryWrapper);
        return userInterface.getLeftNum() > 0;
    }
}




