package com.jiuxiao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 悟道九霄
 * @Date 2023/10/10 9:59
 * @Description Swagger 接口文档配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jiuxiao.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("VSS Demo 系统接口文档")
                .description("使用 Vue + SpringBoot + SpringSecurity 的系统模板接口文档")
                .contact(new Contact("悟道九霄", "http://127.0.0.1:8888/doc.html", "fanye814@gmail.com"))
                .version("1.0.0")
                .build();
    }

    /**
     * @return: java.util.List<springfox.documentation.service.ApiKey>
     * @decription Swagger 接口文档身份验证配置
     * @date 2023/10/10 10:19
     */
    private List<ApiKey> securitySchemes() {
        ArrayList<ApiKey> apiKeys = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        apiKeys.add(apiKey);
        return apiKeys;
    }

    /**
     * @return: java.util.List<springfox.documentation.service.ApiKey>
     * @decription Swagger 接口文档上下文配置
     * @date 2023/10/10 10:19
     */
    private List<SecurityContext> securityContexts() {
        ArrayList<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(getContextByPath("/hello/.*"));
        return securityContexts;
    }

    /**
     * @param pathRegex 路径正则
     * @return: springfox.documentation.spi.service.contexts.SecurityContext
     * @decription
     * @date 2023/10/10 10:24
     */
    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    /**
     * @return: java.util.List<springfox.documentation.service.SecurityReference>
     * @decription 配置默认的安全饮用策略
     * @date 2023/10/10 10:27
     */
    private List<SecurityReference> defaultAuth() {
        ArrayList<SecurityReference> securityReferences = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
}
