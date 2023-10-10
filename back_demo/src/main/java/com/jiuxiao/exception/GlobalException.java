package com.jiuxiao.exception;

import com.jiuxiao.model.RespBean;
import com.jiuxiao.model.ResponseEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @Author 悟道九霄
 * @Date 2023/10/10 10:47
 * @Description 全局异常处理
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public RespBean vssSQLException(SQLException e) {
        if (e != null) {
            return RespBean.error("数据有关联数据，操作失败");
        }
        return RespBean.error(ResponseEnum.SQL_DEFAULT_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public RespBean vssException(Exception e) {
        return RespBean.error("未知异常，请联系管理员");
    }
}
