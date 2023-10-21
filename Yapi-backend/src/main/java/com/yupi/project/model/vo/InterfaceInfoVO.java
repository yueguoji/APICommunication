package com.yupi.project.model.vo;

import com.yapi.yapicommon.model.entity.InterfaceInfo;
import com.yupi.project.model.entity.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口视图
 *
 * @author yupi
 * @TableName product
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    /**
     * 总数
     */
    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}
