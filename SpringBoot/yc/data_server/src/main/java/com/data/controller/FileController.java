package com.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.data.mapper.BzAnnRecordMapper;
import com.data.pojo.BzAnnRecord;
import com.data.pojo.LostData;
import com.data.utils.RespBean;
import io.swagger.models.auth.In;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

/**
 * @Author: Jia Zhenqi
 * @Date: 2023/5/27  16:45
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/file")
public class FileController {


    @Autowired
    BzAnnRecordMapper bzAnnRecordMapper;

    /**
     * 获取文件
     * @param idx
     * @return
     * @throws IOException
     */
    private String[] getFileArray(String idx) throws IOException{
        // 创建 ResourceLoader 对象
        PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
        String[] filelist = null;
        try {
            // 使用 ResourceLoader 获取 resource 文件夹下的所有文件
            Resource[] resources = resourceLoader.getResources("classpath:data/"+idx+"/*");
            String[] files = new String[resources.length];
            // 遍历资源文件，获取文件名
            for (int i = 0; i < resources.length; i++) {
                String fileName = resources[i].getFilename();
                files[i] = fileName;
            }

            filelist = files;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filelist;
    }

    @GetMapping("/getFiles")
    public RespBean getFiles(String idx) throws IOException {
        String[] filelist = getFileArray(idx);

        return RespBean.success("获取成功", filelist);

    }

    @PostMapping("/getlost")
    public RespBean getlost(@RequestBody LostData lostData) throws IOException {
        // 遗漏的对话
        String dhLostArr = "";
        for (int i = lostData.getStart(); i <= lostData.getEnd(); i++) {
            String idx = String.valueOf(i);
            // 获取文件数量(包括wav和txt)
            Integer fileCount = getFileArray(idx).length;

            // 获取该用户对当前对话标记数量
            Integer bzCount = bzAnnRecordMapper.selectCount(new QueryWrapper<BzAnnRecord>().eq("ann_record_dh", idx).eq("ann_record_user", lostData.getAnn_record_user()));

            if(fileCount != (bzCount*2)) {
                dhLostArr += idx+";";
            }
        }

        System.out.println(dhLostArr);
       return  RespBean.success("测试成功", dhLostArr);
    }
}
