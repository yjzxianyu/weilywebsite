package com.website.weily.config;

import com.website.weily.dao.UserDao;
import com.website.weily.entity.Permission;
import com.website.weily.entity.Role;
import com.website.weily.entity.RolePermission;
import com.website.weily.entity.User;
import com.website.weily.entity.UserRole;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 15:38
 * @Version
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;


    /**
     * 为用户授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //根据前端传入的用户信息封装未user对象
        User user = (User) principals.getPrimaryPrincipal();
        //获取前端传入的用户名
        String username = user.getUsername();
        //根据用户名查询数据库中对应的记录
        User user1 = userDao.findByName(username);
        //如果数据库中有该用户名对应的记录，就进行授权操作
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (user1 != null) {
            UserRole userRole = user.getUserRole();
            Role role = null;
            if (userRole != null) {
                role = userRole.getRole();
            }
            if (role != null) {
                //为用户授予角色
                info.addRole(role.getName());

                //用户授予权限
                Set<String> permissionCollection = new HashSet<>();
                List<RolePermission> rolePermissionList = role.getRolePermissionList();
                if (rolePermissionList != null) {
                    for (RolePermission rolePermission : rolePermissionList) {
                        if (rolePermission != null) {
                            Permission permission = rolePermission.getPermission();
                            if (permission != null) {
                                permissionCollection.add(permission.getName());
                            }
                        }
                    }
                    info.addStringPermissions(permissionCollection);
                }
            }
            return info;
        } else {
            return info;
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //token携带用户信息
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //获取用户名
        String username = usernamePasswordToken.getUsername();
        //根据用户名查询数据库中对应记录
        User user = userDao.findByName(username);
        if (user == null){



            throw new AuthenticationException();
        }

        //当前realm对象
        String realmName = getName();
        //封装用户信息，构建AuthenticationInfo对象并返回
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),ByteSource.Util.bytes(user.getSalt()),realmName);
        return authenticationInfo;
    }

    /**
     * 重写方法,清除当前用户的的 授权缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
}
