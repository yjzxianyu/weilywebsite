package com.website.weily.dao;

import com.website.weily.entity.RolePermission;

import java.util.List;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 20:51
 * @Version
 */
public interface RolePermissionDao {

    List<RolePermission> getByRoleId(long roleId);
}
