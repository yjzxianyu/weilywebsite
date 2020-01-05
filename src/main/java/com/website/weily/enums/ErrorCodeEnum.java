package com.website.weily.enums;

import lombok.Getter;

/**
 * @Description 枚举
 * @Author youjianzhao
 * @Date 2020/1/4 14:48
 * @Version
 */
@Getter
public enum ErrorCodeEnum {

    SUCCESS("0000","请求成功"),

    /**
     * 01xx 参数相关
     */
    DATA_ERROR("0100","数据错误"),

    /**
     * 02xx用户信息相关
     */
    DATA_NULL("0200","数据为空"),
    NO_USER("0201", "用户不存在"),
    PASSWORD_ERROR("0202", "密码错误"),
    USER_NOT_LOGIN("0203","用户未登录"),

    /**
     * 04xx 其它错误
     */
    UNKNOWN_ERROR("0400","未知错误"),



    /**
     * 05XX 验证码错误
     */
    CRATE_IMAGE_ERROR("0500", "创建图形验证码失败"),
    IMAGE_CODE_ERROR("0501", "图形验证码时效超时或失败"),


    /**
     * 权限异常
     */
    NOT_HAVE_PERMISSION("0601", "没有权限");

    /**
     * 提示码
     */
    private String code;

    /**
     * 提示内容
     */
    private String msg;

    ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
