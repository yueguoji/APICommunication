package com.yupi.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yapi.yapi0clientsdk.client.YapiClient;
import com.yapi.yapicommon.model.entity.InterfaceInfo;
import com.yapi.yapicommon.model.entity.User;
import com.yupi.project.annotation.AuthCheck;
import com.yupi.project.common.*;
import com.yupi.project.constant.CommonConstant;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.yupi.project.model.dto.interfaceInfo.InterfaceInfoInvokeRequest;
import com.yupi.project.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.yupi.project.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.yupi.project.model.enums.InterfaceStatusEnum;
import com.yupi.project.service.InterfaceInfoService;
import com.yupi.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;

/**
 * 帖子接口
 *
 * @author Yuuue
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceController {


    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private YapiClient yapiClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param iAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addPost(@RequestBody InterfaceInfoAddRequest iAddRequest, HttpServletRequest request) {
        if (iAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(iAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validPost(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newPostId = interfaceInfo.getId();
        return ResultUtils.success(newPostId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deletePost(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldPost = interfaceInfoService.getById(id);
        if (oldPost == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldPost.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param iUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updatePost(@RequestBody InterfaceInfoUpdateRequest iUpdateRequest,
                                            HttpServletRequest request) {
        if (iUpdateRequest == null || iUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(iUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validPost(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = iUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldPost = interfaceInfoService.getById(id);
        if (oldPost == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldPost.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 上线
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")  //判断是否为管理员
    public BaseResponse<Boolean> onlinePost(@RequestBody IdRequest idRequest,
                                            HttpServletRequest request) {



        //判断参数
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(idRequest.getId());
        if (interfaceInfo==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口不存在");
        }

        // TODO 换成直接调用接口地址的
        com.yapi.yapi0clientsdk.model.User user = new com.yapi.yapi0clientsdk.model.User();
        user.setName("yyyy");
//        String userNamePost = yapiClient.getByNamePost(user);
//        if (StringUtils.isBlank(userNamePost)){
//            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口调用失败");
//        }

        interfaceInfo.setStatus(InterfaceStatusEnum.ONLINE.getValue());
        boolean b = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(b);
    }

    /**
     * 下线
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")  //判断是否为管理员
    public BaseResponse<Boolean> offlinePost(@RequestBody IdRequest idRequest,
                                            HttpServletRequest request) {

        //判断参数
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(idRequest.getId());
        if (interfaceInfo==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口不存在");
        }

        interfaceInfo.setStatus(InterfaceStatusEnum.OFFLINE.getValue());
        boolean b = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(b);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getPostById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param iQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listPost(InterfaceInfoQueryRequest iQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (iQueryRequest != null) {
            BeanUtils.copyProperties(iQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> postList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(postList);
    }

    /**
     * 分页获取列表
     *
     * @param iQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listPostByPage(InterfaceInfoQueryRequest iQueryRequest, HttpServletRequest request) {
        if (iQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(iQueryRequest, interfaceInfoQuery);
        long current = iQueryRequest.getCurrent();
        long size = iQueryRequest.getPageSize();
        String sortField = iQueryRequest.getSortField();
        String sortOrder = iQueryRequest.getSortOrder();
//        String content = interfaceInfoQuery.getContent();
        // content 需支持模糊搜索
//        interfaceInfoQuery.setContent(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
//        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> postPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(postPage);
    }

    // endregion

    /**
     * 调用
     *
     * @param interfaceInfoInvokeRequest
     * @param request
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                            HttpServletRequest request) {

        //判断参数
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(interfaceInfoInvokeRequest.getId());
        if (interfaceInfo==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口不存在");
        }
        if (interfaceInfo.getStatus().equals(InterfaceStatusEnum.OFFLINE.getValue())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口已关闭");
        }

        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();

        YapiClient yapiClient1 = new YapiClient(accessKey,secretKey);

        Gson gson = new Gson();
        String userRequestParams = interfaceInfoInvokeRequest.getUserRequestParams();
        com.yapi.yapi0clientsdk.model.User user = gson.fromJson(userRequestParams, com.yapi.yapi0clientsdk.model.User.class);
        String userNamePost = yapiClient1.getByNamePost(user);
        if (StringUtils.isBlank(userNamePost)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口调用失败");
        }
        return ResultUtils.success(userNamePost);
    }

}
