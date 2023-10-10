package com.jiuxiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiuxiao.entry.User;
import com.jiuxiao.mapper.UserMapper;
import com.jiuxiao.model.RespBean;
import com.jiuxiao.model.ResponseEnum;
import com.jiuxiao.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuxiao.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 悟道九霄
 * @since 2023-10-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private UserMapper userMapper;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * @param username 用户名
     * @param password 用户密码
     * @param request  请求对象
     * @return: com.jiuxiao.model.RespBean
     * @decription 用户登录并返回 Token
     * @date 2023/10/9 15:49
     */
    @Override
    public RespBean login(String username, String password, HttpServletRequest request) {
        // 用户登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error(ResponseEnum.USER_LOGIN_USERNAME_OR_PASSWORD_ERROR);
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error(ResponseEnum.USER_ACCOUNT_LOCKED_ERROR);
        }

        // 登陆之后更新 Security 登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成 token 并返回
        String token = jwtTokenUtil.generateToken(userDetails);
        HashMap<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success(ResponseEnum.USER_LOGIN_SUCCESS, tokenMap);
    }

    /**
     * @param username 用户名
     * @return: com.jiuxiao.entry.User
     * @decription 根据用户名查询用户信息
     * @date 2023/10/9 16:14
     */
    @Override
    public User getUserByUserName(String username) {
        return userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", username)
                .eq("is_enable", 1));
    }
}
