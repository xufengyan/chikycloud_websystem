package com.zk.cloudweb.dao;


import com.zk.cloudweb.entity.UserHeadmenu;

import java.util.List;

/**
 * 头部菜单Mapper接口
 * 
 * @author xf
 * @date 2020-05-21
 */
public interface UserHeadmenuMapper 
{
    /**
     * 查询头部菜单
     * 
     * @param id 头部菜单ID
     * @return 头部菜单
     */
    public UserHeadmenu selectUserHeadmenuById(String id);

    /**
     * 查询头部菜单列表
     * 
     * @param userHeadmenu 头部菜单
     * @return 头部菜单集合
     */
    public List<UserHeadmenu> selectUserHeadmenuList(UserHeadmenu userHeadmenu);

    /**
     * 新增头部菜单
     * 
     * @param userHeadmenu 头部菜单
     * @return 结果
     */
    public int insertUserHeadmenu(UserHeadmenu userHeadmenu);

    /**
     * 修改头部菜单
     * 
     * @param userHeadmenu 头部菜单
     * @return 结果
     */
    public int updateUserHeadmenu(UserHeadmenu userHeadmenu);

    /**
     * 删除头部菜单
     * 
     * @param id 头部菜单ID
     * @return 结果
     */
    public int deleteUserHeadmenuById(String id);

    /**
     * 批量删除头部菜单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserHeadmenuByIds(String[] ids);

    Integer selectUserHeadmenuListCount(UserHeadmenu userHeadmenu);
}
