package com.website.weily.dao;

import com.website.weily.entity.UserRole;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 20:30
 * @Version
 */
public interface UserRoleDao {

    UserRole getByUserId(long userId);
}
