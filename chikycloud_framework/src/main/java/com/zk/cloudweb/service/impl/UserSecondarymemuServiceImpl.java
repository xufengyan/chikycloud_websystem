package com.zk.cloudweb.service.impl;

import com.zk.cloudweb.dao.UserSecondarymemuMapper;
import com.zk.cloudweb.entity.UserSecondarymemu;
import com.zk.cloudweb.service.IUserSecondarymemuService;
import com.zk.cloudweb.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 二级菜单Service业务层处理
 * 
 * @author xf
 * @date 2020-05-21
 */
@Service
public class UserSecondarymemuServiceImpl implements IUserSecondarymemuService
{
    @Autowired
    private UserSecondarymemuMapper userSecondarymemuMapper;

    /**
     * 查询二级菜单
     * 
     * @param id 二级菜单ID
     * @return 二级菜单
     */
    @Override
    public UserSecondarymemu selectUserSecondarymemuById(String id)
    {
        return userSecondarymemuMapper.selectUserSecondarymemuById(id);
    }

    /**
     * 查询二级菜单列表
     * 
     * @param userSecondarymemu 二级菜单
     * @return 二级菜单
     */
    @Override
    public List<UserSecondarymemu> selectUserSecondarymemuList(UserSecondarymemu userSecondarymemu)
    {
        return userSecondarymemuMapper.selectUserSecondarymemuList(userSecondarymemu);
    }

    /**
     * 新增二级菜单
     * 
     * @param userSecondarymemu 二级菜单
     * @return 结果
     */
    @Override
    public int insertUserSecondarymemu(UserSecondarymemu userSecondarymemu)
    {
        userSecondarymemu.setId(Tool.CreateID());
        userSecondarymemu.setCreateTime(new Date());
        userSecondarymemu.setIcon("fa fa-meetup");
        return userSecondarymemuMapper.insertUserSecondarymemu(userSecondarymemu);
    }

    /**
     * 修改二级菜单
     * 
     * @param userSecondarymemu 二级菜单
     * @return 结果
     */
    @Override
    public int updateUserSecondarymemu(UserSecondarymemu userSecondarymemu)
    {
        return userSecondarymemuMapper.updateUserSecondarymemu(userSecondarymemu);
    }

    /**
     * 删除二级菜单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserSecondarymemuByIds(String ids)
    {
        return userSecondarymemuMapper.deleteUserSecondarymemuById(ids);
    }

    /**
     * 删除二级菜单信息
     * 
     * @param id 二级菜单ID
     * @return 结果
     */
    @Override
    public int deleteUserSecondarymemuById(String id)
    {
        return userSecondarymemuMapper.deleteUserSecondarymemuById(id);
    }

    @Override
    public List selectHeadSecondaryList(UserSecondarymemu userSecondarymenu) {

        if (null!=userSecondarymenu.getLimit()){
            Integer limit = userSecondarymenu.getLimit();
            userSecondarymenu.setLimit(userSecondarymenu.getLimit()*(userSecondarymenu.getPage()-1));
            userSecondarymenu.setCount(limit*userSecondarymenu.getPage());
        }
        return userSecondarymemuMapper.selectHeadSecondaryList(userSecondarymenu);
    }

    @Override
    public Integer selectHeadSecondaryListCount(UserSecondarymemu userSecondarymenu) {
        return userSecondarymemuMapper.selectHeadSecondaryListCount(userSecondarymenu);
    }
}
