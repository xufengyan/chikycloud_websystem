package com.zk.cloudweb.sercice;

import com.zk.cloudweb.entity.ZkImageDw;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2021-03-10
 */
public interface IZkImageDwService
{
    /**
     * 查询【请填写功能名称】
     *
     * @param zId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public ZkImageDw selectZkImageDwById(Long zId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param zkImageDw 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ZkImageDw> selectZkImageDwList(ZkImageDw zkImageDw);

    /**
     * 新增【请填写功能名称】
     *
     * @param zkImageDw 【请填写功能名称】
     * @return 结果
     */
    public int insertZkImageDw(ZkImageDw zkImageDw);

    /**
     * 修改【请填写功能名称】
     *
     * @param zkImageDw 【请填写功能名称】
     * @return 结果
     */
    public int updateZkImageDw(ZkImageDw zkImageDw);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZkImageDwByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param zId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteZkImageDwById(Long zId);
}
