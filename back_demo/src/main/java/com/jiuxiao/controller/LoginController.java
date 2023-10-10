package com.jiuxiao.controller;

import com.jiuxiao.entry.User;
import com.jiuxiao.model.RespBean;
import com.jiuxiao.model.ResponseEnum;
import com.jiuxiao.service.UserService;
import com.jiuxiao.vo.UserLoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @Author 悟道九霄
 * @Date 2023/10/9 15:37
 * @Description 登录控制器
 */
@Api(tags = "登录控制器")
@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public RespBean login(@RequestBody UserLoginParam loginParam, HttpServletRequest request) {
        return userService.login(loginParam.getUsername(), loginParam.getPassword(), request);
    }

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/info")
    public RespBean info(Principal principal) {
        if (principal == null){
            return null;
        }
        String username = principal.getName();
        User user = userService.getUserByUserName(username);
        user.setPassword(null);
        return RespBean.success(ResponseEnum.USER_INFO_SUCCESS, user);
    }

    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success(ResponseEnum.USER_LOGOUT_SUCCESS);
    }
}
