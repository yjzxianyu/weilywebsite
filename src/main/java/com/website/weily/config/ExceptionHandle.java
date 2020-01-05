package com.website.weily.config;

import com.website.weily.enums.ErrorCodeEnum;
import com.website.weily.exception.BusinessException;
import com.website.weily.util.ResultUtil;
import com.website.weily.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 全局异常处理器
 * @Author youjianzhao
 * @Date 2020/1/5 13:04
 * @Version
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandle {


    /**
     * 自定义异常
     * @param baseException
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResultVo handle(BusinessException baseException) {
        log.error("ExceptionHandle#handle(BusinessException) : code={}, msg={}, e={}",
                baseException.getCode(), baseException.getMessage(), baseException);
        return ResultUtil.error(baseException.getCode(),baseException.getMessage());
    }


    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseBody
    public ResultVo handle(UnauthenticatedException unauthenticatedException) {
        log.error("ExceptionHandle#handle(UnauthorizedException) : msg={}, e={}",
                unauthenticatedException.getMessage(),unauthenticatedException);

        return ResultUtil.error(ErrorCodeEnum.NOT_HAVE_PERMISSION);
    }


    /**
     * 其它异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVo handle(Exception e) {
        log.error("【系统异常】{}", e);
        return ResultUtil.error(ErrorCodeEnum.UNKNOWN_ERROR);
    }
}
