package com.data.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author seven
 * @since 2023-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bz_record")
@ApiModel(value="BzRecord对象", description="")
public class BzRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    @Excel(name = "index")
    @TableField("record_index")
    private String recordIndex;

    @Excel(name = "text")
    @TableField("record_text")
    private String recordText;

    @Excel(name = "标注1")
    @TableField("record_user1")
    private Integer recordUser1;

    @Excel(name = "标注2")
    @TableField("record_user2")
    private Integer recordUser2;

    @Excel(name = "标注3")
    @TableField("record_user3")
    private Integer recordUser3;

    @Excel(name = "标注4")
    @TableField("record_user4")
    private Integer recordUser4;

    @Excel(name = "标注5")
    @TableField("record_user5")
    private Integer recordUser5;


    @Excel(name = "标注6")
    @TableField("record_user6")
    private Integer recordUser6;

    @Excel(name = "标注7")
    @TableField("record_user7")
    private Integer recordUser7;

    @Excel(name = "标注8")
    @TableField("record_user8")
    private Integer recordUser8;

    @Excel(name = "标注9")
    @TableField("record_user9")
    private Integer recordUser9;

    @Excel(name = "标注10")
    @TableField("record_user10")
    private Integer recordUser10;

    public BzRecord(){
        this.recordIndex = "";
        this.recordText = "";
    }

    public BzRecord(String recordIndex, String recordText,
                    Integer recordUser1, Integer recordUser2, Integer recordUser3, Integer recordUser4, Integer recordUser5,
                    Integer recordUser6, Integer recordUser7, Integer recordUser8, Integer recordUser9, Integer recordUser10) {
        this.recordIndex = recordIndex;
        this.recordText = recordText;
        this.recordUser1 = recordUser1;
        this.recordUser2 = recordUser2;
        this.recordUser3 = recordUser3;
        this.recordUser4 = recordUser4;
        this.recordUser5 = recordUser5;
        this.recordUser6 = recordUser6;
        this.recordUser7 = recordUser7;
        this.recordUser8 = recordUser8;
        this.recordUser9 = recordUser9;
        this.recordUser10 = recordUser10;
    }
}
