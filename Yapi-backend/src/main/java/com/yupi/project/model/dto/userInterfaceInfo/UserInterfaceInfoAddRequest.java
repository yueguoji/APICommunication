package com.yupi.project.model.dto.userInterfaceInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {


    /**
     * 用户名
     */
    private Integer id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 接口Id
     */
    private Long interfaceId;

    /**
     * 使用总数
     */
    private Integer totalNum;

    /**
     * 剩余次数
     */
    private Integer leftNum;

    /**
     * 状态
     */
    private Integer status;





    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
