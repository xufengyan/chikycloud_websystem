package com.zk.cloudweb.dao;


import com.zk.cloudweb.entity.UserRole;

import java.util.List;

/**
 * 角色Mapper接口
 * 
 * @author ruoyi
 * @date 2020-05-20
 */
public interface UserRoleMapper 
{
    /**
     * 查询角色
     * 
     * @param id 角色ID
     * @return 角色
     */
    public UserRole selectUserRoleById(String id);

    /**
     * 查询角色列表
     * 
     * @param userRole 角色
     * @return 角色集合
     */
    public List<UserRole> selectUserRoleList(UserRole userRole);

    /**
     * 新增角色
     * 
     * @param userRole 角色
     * @return 结果
     */
    public int insertUserRole(UserRole userRole);

    /**
     * 修改角色
     * 
     * @param userRole 角色
     * @return 结果
     */
    public int updateUserRole(UserRole userRole);

    /**
     * 删除角色
     * 
     * @param id 角色ID
     * @return 结果
     */
    public int deleteUserRoleById(String id);

    /**
     * 批量删除角色
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserRoleByIds(String[] ids);
}
