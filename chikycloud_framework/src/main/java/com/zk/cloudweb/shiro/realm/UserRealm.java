package com.zk.cloudweb.shiro.realm;


import com.zk.cloudweb.entity.User;
import com.zk.cloudweb.entity.UserMenu;
import com.zk.cloudweb.entity.UserRoleMenu;
import com.zk.cloudweb.service.IUserMenuService;
import com.zk.cloudweb.service.IUserRoleMenuService;
import com.zk.cloudweb.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleMenuService userRoleMenuService;
    @Autowired
    private IUserMenuService userMenuService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0){
        System.out.println("授权");
        Subject subject= SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        UserRoleMenu userRoleMenu = new UserRoleMenu();
        userRoleMenu.setRoleId(user.getRoleId());
        List<UserRoleMenu> userRoleMenus =  userRoleMenuService.selectUserRoleMenuList(userRoleMenu);
        List<UserMenu> userMenus = new ArrayList<>();
        Set<String> menus = new HashSet<>();
        for (UserRoleMenu u:userRoleMenus){
            menus.add(userMenuService.selectUserMenuById(u.getMenuId()).getHref());
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(menus);
        return simpleAuthorizationInfo;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        // TODO Auto-generated method stub
        System.out.println("认证");
 
        //shiro判断逻辑
        UsernamePasswordToken user = (UsernamePasswordToken) arg0;
        User u = new User();
        u.setUName(user.getUsername());
        u.setUPassword(String.copyValueOf(user.getPassword()));
//        User newUser = userService.findUser(realUser);
        User newUser = userService.findUser(u);
        //System.out.println(user.getUsername());
        if(newUser == null){
            //用户名错误
            //shiro会抛出UnknownAccountException异常
            return null;
        }
 
        return new SimpleAuthenticationInfo(newUser,newUser.getUPassword(),"");
    }
 
}
