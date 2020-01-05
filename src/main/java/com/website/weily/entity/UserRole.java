package com.website.weily.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 15:52
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole extends Base {


    private static final long serialVersionUID = 7864615491999504719L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 描述
     */
    private String description;

    /**
     * 注入角色
     * TODO: 添加
     */
    private Role role;
}
