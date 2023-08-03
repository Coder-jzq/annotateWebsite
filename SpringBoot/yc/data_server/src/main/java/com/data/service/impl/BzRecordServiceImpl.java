package com.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.data.mapper.BzRecordMapper;
import com.data.mapper.BzRecordPauseMapper;
import com.data.pojo.BzRecord;
import com.data.service.IBzRecordService;
import com.data.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author seven
 * @since 2023-05-25
 */
@Service
public class BzRecordServiceImpl extends ServiceImpl<BzRecordMapper, BzRecord> implements IBzRecordService {

    @Autowired
    private BzRecordMapper bzRecordMapper;

    @Autowired
    private BzRecordPauseMapper bzRecordPauseMapper;


    @Override
    public RespBean updataRecord(Integer dh, Integer jz, Integer[] wordsIndex, Integer[] wordsPauseIndex, String userIndex) {
        String index = dh+"-"+jz+"-";

        // 循环把每个单词记录都标注了
        for (int i = 0; i < wordsIndex.length; i++) {
            String indexT = index + wordsIndex[i];

            HashMap<String, String> map = new HashMap<>();
            map.put("idx", indexT);
            map.put("userlabel", userIndex);
            bzRecordMapper.updataRecord(map);

        }

        for (int i = 0; i < wordsPauseIndex.length; i++) {
            String indexT = index + wordsPauseIndex[i];

            HashMap<String, String> map = new HashMap<>();
            map.put("idx", indexT);
            map.put("userlabel", userIndex);
            bzRecordPauseMapper.updataRecordPause(map);

        }

        return RespBean.success("更新成功");
    }

    @Override
    public RespBean updataRecord_0(Integer dh, Integer jz, Integer[] wordsIndex, String userIndex) {
        String index = dh+"-"+jz+"-";
        for (int i = 0; i < wordsIndex.length; i++) {
            String indexT = index + wordsIndex[i];

            HashMap<String, String> map = new HashMap<>();
            map.put("idx", indexT);
            map.put("userlabel", userIndex);

            // 重音置零
            bzRecordMapper.updataRecord_0(map);

            // 停顿至于0
            bzRecordPauseMapper.updataRecordPause_0(map);


        }
        // 循环把每个单词记录都标注了

        return RespBean.success("更新成功");
    }

    @Override
    public RespBean delByindex(Integer start, Integer end) {
        LambdaQueryWrapper<BzRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(BzRecord::getRecordId, start, end);
        return RespBean.success("删除成功", bzRecordMapper.delete(queryWrapper));
    }


}
