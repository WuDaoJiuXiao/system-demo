package com.jiuxiao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 悟道九霄
 * @Date 2023/10/9 15:10
 * @Description 公共返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    /* 返回的状态码 */
    private Integer code;

    /* 返回的消息 */
    private String message;

    /* 返回的对象 */
    private Object data;

    /**
     * @param message 返回的消息
     * @return: com.jiuxiao.model.RespBean
     * @decription 成功的返回对象
     * @date 2023/10/9 15:14
     */
    public static RespBean success(String message) {
        return new RespBean(10000, message, null);
    }

    /**
     * @param message 返回的消息
     * @param object  返回的对象
     * @return: com.jiuxiao.model.RespBean
     * @decription 成功的返回对象
     * @date 2023/10/9 15:15
     */
    public static RespBean success(String message, Object object) {
        return new RespBean(10000, message, object);
    }

    /**
     * @param responseEnum 返回结果枚举类
     * @return: com.jiuxiao.model.RespBean
     * @decription 成功的返回对象
     * @date 2023/10/9 15:20
     */
    public static RespBean success(ResponseEnum responseEnum) {
        return new RespBean(responseEnum.getCode(), responseEnum.getMessage(), null);
    }

    /**
     * @param responseEnum 返回结果枚举类
     * @param object       返回的对象
     * @return: com.jiuxiao.model.RespBean
     * @decription 成功的返回对象
     * @date 2023/10/9 15:21
     */
    public static RespBean success(ResponseEnum responseEnum, Object object) {
        return new RespBean(responseEnum.getCode(), responseEnum.getMessage(), object);
    }

    /**
     * @param message 返回的消息
     * @return: com.jiuxiao.model.RespBean
     * @decription 失败的返回对象
     * @date 2023/10/9 15:14
     */
    public static RespBean error(String message) {
        return new RespBean(-10000, message, null);
    }

    /**
     * @param message 返回的消息
     * @param object  返回的对象
     * @return: com.jiuxiao.model.RespBean
     * @decription 失败的返回对象
     * @date 2023/10/9 15:15
     */
    public static RespBean error(String message, Object object) {
        return new RespBean(-10000, message, object);
    }

    /**
     * @param responseEnum 返回结果枚举类
     * @return: com.jiuxiao.model.RespBean
     * @decription 失败的返回对象
     * @date 2023/10/9 15:20
     */
    public static RespBean error(ResponseEnum responseEnum) {
        return new RespBean(responseEnum.getCode(), responseEnum.getMessage(), null);
    }

    /**
     * @param responseEnum 返回结果枚举类
     * @param object       返回的对象
     * @return: com.jiuxiao.model.RespBean
     * @decription 失败的返回对象
     * @date 2023/10/9 15:21
     */
    public static RespBean error(ResponseEnum responseEnum, Object object) {
        return new RespBean(responseEnum.getCode(), responseEnum.getMessage(), object);
    }
}
