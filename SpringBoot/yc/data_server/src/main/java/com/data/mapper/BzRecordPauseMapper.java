package com.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.data.pojo.BzRecord;
import com.data.pojo.BzRecordPause;

import java.util.HashMap;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author seven
 * @since 2023-05-25
 */
public interface BzRecordPauseMapper extends BaseMapper<BzRecordPause> {

    void updataRecordPause(HashMap<String,String> map1);

    void updataRecordPause_0(HashMap<String, String> map);
}
