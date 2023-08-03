package com.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.data.mapper.BzUserMapper;
import com.data.pojo.BzUser;
import com.data.service.IBzUserService;
import com.data.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author seven
 * @since 2023-05-25
 */
@Service
public class BzUserServiceImpl extends ServiceImpl<BzUserMapper, BzUser> implements IBzUserService {

    @Autowired
    private BzUserMapper userMapper;
    @Override
    public RespBean login(String username, String password) {
        // 查询符合条件的记录
        BzUser bzUser = userMapper.selectOne(new QueryWrapper<BzUser>().eq("username", username).eq("password", password));
        if (bzUser==null) {
            return RespBean.warning("登录失败");
        }else {
            // 清空密码
            bzUser.setPassword("");
            return RespBean.success("登录成功", bzUser);
        }
    }
}
