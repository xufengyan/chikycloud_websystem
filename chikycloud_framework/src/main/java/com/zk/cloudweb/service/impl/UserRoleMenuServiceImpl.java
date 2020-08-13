package com.zk.cloudweb.service.impl;


import com.zk.cloudweb.dao.UserRoleMenuMapper;
import com.zk.cloudweb.entity.UserRoleMenu;
import com.zk.cloudweb.service.IUserRoleMenuService;
import com.zk.cloudweb.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色权限Service业务层处理
 * 
 * @author xf
 * @date 2020-05-20
 */
@Service
public class UserRoleMenuServiceImpl implements IUserRoleMenuService
{
    @Autowired
    private UserRoleMenuMapper userRoleMenuMapper;

    /**
     * 查询角色权限
     * 
     * @param id 角色权限ID
     * @return 角色权限
     */
    @Override
    public UserRoleMenu selectUserRoleMenuById(String id)
    {
        return userRoleMenuMapper.selectUserRoleMenuById(id);
    }

    /**
     * 查询角色权限列表
     * 
     * @param userRoleMenu 角色权限
     * @return 角色权限
     */
    @Override
    public List<UserRoleMenu> selectUserRoleMenuList(UserRoleMenu userRoleMenu)
    {
        return userRoleMenuMapper.selectUserRoleMenuList(userRoleMenu);
    }

    /**
     * 新增角色权限
     * 
     * @param userRoleMenu 角色权限
     * @return 结果
     */
    @Override
    public int insertUserRoleMenu(UserRoleMenu userRoleMenu)
    {
        userRoleMenu.setId(Tool.CreateID());
        return userRoleMenuMapper.insertUserRoleMenu(userRoleMenu);
    }

    /**
     * 修改角色权限
     * 
     * @param userRoleMenu 角色权限
     * @return 结果
     */
    @Override
    public int updateUserRoleMenu(UserRoleMenu userRoleMenu)
    {
        return userRoleMenuMapper.updateUserRoleMenu(userRoleMenu);
    }

    /**
     * 删除角色权限对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserRoleMenuByIds(String ids)
    {
        return userRoleMenuMapper.deleteUserRoleMenuById(ids);
    }

    /**
     * 删除角色权限信息
     * 
     * @param id 角色权限ID
     * @return 结果
     */
    @Override
    public int deleteUserRoleMenuById(String id)
    {
        return userRoleMenuMapper.deleteUserRoleMenuById(id);
    }

    @Override
    public int deleteUserRoleMenuByRM(UserRoleMenu userRoleMenu) {
        return userRoleMenuMapper.deleteUserRoleMenuByRM(userRoleMenu);
    }
}
