package com.website.weily.dao;

import com.website.weily.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 14:11
 * @Version
 */

public interface UserDao {

    User findByName(String username);

    boolean update(Long id);
}
