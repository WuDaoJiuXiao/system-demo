package com.jiuxiao;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author 悟道九霄
 * @Date 2023/10/8 13:56
 * @Description 主启动类
 */
@SpringBootApplication
@MapperScan("com.jiuxiao.mapper")
public class VSSDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(VSSDemoApplication.class, args);
    }
}
