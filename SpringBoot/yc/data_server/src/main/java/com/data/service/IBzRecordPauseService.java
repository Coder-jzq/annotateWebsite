package com.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.data.pojo.BzRecord;
import com.data.pojo.BzRecordPause;
import com.data.utils.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author seven
 * @since 2023-05-25
 */
public interface IBzRecordPauseService extends IService<BzRecordPause> {

    RespBean updataRecordPause(Integer dh, Integer jz, Integer[] wordsIndex, String userIndex);

    RespBean delByindexPause(Integer start, Integer end);

    RespBean updataRecordPause_0(Integer dh, Integer jz, Integer[] wordsIndex, String userIndex);
}
