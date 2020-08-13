package com.zk.cloudweb.service.impl;


import com.zk.cloudweb.dao.UserRoleMapper;
import com.zk.cloudweb.entity.UserRole;
import com.zk.cloudweb.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色Service业务层处理
 * 
 * @author xf
 * @date 2020-05-20
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService
{
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 查询角色
     * 
     * @param id 角色ID
     * @return 角色
     */
    @Override
    public UserRole selectUserRoleById(String id)
    {
        return userRoleMapper.selectUserRoleById(id);
    }

    /**
     * 查询角色列表
     * 
     * @param userRole 角色
     * @return 角色
     */
    @Override
    public List<UserRole> selectUserRoleList(UserRole userRole)
    {
        return userRoleMapper.selectUserRoleList(userRole);
    }

    /**
     * 新增角色
     * 
     * @param userRole 角色
     * @return 结果
     */
    @Override
    public int insertUserRole(UserRole userRole)
    {
        return userRoleMapper.insertUserRole(userRole);
    }

    /**
     * 修改角色
     * 
     * @param userRole 角色
     * @return 结果
     */
    @Override
    public int updateUserRole(UserRole userRole)
    {
        return userRoleMapper.updateUserRole(userRole);
    }

    /**
     * 删除角色对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserRoleByIds(String ids)
    {
        return userRoleMapper.deleteUserRoleById(ids);
    }

    /**
     * 删除角色信息
     * 
     * @param id 角色ID
     * @return 结果
     */
    @Override
    public int deleteUserRoleById(String id)
    {
        return userRoleMapper.deleteUserRoleById(id);
    }
}
