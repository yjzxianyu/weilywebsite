package com.website.weily.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 15:45
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission extends Base{


    private static final long serialVersionUID = 1451349070940644006L;
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限ID
     */
    private Long permissionId;

    /**
     * 描述
     */
    private String description;

    /**
     * 注入权限
     * TODO:添加
     */
    private Permission permission;
}
