package com.jiuxiao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author 悟道九霄
 * @Date 2023/10/10 12:57
 * @Description 跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // 允许所有域名访问，可根据需要设置具体的域名
                .allowedMethods("*")  // 允许所有请求方法，可根据需要设置具体的请求方法，例如：GET、POST
                .allowedHeaders("*")  // 允许所有请求头，可根据需要设置具体的请求头
                .allowCredentials(true);  // 允许发送cookie
    }
}
