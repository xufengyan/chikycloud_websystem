package com.zk.cloudweb.service.impl;


import com.zk.cloudweb.dao.UserMenuMapper;
import com.zk.cloudweb.entity.UserHeadmenu;
import com.zk.cloudweb.entity.UserMenu;
import com.zk.cloudweb.service.IUserMenuService;
import com.zk.cloudweb.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 菜单Service业务层处理
 * 
 * @author xf
 * @date 2020-05-20
 */
@Service
public class UserMenuServiceImpl implements IUserMenuService
{
    @Autowired
    private UserMenuMapper userMenuMapper;

    /**
     * 查询菜单
     * 
     * @param id 菜单ID
     * @return 菜单
     */
    @Override
    public UserMenu selectUserMenuById(String id)
    {
        return userMenuMapper.selectUserMenuById(id);
    }

    /**
     * 查询菜单列表
     * 
     * @param userMenu 菜单
     * @return 菜单
     */
    @Override
    public List<UserMenu> selectUserMenuList(UserMenu userMenu)
    {
        return userMenuMapper.selectUserMenuList(userMenu);
    }

    /**
     * 新增菜单
     * 
     * @param userMenu 菜单
     * @return 结果
     */
    @Override
    public int insertUserMenu(UserMenu userMenu)
    {
        userMenu.setId(Tool.CreateID());
        userMenu.setIcon("fa fa-list-alt");
        userMenu.setTarget("_self");
        userMenu.setCreateTime(new Date());
        return userMenuMapper.insertUserMenu(userMenu);
    }

    /**
     * 修改菜单
     * 
     * @param userMenu 菜单
     * @return 结果
     */
    @Override
    public int updateUserMenu(UserMenu userMenu)
    {
        return userMenuMapper.updateUserMenu(userMenu);
    }

    /**
     * 删除菜单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserMenuByIds(String ids)
    {
        return userMenuMapper.deleteUserMenuById(ids);
    }

    /**
     * 删除菜单信息
     * 
     * @param id 菜单ID
     * @return 结果
     */
    @Override
    public int deleteUserMenuById(String id)
    {
        return userMenuMapper.deleteUserMenuById(id);
    }

    @Override
    public UserHeadmenu selectALlMenu(UserMenu userMenu) {
        return userMenuMapper.selectALlMenu(userMenu);
    }

    @Override
    public List selectALlMenuList(UserMenu menu) {

        if(null!=menu.getLimit()){
            Integer limit = menu.getLimit();
            menu.setLimit(menu.getLimit()*(menu.getPage()-1));
            menu.setCount(limit*menu.getPage());
        }

        return userMenuMapper.selectALlMenuList(menu);
    }

    @Override
    public Integer selectALlMenuListCount(UserMenu menu) {
        return userMenuMapper.selectALlMenuListCount(menu);
    }
}
