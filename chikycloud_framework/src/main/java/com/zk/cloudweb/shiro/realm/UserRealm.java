package com.zk.cloudweb.shiro.realm;


import com.zk.cloudweb.entity.User;
import com.zk.cloudweb.entity.UserMenu;
import com.zk.cloudweb.entity.UserRoleMenu;
import com.zk.cloudweb.service.IUserMenuService;
import com.zk.cloudweb.service.IUserRoleMenuService;
import com.zk.cloudweb.service.IUserService;
import com.zk.cloudweb.util.ServletUtils;
import com.zk.cloudweb.util.constant.ShiroConstants;
import com.zk.cloudweb.util.exception.CaptchaException;
import com.zk.cloudweb.util.exception.UserNotExistsException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import sun.misc.MessageUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
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

        User newUser = null;
        try{
            newUser = userService.findUser(u);
        }
        catch (CaptchaException e)
        {
            throw new AuthenticationException(e.getMessage(), e);
        }
        catch (UserNotExistsException e)
        {
            throw new UnknownAccountException(e.getMessage(), e);
        }
        catch (Exception e){
            log.info("对用户[" + user.getUsername() + "]进行登录验证..验证未通过{}", e.getMessage());
        }
        SimpleAuthenticationInfo info =new SimpleAuthenticationInfo(newUser,newUser.getUPassword(),"");
        return info;
    }
    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
