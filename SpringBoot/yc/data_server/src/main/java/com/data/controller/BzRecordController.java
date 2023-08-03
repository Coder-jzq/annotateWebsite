package com.data.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.data.pojo.BzRecord;
import com.data.pojo.DataFormat;
import com.data.pojo.DataPostFormat;
import com.data.service.IBzRecordService;
import com.data.utils.RespBean;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author seven
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/api/bz-record")
public class BzRecordController {
    @Autowired
    private IBzRecordService bzRecordService;

    /**
     * 导出bzrecord列表
     */



    @PostMapping("/addJz")
    public RespBean addJz(@RequestBody DataFormat dataFormat){
        // 传进来一组单词，入库

        for (int i = 0; i < dataFormat.getWords().length; i++) {
            //生成一个保存的对象
            BzRecord bzRecord = new BzRecord("","",0,0,0,0,0,0,0,0,0,0);
            // 设置序号
            bzRecord.setRecordIndex(dataFormat.getDh()+"-"+dataFormat.getJz()+"-"+i);
            // 设置单词
            bzRecord.setRecordText(dataFormat.getWords()[i]);
            // 初始化0
            bzRecordService.save(bzRecord);
        }

        // 每一句话中间加一个空行
        BzRecord bzRecord = new BzRecord();
        bzRecordService.save(bzRecord);

        return RespBean.success("第"+dataFormat.getDh()+"-"+dataFormat.getJz()+"插入成功");
    }


    @DeleteMapping("/delAll")
    public RespBean delAll(){
        //清空表
        bzRecordService.remove(new QueryWrapper<>());

        return RespBean.success("删除所有记录");
    }


    @PutMapping("/updataRecord")
    public RespBean updataRecord(@RequestBody DataPostFormat dataPostFormat){
        return bzRecordService.updataRecord(dataPostFormat.getDh(), dataPostFormat.getJz(), dataPostFormat.getWordsIndex(),dataPostFormat.getWordsPauseIndex(), dataPostFormat.getUserIndex());
    }

    @PutMapping("/updataRecord_0")
    public RespBean updataRecord_0(@RequestBody DataPostFormat dataPostFormat){
        return bzRecordService.updataRecord_0(dataPostFormat.getDh(), dataPostFormat.getJz(), dataPostFormat.getWordsIndex(), dataPostFormat.getUserIndex());
    }

    @DeleteMapping("/delByindex")
    public RespBean delByindex(Integer start, Integer end){
        //清空对应字段【start, end】
        return bzRecordService.delByindex(start,end);
    }
}
