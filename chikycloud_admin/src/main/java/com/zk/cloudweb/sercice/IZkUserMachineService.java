package com.zk.cloudweb.sercice;

import com.zk.cloudweb.entity.ZkMachine;
import com.zk.cloudweb.entity.ZkUserMachine;

import java.util.List;

/**
 * 用户对应机器Service接口
 * 
 * @author ruoyi
 * @date 2020-06-11
 */
public interface IZkUserMachineService 
{
    /**
     * 查询用户对应机器
     * 
     * @param id 用户对应机器ID
     * @return 用户对应机器
     */
    public ZkUserMachine selectZkUserMachineById(String id);

    /**
     * 查询用户对应机器列表
     * 
     * @param zkUserMachine 用户对应机器
     * @return 用户对应机器集合
     */
    public List<ZkUserMachine> selectZkUserMachineList(ZkUserMachine zkUserMachine);

    /**
     * 新增用户对应机器
     * 
     * @param zkUserMachine 用户对应机器
     * @return 结果
     */
    public int insertZkUserMachine(ZkUserMachine zkUserMachine);

    /**
     * 修改用户对应机器
     * 
     * @param zkUserMachine 用户对应机器
     * @return 结果
     */
    public int updateZkUserMachine(ZkUserMachine zkUserMachine);

    /**
     * 批量删除用户对应机器
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZkUserMachineByIds(String ids);

    /**
     * 删除用户对应机器信息
     * 
     * @param id 用户对应机器ID
     * @return 结果
     */
    public int deleteZkUserMachineById(String id);

    /**
     * 查询用户设备
     * @param zkUserMachine
     * @return
     */
    List<ZkMachine> selectZkUserMachineByPage(ZkUserMachine zkUserMachine);

    Integer selectZkUserMachineByPageCount(ZkUserMachine zkUserMachine);


    ZkUserMachine selectZkUserMachineByEntity(ZkMachine zm);
}
