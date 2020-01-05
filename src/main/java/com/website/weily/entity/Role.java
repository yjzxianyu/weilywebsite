package com.website.weily.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 15:44
 * @Version
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Base{


    private static final long serialVersionUID = 6398366878808498076L;
    /**
     * 角色名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;


    /**
     * TODO: 添加
     * 一个角色 对应 多个角色权限
     */
    private List<RolePermission> rolePermissionList;
}
