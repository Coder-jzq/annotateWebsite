package com.data.controller;


import com.data.pojo.UserLoginParam;
import com.data.service.IBzUserService;
import com.data.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author seven
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/api/user")
public class BzUserController {
    @Autowired
    private IBzUserService bzUserService;

    /**
     * 登录
     * @param userLoginParam
     * @return
     */

    @PostMapping("/login")
    public RespBean login(@RequestBody UserLoginParam userLoginParam) {
        // 传入账号密码进行查询
        System.out.println(userLoginParam);
        return bzUserService.login(userLoginParam.getUsername(), userLoginParam.getPassword());
    }

    @GetMapping("/ceshi")
    private RespBean ceshi() {
        return RespBean.success("111");
    }
}
