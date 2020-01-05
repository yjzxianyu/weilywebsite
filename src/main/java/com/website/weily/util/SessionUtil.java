package com.website.weily.util;

import com.website.weily.constant.SessionConstant;
import com.website.weily.enums.ErrorCodeEnum;
import com.website.weily.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description session工具类
 * @Author youjianzhao
 * @Date 2020/1/4 19:38
 * @Version
 */
@Slf4j
@Service
public class SessionUtil {

    private SessionUtil() {
    }


    /**
     * 校验图形验证码
     */
    public static boolean checkImageCode(String imageCode, HttpServletRequest request) {
        if (StringUtils.isBlank(imageCode)) {
            log.error("SessionUtil#checkImageCode: param is null. imageCode={}, request={}", imageCode, request);
            throw new BusinessException(ErrorCodeEnum.DATA_NULL);
        }

        return imageCode.equalsIgnoreCase(getImageCode(request));
    }

    /**
     * 获取验证码
     */
    public static String getImageCode(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(SessionConstant.IMAGE);
    }
}
