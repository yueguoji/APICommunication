package com.yapi.yapicommon.service;




import com.baomidou.mybatisplus.extension.service.IService;
import com.yapi.yapicommon.model.entity.User;

//import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author yupi
 */
public interface InnerUserService {

    /**
     * 数据库中查是否已分配给用户秘钥
     * @param accessKey
     * @return
     */
    User getLoginUser(String accessKey);
}
