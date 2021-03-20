package com.zk.cloudweb.dao;

import com.zk.cloudweb.entity.ZkMachineOnline;

import java.util.List;

/**
 * 连接记录Mapper接口
 *
 * @author xf
 * @date 2021-03-19
 */
public interface ZkMachineOnlineDao
{
    /**
     * 查询连接记录
     *
     * @param id 连接记录ID
     * @return 连接记录
     */
    public ZkMachineOnline selectZkMachineOnlineById(Integer id);

    /**
     * 查询连接记录列表
     *
     * @param zkMachineOnline 连接记录
     * @return 连接记录集合
     */
    public List<ZkMachineOnline> selectZkMachineOnlineList(ZkMachineOnline zkMachineOnline);

    /**
     * 新增连接记录
     *
     * @param zkMachineOnline 连接记录
     * @return 结果
     */
    public int insertZkMachineOnline(ZkMachineOnline zkMachineOnline);

    /**
     * 修改连接记录
     *
     * @param zkMachineOnline 连接记录
     * @return 结果
     */
    public int updateZkMachineOnline(ZkMachineOnline zkMachineOnline);

    /**
     * 删除连接记录
     *
     * @param id 连接记录ID
     * @return 结果
     */
    public int deleteZkMachineOnlineById(Integer id);

    /**
     * 批量删除连接记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZkMachineOnlineByIds(String[] ids);
}
