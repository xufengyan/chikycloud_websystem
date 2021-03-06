package com.zk.cloudweb.service;


import com.zk.cloudweb.entity.UserRole;

import java.util.List;

/**
 * 角色Service接口
 *
 * @author xf
 * @date 2020-05-20
 */
public interface IUserRoleService
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
     * 批量删除角色
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserRoleByIds(String ids);

    /**
     * 删除角色信息
     *
     * @param id 角色ID
     * @return 结果
     */
    public int deleteUserRoleById(String id);

    List<UserRole> selectUserRoleListById(String[] roleIdArr);
}
