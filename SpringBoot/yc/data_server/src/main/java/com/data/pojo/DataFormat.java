package com.data.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: Jia Zhenqi
 * @Date: 2023/5/30  13:24
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "数据格式", description = "")    // swagger文档注解
public class DataFormat {
    @ApiModelProperty(value = "words", required = true)
    private String[] words;

    @ApiModelProperty(value = "对话序号", required = true)
    private Integer dh;

    @ApiModelProperty(value = "句子序号", required = true)
    private Integer jz;

}
