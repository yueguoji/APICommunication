package com.yapi.yapicommon.model.dto.interfaceInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {


    /**
     * 用户名
     */
    private Long id;

    /**
     * 请求类型
     */
    private String userRequestParams;





    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
