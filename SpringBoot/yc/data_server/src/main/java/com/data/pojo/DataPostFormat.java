package com.data.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: Jia Zhenqi
 * @Date: 2023/5/30  20:14
 * @Version 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "提交对象", description = "")    // swagger文档注解
public class DataPostFormat {
    private Integer dh;
    private Integer jz;
    private Integer[] wordsIndex;
    private Integer[] wordsPauseIndex;

    private String userIndex;
}
