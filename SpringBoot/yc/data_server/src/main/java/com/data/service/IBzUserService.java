package com.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.data.pojo.BzUser;
import com.data.utils.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author seven
 * @since 2023-05-25
 */
public interface IBzUserService extends IService<BzUser> {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    RespBean login(String username, String password);
}
