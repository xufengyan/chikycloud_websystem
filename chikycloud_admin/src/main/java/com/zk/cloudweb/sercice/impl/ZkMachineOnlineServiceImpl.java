package com.zk.cloudweb.sercice.impl;

import java.util.List;

import com.zk.cloudweb.dao.ZkMachineOnlineDao;
import com.zk.cloudweb.entity.ZkMachineOnline;
import com.zk.cloudweb.sercice.IZkMachineOnlineService;
import com.zk.cloudweb.util.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 连接记录Service业务层处理
 *
 * @author xf
 * @date 2021-03-19
 */
@Service
public class ZkMachineOnlineServiceImpl implements IZkMachineOnlineService
{
    @Autowired
    private ZkMachineOnlineDao zkMachineOnlineMapper;

    /**
     * 查询连接记录
     *
     * @param id 连接记录ID
     * @return 连接记录
     */
    @Override
    public ZkMachineOnline selectZkMachineOnlineById(Integer id)
    {
        return zkMachineOnlineMapper.selectZkMachineOnlineById(id);
    }

    /**
     * 查询连接记录列表
     *
     * @param zkMachineOnline 连接记录
     * @return 连接记录
     */
    @Override
    public List<ZkMachineOnline> selectZkMachineOnlineList(ZkMachineOnline zkMachineOnline)
    {
        return zkMachineOnlineMapper.selectZkMachineOnlineList(zkMachineOnline);
    }

    /**
     * 新增连接记录
     *
     * @param zkMachineOnline 连接记录
     * @return 结果
     */
    @Override
    public int insertZkMachineOnline(ZkMachineOnline zkMachineOnline)
    {
        return zkMachineOnlineMapper.insertZkMachineOnline(zkMachineOnline);
    }

    /**
     * 修改连接记录
     *
     * @param zkMachineOnline 连接记录
     * @return 结果
     */
    @Override
    public int updateZkMachineOnline(ZkMachineOnline zkMachineOnline)
    {
        return zkMachineOnlineMapper.updateZkMachineOnline(zkMachineOnline);
    }

    /**
     * 删除连接记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZkMachineOnlineByIds(String ids)
    {
        return zkMachineOnlineMapper.deleteZkMachineOnlineByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除连接记录信息
     *
     * @param id 连接记录ID
     * @return 结果
     */
    @Override
    public int deleteZkMachineOnlineById(Integer id)
    {
        return zkMachineOnlineMapper.deleteZkMachineOnlineById(id);
    }
}
