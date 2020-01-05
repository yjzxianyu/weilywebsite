package com.website.weily.util;

import com.website.weily.enums.ErrorCodeEnum;
import com.website.weily.vo.ResultVo;

/**
 * @Description api返回数据工具类
 * @Author youjianzhao
 * @Date 2020/1/4 14:51
 * @Version
 */
public final class ResultUtil {

    public static ResultVo success(Object data) {
        ResultVo result = new ResultVo();
        result.setCode(ErrorCodeEnum.SUCCESS.getCode());
        result.setMsg(ErrorCodeEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static ResultVo success() {
        return success(null);
    }

    public static ResultVo error(String code, String msg) {
        ResultVo result = new ResultVo();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static ResultVo error(ErrorCodeEnum errorCodeEnum) {
        ResultVo result = new ResultVo();
        result.setCode(errorCodeEnum.getCode());
        result.setMsg(errorCodeEnum.getMsg());
        result.setData(null);
        return result;
    }
}
