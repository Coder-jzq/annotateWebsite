package com.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.data.pojo.BzRecord;
import com.data.utils.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author seven
 * @since 2023-05-25
 */
public interface IBzRecordService extends IService<BzRecord> {

    RespBean updataRecord(Integer dh, Integer jz, Integer[] wordsIndex, Integer[] wordsPauseIndex, String userIndex);

    RespBean delByindex(Integer start, Integer end);

    RespBean updataRecord_0(Integer dh, Integer jz, Integer[] wordsIndex, String userIndex);
}
