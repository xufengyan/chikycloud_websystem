package com.zk.cloudweb.dao;

import com.zk.cloudweb.entity.UserSecondarymemu;

import java.util.List;

/**
 * 二级菜单Mapper接口
 * 
 * @author ruoyi
 * @date 2020-05-21
 */
public interface UserSecondarymemuMapper 
{
    /**
     * 查询二级菜单
     * 
     * @param id 二级菜单ID
     * @return 二级菜单
     */
    public UserSecondarymemu selectUserSecondarymemuById(String id);

    /**
     * 查询二级菜单列表
     * 
     * @param userSecondarymemu 二级菜单
     * @return 二级菜单集合
     */
    public List<UserSecondarymemu> selectUserSecondarymemuList(UserSecondarymemu userSecondarymemu);

    /**
     * 新增二级菜单
     * 
     * @param userSecondarymemu 二级菜单
     * @return 结果
     */
    public int insertUserSecondarymemu(UserSecondarymemu userSecondarymemu);

    /**
     * 修改二级菜单
     * 
     * @param userSecondarymemu 二级菜单
     * @return 结果
     */
    public int updateUserSecondarymemu(UserSecondarymemu userSecondarymemu);

    /**
     * 删除二级菜单
     * 
     * @param id 二级菜单ID
     * @return 结果
     */
    public int deleteUserSecondarymemuById(String id);

    /**
     * 批量删除二级菜单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserSecondarymemuByIds(String[] ids);

    List selectHeadSecondaryList(UserSecondarymemu userSecondarymenu);

    Integer selectHeadSecondaryListCount(UserSecondarymemu userSecondarymenu);
}
