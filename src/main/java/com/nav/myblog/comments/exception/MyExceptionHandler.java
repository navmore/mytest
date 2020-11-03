package com.nav.myblog.comments.exception;

import com.nav.myblog.comments.rtJson.Result;
import com.nav.myblog.comments.rtJson.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);
    //当前需要处理的异常（BindException）
    @ExceptionHandler(BindException.class)
    public Result exceptionHandler(BindException e) {
        logger.error(e.getClass().toString(), e.getMessage());
        System.out.println(e.getFieldError());
       // return ResultUtil.error(401, e.getFieldError().getDefaultMessage());
        return null;
    }
    //缺少参数抛出的异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result exceptionHandler(MissingServletRequestParameterException e) {
        logger.error(e.getClass().toString(), e.getMessage());
        System.out.println(e.getParameterName());
        return ResultUtil.error(450, "缺少参数" + e.getParameterName());
    }
}
