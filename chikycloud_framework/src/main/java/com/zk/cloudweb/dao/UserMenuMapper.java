package com.zk.cloudweb.dao;




import com.zk.cloudweb.entity.UserHeadmenu;
import com.zk.cloudweb.entity.UserMenu;

import java.util.List;

/**
 * 菜单Mapper接口
 * 
 * @author ruoyi
 * @date 2020-05-20
 */
public interface UserMenuMapper 
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
     * 删除菜单
     * 
     * @param id 菜单ID
     * @return 结果
     */
    public int deleteUserMenuById(String id);

    /**
     * 批量删除菜单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserMenuByIds(String[] ids);

    public UserHeadmenu selectALlMenu(UserMenu userMenu);

    List selectALlMenuList(UserMenu menu);

    Integer selectALlMenuListCount(UserMenu menu);
}
