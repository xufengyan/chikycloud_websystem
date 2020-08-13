package com.zk.cloudweb.service;



import com.zk.cloudweb.entity.UserHeadmenu;
import com.zk.cloudweb.entity.UserMenu;

import java.util.List;

/**
 * 菜单Service接口
 * 
 * @author xf
 * @date 2020-05-20
 */
public interface IUserMenuService 
{
    /**
     * 查询菜单
     * 
     * @param id 菜单ID
     * @return 菜单
     */
    public UserMenu selectUserMenuById(String id);

    /**
     * 查询菜单列表
     * 
     * @param userMenu 菜单
     * @return 菜单集合
     */
    public List<UserMenu> selectUserMenuList(UserMenu userMenu);

    /**
     * 新增菜单
     * 
     * @param userMenu 菜单
     * @return 结果
     */
    public int insertUserMenu(UserMenu userMenu);

    /**
     * 修改菜单
     * 
     * @param userMenu 菜单
     * @return 结果
     */
    public int updateUserMenu(UserMenu userMenu);

    /**
     * 批量删除菜单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserMenuByIds(String ids);

    /**
     * 删除菜单信息
     * 
     * @param id 菜单ID
     * @return 结果
     */
    public int deleteUserMenuById(String id);

    public UserHeadmenu selectALlMenu(UserMenu userMenu);

    List selectALlMenuList(UserMenu menu);

    Integer selectALlMenuListCount(UserMenu menu);
}
