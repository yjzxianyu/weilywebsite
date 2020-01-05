package com.website.weily.dao;

import com.website.weily.util.EncryptionUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 14:14
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired

    private UserRoleDao userRoleDao;

    @Test
    public void findByName() {
        String username="张三";
        System.out.println(userDao.findByName(username));
    }


    //P9ZUE6cQY7XH9Z6fnHomUrQVrAX7DP6LW6M4SPTSl3WvFz4g3WgL8qjfo5TIyDkQ
    @Test
    public void test(){
        System.out.println(EncryptionUtil.getMd5HashSalt(64));
    }

    @Test
    public void test2(){
        String salt ="P9ZUE6cQY7XH9Z6fnHomUrQVrAX7DP6LW6M4SPTSl3WvFz4g3WgL8qjfo5TIyDkQ";
        System.out.println(EncryptionUtil.md5Hash("123456",salt));
    }
    @Test
    public void test3(){
        System.out.println(userRoleDao.getByUserId(1L));
    }
}