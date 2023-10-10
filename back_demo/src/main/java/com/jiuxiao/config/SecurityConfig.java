package com.jiuxiao.config;

import com.jiuxiao.security.JwtAuthTokenFilter;
import com.jiuxiao.security.RestAccessDeniedHandler;
import com.jiuxiao.security.RestAuthEntryPoint;
import com.jiuxiao.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Author 悟道九霄
 * @Date 2023/10/10 8:59
 * @Description SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserService userService;

    @Resource
    private RestAuthEntryPoint restAuthEntryPoint;

    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;

    /**
     * @param auth 身份验证器
     * @return: void
     * @decription 配置 Security 认证机制
     * @date 2023/10/10 9:07
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * @param web 网页安全对象
     * @return: void
     * @decription 配置 Web 资源过滤机制
     * @date 2023/10/10 10:11
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**"
        );
    }

    /**
     * @param http Http 对象
     * @return: void
     * @decription 配置 HTTP 请求安全性
     * @date 2023/10/10 9:10
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置跨域
        http.cors().and()
                // 使用 jwt 令牌，不需要 csrf
                .csrf().disable()
                // 基于 token，不需要 session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 所有请求均要进行认证
                .anyRequest()
                .authenticated()
                .and()
                .headers()
                .cacheControl();
        // 添加 jwt 登录授权过滤器
        http.addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                // 添加自定义未授权登录的结果返回
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(restAuthEntryPoint);
    }

    /**
     * @return: org.springframework.security.core.userdetails.UserDetailsService
     * @decription 实现 UserDetailService 接口的 loadUserByUsername 方法
     * @date 2023/10/10 9:09
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> userService.getUserByUserName(username);
    }

    /**
     * @return: org.springframework.security.crypto.password.PasswordEncoder
     * @decription BCryptPasswordEncoder 密码解析器
     * @date 2023/10/10 9:08
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return: com.jiuxiao.security.JwtAuthTokenFilter
     * @decription 自定义 Jwt 登录授权过滤器
     * @date 2023/10/10 9:39
     */
    @Bean
    public JwtAuthTokenFilter jwtAuthTokenFilter() {
        return new JwtAuthTokenFilter();
    }
}
