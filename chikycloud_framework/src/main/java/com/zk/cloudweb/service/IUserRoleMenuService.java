package com.zk.cloudweb.service;


import com.zk.cloudweb.entity.UserRoleMenu;

import java.util.List;

/**
 * 角色权限Service接口
 * 
 * @author ruoyi
 * @date 2020-05-20
 */
public interface IUserRoleMenuService 
{
    /**
     * 查询角色权限
     * 
     * @param id 角色权限ID
     * @return 角色权限
     */
    public UserRoleMenu selectUserRoleMenuById(String id);

    /**
     * 查询角色权限列表
     * 
     * @param userRoleMenu 角色权限
     * @return 角色权限集合
     */
    public List<UserRoleMenu> selectUserRoleMenuList(UserRoleMenu userRoleMenu);

    /**
     * 新增角色权限
     * 
     * @param userRoleMenu 角色权限
     * @return 结果
     */
    public int insertUserRoleMenu(UserRoleMenu userRoleMenu);

    /**
     * 修改角色权限
     * 
     * @param userRoleMenu 角色权限
     * @return 结果
     */
    public int updateUserRoleMenu(UserRoleMenu userRoleMenu);

    /**
     * 批量删除角色权限
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserRoleMenuByIds(String ids);

    /**
     * 删除角色权限信息
     * 
     * @param id 角色权限ID
     * @return 结果
     */
    public int deleteUserRoleMenuById(String id);

    int deleteUserRoleMenuByRM(UserRoleMenu userRoleMenu);
}
