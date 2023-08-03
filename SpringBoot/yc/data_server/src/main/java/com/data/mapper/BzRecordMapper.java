package com.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.data.pojo.BzRecord;

import java.util.HashMap;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author seven
 * @since 2023-05-25
 */
public interface BzRecordMapper extends BaseMapper<BzRecord> {

    void updataRecord(HashMap<String,String> map1);

    void updataRecord_0(HashMap<String, String> map);
}
