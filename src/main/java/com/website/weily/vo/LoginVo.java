package com.website.weily.vo;

import com.website.weily.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 登录返回的vo
 * @Author youjianzhao
 * @Date 2020/1/4 19:49
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo implements Serializable {


    private static final long serialVersionUID = 1351467631947362225L;


    private User user;
}
