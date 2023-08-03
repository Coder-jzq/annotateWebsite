package com.data.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: Jia Zhenqi
 * @Date: 2023/7/14  23:09
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "遗漏数据", description = "")    // swagger文档注解
public class LostData {
    String ann_record_user;
    Integer start;
    Integer end;
}
