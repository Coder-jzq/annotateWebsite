package com.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.data.pojo.BzAnnRecord;
import com.data.utils.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author seven
 * @since 2023-05-30
 */
public interface IBzAnnRecordService extends IService<BzAnnRecord> {

    RespBean getAnnRecordByDhAndUser(Integer dh, String username);

    RespBean getAnnRecordCountByDhAndUser(String username);

    Integer getAnnRecordCount(String username);
}
