package com.jiuxiao.controller;

import com.jiuxiao.model.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 悟道九霄
 * @Date 2023/10/10 10:20
 * @Description 环境测试控制器
 */
@RestController
@Api(tags = "环境测试控制器")
public class HelloController {

    @ApiOperation(value = "环境测试")
    @GetMapping("/hello")
    public RespBean hello() {
        return RespBean.success("测试成功");
    }
}
