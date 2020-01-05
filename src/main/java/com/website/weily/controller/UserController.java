package com.website.weily.controller;

import com.website.weily.constant.SessionConstant;
import com.website.weily.constant.ShiroConstant;
import com.website.weily.dao.UserDao;
import com.website.weily.entity.User;
import com.website.weily.enums.ErrorCodeEnum;
import com.website.weily.exception.BusinessException;
import com.website.weily.util.ResultUtil;
import com.website.weily.util.SessionUtil;
import com.website.weily.vo.LoginVo;
import com.website.weily.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 19:26
 * @Version
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserDao userDao;
    /**
     * 用户未登录授权提示
     */
    @GetMapping(value = "/authc")
    public ResultVo authc() {
        throw new BusinessException(ErrorCodeEnum.USER_NOT_LOGIN);
    }

    /**
     * 登录
     */
    @PostMapping(value = "/login")
    public ResultVo login(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "imageCode") String imageCode,
                          HttpServletRequest request) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(imageCode)) {
            log.error("UserController#login: param is null. username={},imageCode={}, request={}. ",
                    username, imageCode, request);
            throw new BusinessException(ErrorCodeEnum.DATA_NULL);
        }

        boolean checkCode = SessionUtil.checkImageCode(imageCode,request);
        if (!checkCode) {
            log.error("UserController#login: param is null. username={},imageCode={}, request={}. ",
                    username, imageCode, request);
            throw new BusinessException(ErrorCodeEnum.IMAGE_CODE_ERROR);
        }

        LoginVo loginVo = new LoginVo();

        //用户基本信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        User user = null;

        try {
            //登录，即身份验证
            subject.login(usernamePasswordToken);
            if (subject.isAuthenticated()) {
                user = (User) subject.getPrincipal();
                request.getSession().setAttribute(SessionConstant.USER,user);
            }
        } catch (UnknownAccountException e) {
            throw new BusinessException(ErrorCodeEnum.NO_USER);
        } catch (IncorrectCredentialsException e) {
            throw new BusinessException(ErrorCodeEnum.PASSWORD_ERROR);
        } catch (AuthenticationException e) {
            log.error("其它错误");
            throw new BusinessException(ErrorCodeEnum.NO_USER);
        }
        loginVo.setUser(user);

        return ResultUtil.success(user);
    }

    @PostMapping(value = "/update")
    @RequiresRoles(value = {ShiroConstant.ADMINISTRATOR},logical = Logical.OR)
    public ResultVo update(@RequestParam(value = "id")Long id) {
        boolean flag = userDao.update(id);
        return ResultUtil.success(flag);
    }
}
