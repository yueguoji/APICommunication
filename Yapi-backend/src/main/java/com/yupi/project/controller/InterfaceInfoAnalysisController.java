package com.yupi.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yapi.yapicommon.model.entity.InterfaceInfo;
import com.yapi.yapicommon.model.entity.User;
import com.yapi.yapicommon.model.entity.UserInterfaceInfo;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserInterfaceInfoMapper;
import com.yupi.project.model.dto.post.PostAddRequest;
import com.yupi.project.model.entity.Post;
import com.yupi.project.model.vo.InterfaceInfoVO;
import com.yupi.project.service.InterfaceInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Yuuue
 * creat by 2023-09-21
 */
@RestController
@RequestMapping("/analysis")
public class InterfaceInfoAnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;
    /**
     * 接口分析
     *
     * @return
     */
    @GetMapping("/top/interface/invoke")
    public BaseResponse<List<InterfaceInfoVO>> invokeAnalysis() {
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.getCount(3);
        Map<Long, List<UserInterfaceInfo>> listMap = userInterfaceInfos.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceId));
        LambdaQueryWrapper<InterfaceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(InterfaceInfo::getId,listMap.keySet());
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(lambdaQueryWrapper);
        List<InterfaceInfoVO> interfaceInfoVOS = interfaceInfoList.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            interfaceInfoVO.setTotalNum(listMap.get(interfaceInfo.getId()).get(0).getTotalNum());
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(interfaceInfoVOS);
    }


}
