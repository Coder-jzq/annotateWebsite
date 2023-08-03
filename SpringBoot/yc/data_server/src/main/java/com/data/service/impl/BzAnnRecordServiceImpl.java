package com.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.data.mapper.BzAnnRecordMapper;
import com.data.pojo.BzAnnRecord;
import com.data.service.IBzAnnRecordService;
import com.data.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author seven
 * @since 2023-05-30
 */
@Service
public class BzAnnRecordServiceImpl extends ServiceImpl<BzAnnRecordMapper, BzAnnRecord> implements IBzAnnRecordService {
    @Autowired
    private BzAnnRecordMapper bzAnnRecordMapper;

    @Override
    public RespBean getAnnRecordByDhAndUser(Integer dh, String username) {
        return RespBean.success("获取成功",bzAnnRecordMapper.selectList(new QueryWrapper<BzAnnRecord>().eq("ann_record_dh", dh).eq("ann_record_user", username)));
    }

    @Override
    public RespBean getAnnRecordCountByDhAndUser(String username) {
        return RespBean.success("获取成功",bzAnnRecordMapper.selectList(new QueryWrapper<BzAnnRecord>().eq("ann_record_user", username)).size());

    }

    @Override
    public Integer getAnnRecordCount(String username) {
        return bzAnnRecordMapper.selectList(new QueryWrapper<BzAnnRecord>().eq("ann_record_user", username)).size();
    }
}
