package com.zk.cloudweb.util;

import com.zk.cloudweb.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 查询shiro中当前登录的用户
 * @author xf
 * @version 1.0
 * @date 2020/6/11 10:52
 */
public class getShiroUser {
    public static User getUser(){
        /**
         * 从shiro中查询用户信息
         */
        Subject subject= SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user;
    }

}
