package com.jiuxiao.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuxiao.model.RespBean;
import com.jiuxiao.model.ResponseEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author 悟道九霄
 * @Date 2023/10/10 9:44
 * @Description 当未登录或者 token 失效时，用户访问接口时的自定义返回结果
 */
@Component
public class RestAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        RespBean bean = RespBean.error(ResponseEnum.NOT_LOGIN_ERROR);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
