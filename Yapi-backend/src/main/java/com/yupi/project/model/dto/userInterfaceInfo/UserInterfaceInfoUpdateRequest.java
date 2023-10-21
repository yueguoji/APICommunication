package com.yupi.project.model.dto.userInterfaceInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {

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

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 更新时间
     */
    private Date update_time;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
