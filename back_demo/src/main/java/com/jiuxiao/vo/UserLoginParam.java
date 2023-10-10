package com.jiuxiao.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author 悟道九霄
 * @Date 2023/10/9 15:31
 * @Description 用户登录实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UserLoginParam", description = "用户登录参数对象")
public class UserLoginParam {

    @ApiModelProperty(value = "用户登录用户名", required = true)
    private String username;

    @ApiModelProperty(value = "用户登录用户密码", required = true)
    private String password;
}
