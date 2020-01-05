package com.website.weily.exception;

import com.website.weily.enums.ErrorCodeEnum;
import lombok.Getter;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 14:49
 * @Version
 */
@Getter
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(ErrorCodeEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
    }


    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
}
