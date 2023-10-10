package com.jiuxiao.service;

import com.jiuxiao.entry.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuxiao.model.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 悟道九霄
 * @since 2023-10-08
 */
public interface UserService extends IService<User> {

    /**
     * @param username 用户名
     * @param password 用户密码
     * @param request  请求对象
     * @return: com.jiuxiao.model.RespBean
     * @decription 用户登录并返回 Token
     * @date 2023/10/9 15:48
     */
    RespBean login(String username, String password, HttpServletRequest request);

    /**
     * @param username 用户名
     * @return: com.jiuxiao.entry.User
     * @decription 根据用户名查询用户信息
     * @date 2023/10/9 16:14
     */
    User getUserByUserName(String username);
}
