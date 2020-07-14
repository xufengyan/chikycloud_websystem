package com.zk.cloudweb.dao;


import com.zk.cloudweb.entity.UserRoleMenu;

import java.util.List;

/**
 * 角色权限Mapper接口
 * 
 * @author ruoyi
 * @date 2020-05-20
 */
public interface UserRoleMenuMapper 
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
     * 删除角色权限
     * 
     * @param id 角色权限ID
     * @return 结果
     */
    public int deleteUserRoleMenuById(String id);

    /**
     * 批量删除角色权限
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserRoleMenuByIds(String[] ids);

    int deleteUserRoleMenuByRM(UserRoleMenu userRoleMenu);
}
