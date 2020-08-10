package com.zk.cloudweb.service.impl;


import com.zk.cloudweb.dao.UserMapper;
import com.zk.cloudweb.entity.User;
import com.zk.cloudweb.service.IUserService;
import com.zk.cloudweb.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-05-19
 */
@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询用户
     * 
     * @param id 用户ID
     * @return 用户
     */
    @Override
    public User selectUserById(String id)
    {
        return userMapper.selectUserById(id);
    }

    /**
     * 查询用户列表
     * 
     * @param user 用户
     * @return 用户
     */
    @Override
    public List<User> selectUserList(User user)
    {
        if (user.getLimit()!=null&&user.getPage()!=null){
            Integer limit = user.getLimit();
            user.setLimit(user.getLimit()*(user.getPage()-1));
            user.setCount(limit*user.getPage());
        }

        return userMapper.selectUserList(user);
    }

    /**
     * 新增用户
     * 
     * @param user 用户
     * @return 结果
     */
    @Override
    public int insertUser(User user)
    {
        user.setId(Tool.CreateID());
        user.setCreateTime(new Date());
        return userMapper.insertUser(user);
    }

    /**
     * 修改用户
     * 
     * @param user 用户
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 删除用户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids)
    {
        return userMapper.deleteUserById(ids);
    }

    /**
     * 删除用户信息
     * 
     * @param id 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(String id)
    {
        return userMapper.deleteUserById(id);
    }

    @Override
    public User findUser(User realUser) {
        return userMapper.findUser(realUser);
    }

    @Override
    public Integer selectUserListCount(User user) {
        return userMapper.selectUserListCount(user);
    }
}
