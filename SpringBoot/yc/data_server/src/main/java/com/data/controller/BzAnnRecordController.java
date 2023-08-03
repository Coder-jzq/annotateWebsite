package com.data.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.data.pojo.BzAnnRecord;
import com.data.service.IBzAnnRecordService;
import com.data.service.impl.BzAnnRecordServiceImpl;
import com.data.utils.RespBean;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author seven
 * @since 2023-05-30
 */
@RestController
@RequestMapping("/api/bz-ann-record")
public class BzAnnRecordController {
    @Autowired
    private IBzAnnRecordService bzAnnRecordService;

    @PostMapping("/addAnnRecord")
    public RespBean addAnnRecord(@RequestBody BzAnnRecord bzAnnRecord) {
        Integer count = bzAnnRecordService.count(new QueryWrapper<BzAnnRecord>()
                .eq("ann_record_dh", bzAnnRecord.getAnnRecordDh())
                .eq("ann_record_jz",bzAnnRecord.getAnnRecordJz())
                .eq("ann_record_user", bzAnnRecord.getAnnRecordUser()));

        if(count != 0) {
            return RespBean.warning("重复存储,已撤销");
        }

        return RespBean.success("标注成功", bzAnnRecordService.save(bzAnnRecord));
    }

    @DeleteMapping("/deleteRecord")
    public RespBean deleteRecord(@RequestBody BzAnnRecord bzAnnRecord){
        System.out.println(bzAnnRecord);
        return RespBean.success("撤回成功", bzAnnRecordService.remove(new QueryWrapper<BzAnnRecord>().eq("ann_record_dh", bzAnnRecord.getAnnRecordDh()).eq("ann_record_jz", bzAnnRecord.getAnnRecordJz()).eq("ann_record_user",bzAnnRecord.getAnnRecordUser())));
    }

    @GetMapping("/getAnnRecordByDhAndUser")
        public RespBean getAnnRecordByDhAndUser(Integer dh, String username){
        return bzAnnRecordService.getAnnRecordByDhAndUser(dh, username);
    }

    @GetMapping("/getAnnRecordCountByDhAndUser")
    public RespBean getAnnRecordCountByDhAndUser(String username){
        return bzAnnRecordService.getAnnRecordCountByDhAndUser(username);
    }

    @GetMapping("/getAnnRecordCountS")
    public RespBean getAnnRecordCountS(){
        String[] users = {"tts001", "tts002", "tts003", "tts004", "tts005", "tts006", "tts007", "tts008", "tts009", "tts010",
                        "tts011", "tts012", "tts013", "tts014", "tts015", "tts016", "tts017", "tts018", "tts019", "tts020"};

        HashMap<String, Object>[] counts = new HashMap[20];

        // 遍历查询到20个账号记录
        for (int i = 0; i < 20; i++) {
            Integer annRecordCount = bzAnnRecordService.getAnnRecordCount(users[i]);
            HashMap<String, Object> map = new HashMap<>();
            map.put("username", users[i]);
            map.put("count", annRecordCount);

            counts[i] = map;
        }

        return RespBean.success("查询成功", counts);
    }
}
