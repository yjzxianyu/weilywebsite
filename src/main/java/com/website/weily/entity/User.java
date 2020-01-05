package com.website.weily.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 13:58
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base{


    private static final long serialVersionUID = -1580257572947546631L;

    private String username;

    private String password;

    private String type;

    private String salt;

    private UserRole userRole;
}
