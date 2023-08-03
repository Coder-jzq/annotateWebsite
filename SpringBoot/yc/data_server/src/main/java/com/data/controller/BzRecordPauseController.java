package com.data.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.data.pojo.BzRecord;
import com.data.pojo.BzRecordPause;
import com.data.pojo.DataFormat;
import com.data.pojo.DataPostFormat;
import com.data.service.IBzRecordPauseService;
import com.data.service.IBzRecordService;
import com.data.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author seven
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/api/bz-record-pause")
public class BzRecordPauseController {
    @Autowired
    private IBzRecordPauseService bzRecordPauseService;

    /**
     * 导出bzrecord列表
     */



    @PostMapping("/addJzPause")
    public RespBean addJzPause(@RequestBody DataFormat dataFormat){
        // 传进来一组单词，入库

        for (int i = 0; i < dataFormat.getWords().length; i++) {
            //生成一个保存的对象
            BzRecordPause bzRecordPause = new BzRecordPause("","",0,0,0,0,0,0,0,0,0,0);
            // 设置序号
            bzRecordPause.setRecordIndex(dataFormat.getDh()+"-"+dataFormat.getJz()+"-"+i);
            // 设置单词
            bzRecordPause.setRecordText(dataFormat.getWords()[i]);
            // 初始化0
            bzRecordPauseService.save(bzRecordPause);
        }

        // 每一句话中间加一个空行
        BzRecordPause bzRecordPause = new BzRecordPause();
        bzRecordPauseService.save(bzRecordPause);

        return RespBean.success("第"+dataFormat.getDh()+"-"+dataFormat.getJz()+"插入成功");
    }


    @DeleteMapping("/delAllPause")
    public RespBean delAllPause(){
        //清空表
        bzRecordPauseService.remove(new QueryWrapper<>());

        return RespBean.success("删除所有记录");
    }


    @PutMapping("/updataRecordPause")
    public RespBean updataRecordPause(@RequestBody DataPostFormat dataPostFormat){
        return bzRecordPauseService.updataRecordPause(dataPostFormat.getDh(), dataPostFormat.getJz(), dataPostFormat.getWordsIndex(), dataPostFormat.getUserIndex());
    }

    @PutMapping("/updataRecordPause_0")
    public RespBean updataRecordPause_0(@RequestBody DataPostFormat dataPostFormat){
        return bzRecordPauseService.updataRecordPause_0(dataPostFormat.getDh(), dataPostFormat.getJz(), dataPostFormat.getWordsIndex(), dataPostFormat.getUserIndex());
    }

    @DeleteMapping("/delByindexPause")
    public RespBean delByindexPause(Integer start, Integer end){
        //清空对应字段【start, end】
        return bzRecordPauseService.delByindexPause(start,end);
    }
}
