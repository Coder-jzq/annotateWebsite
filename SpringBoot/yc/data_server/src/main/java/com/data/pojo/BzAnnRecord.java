package com.data.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author seven
 * @since 2023-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bz_ann_record")
@ApiModel(value="BzAnnRecord对象", description="")
public class BzAnnRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ann_record_id", type = IdType.AUTO)
    private Integer annRecordId;

    @ApiModelProperty(value = "第几个对话")
    @TableField("ann_record_dh")
    private Integer annRecordDh;

    @ApiModelProperty(value = "第几个句子")
    @TableField("ann_record_jz")
    private Integer annRecordJz;

    @ApiModelProperty(value = "标注人")
    @TableField("ann_record_user")
    private String annRecordUser;


}
