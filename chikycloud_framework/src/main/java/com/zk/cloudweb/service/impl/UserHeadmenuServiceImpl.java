package com.zk.cloudweb.service.impl;

import com.zk.cloudweb.dao.UserHeadmenuMapper;
import com.zk.cloudweb.entity.UserHeadmenu;
import com.zk.cloudweb.service.IUserHeadmenuService;
import com.zk.cloudweb.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 头部菜单Service业务层处理
 *
 * @author xf
 * @date 2020-05-21
 */
@Service
public class UserHeadmenuServiceImpl implements IUserHeadmenuService
{
    @Autowired
    private UserHeadmenuMapper userHeadmenuMapper;

    /**
     * 查询头部菜单
     *
     * @param id 头部菜单ID
     * @return 头部菜单
     */
    @Override
    public UserHeadmenu selectUserHeadmenuById(String id)
    {
        return userHeadmenuMapper.selectUserHeadmenuById(id);
    }

    /**
     * 查询头部菜单列表
     *
     * @param userHeadmenu 头部菜单
     * @return 头部菜单
     */
    @Override
    public List<UserHeadmenu> selectUserHeadmenuList(UserHeadmenu userHeadmenu)
    {
//        if(null!=userHeadmenu.getLimit()){
//            Integer limit = userHeadmenu.getLimit();
//            userHeadmenu.setLimit(userHeadmenu.getLimit()*(userHeadmenu.getPage()-1));
//            userHeadmenu.setCount(limit*userHeadmenu.getPage());
//        }
        return userHeadmenuMapper.selectUserHeadmenuList(userHeadmenu);
    }

    /**
     * 新增头部菜单
     *
     * @param userHeadmenu 头部菜单
     * @return 结果
     */
    @Override
    public int insertUserHeadmenu(UserHeadmenu userHeadmenu)
    {
        userHeadmenu.setId(Tool.CreateID());
        userHeadmenu.setCreateTime(new Date());
        return userHeadmenuMapper.insertUserHeadmenu(userHeadmenu);
    }

    /**
     * 修改头部菜单
     *
     * @param userHeadmenu 头部菜单
     * @return 结果
     */
    @Override
    public int updateUserHeadmenu(UserHeadmenu userHeadmenu)
    {
        return userHeadmenuMapper.updateUserHeadmenu(userHeadmenu);
    }

    /**
     * 删除头部菜单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserHeadmenuByIds(String ids)
    {
        return userHeadmenuMapper.deleteUserHeadmenuById(ids);
    }

    /**
     * 删除头部菜单信息
     *
     * @param id 头部菜单ID
     * @return 结果
     */
    @Override
    public int deleteUserHeadmenuById(String id)
    {
        return userHeadmenuMapper.deleteUserHeadmenuById(id);
    }

    @Override
    public Integer selectUserHeadmenuListCount(UserHeadmenu userHeadmenu) {
        return userHeadmenuMapper.selectUserHeadmenuListCount(userHeadmenu);
    }
}
