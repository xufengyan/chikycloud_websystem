package com.zk.cloudweb.sercice.impl;


import com.zk.cloudweb.dao.ZkUserMachineMapper;
import com.zk.cloudweb.entity.ZkMachine;
import com.zk.cloudweb.entity.ZkUserMachine;
import com.zk.cloudweb.sercice.IZkUserMachineService;
import com.zk.cloudweb.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户对应机器Service业务层处理
 * 
 * @author xf
 * @date 2020-06-11
 */
@Service
public class ZkUserMachineServiceImpl implements IZkUserMachineService
{
    @Autowired
    private ZkUserMachineMapper zkUserMachineMapper;

    /**
     * 查询用户对应机器
     * 
     * @param id 用户对应机器ID
     * @return 用户对应机器
     */
    @Override
    public ZkUserMachine selectZkUserMachineById(String id)
    {
        return zkUserMachineMapper.selectZkUserMachineById(id);
    }

    /**
     * 查询用户对应机器列表
     * 
     * @param zkUserMachine 用户对应机器
     * @return 用户对应机器
     */
    @Override
    public List<ZkUserMachine> selectZkUserMachineList(ZkUserMachine zkUserMachine)
    {
        return zkUserMachineMapper.selectZkUserMachineList(zkUserMachine);
    }

    /**
     * 新增用户对应机器
     * 
     * @param zkUserMachine 用户对应机器
     * @return 结果
     */
    @Override
    public int insertZkUserMachine(ZkUserMachine zkUserMachine)
    {
        zkUserMachine.setId(Tool.CreateID());
        zkUserMachine.setCreateTime(new Date());
        return zkUserMachineMapper.insertZkUserMachine(zkUserMachine);
    }

    /**
     * 修改用户对应机器
     * 
     * @param zkUserMachine 用户对应机器
     * @return 结果
     */
    @Override
    public int updateZkUserMachine(ZkUserMachine zkUserMachine)
    {
        return zkUserMachineMapper.updateZkUserMachine(zkUserMachine);
    }

    /**
     * 删除用户对应机器对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZkUserMachineByIds(String ids)
    {
        return zkUserMachineMapper.deleteZkUserMachineById(ids);
    }

    /**
     * 删除用户对应机器信息
     * 
     * @param id 用户对应机器ID
     * @return 结果
     */
    @Override
    public int deleteZkUserMachineById(String id)
    {
        return zkUserMachineMapper.deleteZkUserMachineById(id);
    }

    /**
     * 查询用户设备列表
     * @param zkUserMachine
     * @return
     */
    @Override
    public List<ZkMachine> selectZkUserMachineByPage(ZkUserMachine zkUserMachine) {
        if(null!=zkUserMachine.getLimit()){
            Integer limit = zkUserMachine.getLimit();
            zkUserMachine.setLimit(zkUserMachine.getLimit()*(zkUserMachine.getPage()-1));
            zkUserMachine.setCount(limit*zkUserMachine.getPage());
        }
        return zkUserMachineMapper.selectZkUserMachineByPage(zkUserMachine);
    }

    @Override
    public Integer selectZkUserMachineByPageCount(ZkUserMachine zkUserMachine) {
        return zkUserMachineMapper.selectZkUserMachineByPageCount(zkUserMachine);
    }

    /**
     * 查询用户在线设备
     * @param zm
     * @return
     */
    @Override
    public ZkUserMachine selectZkUserMachineByEntity(ZkMachine zm) {
        return zkUserMachineMapper.selectZkUserMachineByEntity(zm);
    }


}
