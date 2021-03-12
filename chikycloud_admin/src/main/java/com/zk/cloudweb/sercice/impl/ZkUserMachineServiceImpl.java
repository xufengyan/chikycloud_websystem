package com.zk.cloudweb.sercice.impl;


import com.zk.cloudweb.dao.SocketMeasureResultDao;
import com.zk.cloudweb.dao.ZkUserMachineDao;
import com.zk.cloudweb.entity.User;
import com.zk.cloudweb.entity.ZkMachine;
import com.zk.cloudweb.entity.ZkUserMachine;
import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;
import com.zk.cloudweb.sercice.IZkUserMachineService;
import com.zk.cloudweb.util.Tool;
import com.zk.cloudweb.util.getShiroUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ZkUserMachineDao zkUserMachineDao;

    @Autowired
    SocketMeasureResultDao socketMeasureResultDao;
    /**
     * 查询用户对应机器
     *
     * @param id 用户对应机器ID
     * @return 用户对应机器
     */
    @Override
    public ZkUserMachine selectZkUserMachineById(String id)
    {
        return zkUserMachineDao.selectZkUserMachineById(id);
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
        return zkUserMachineDao.selectZkUserMachineList(zkUserMachine);
    }

    /**
     * 新增用户对应机器
     *
     * @param zkUserMachine 用户对应机器
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertZkUserMachine(ZkUserMachine zkUserMachine)
    {
        zkUserMachine.setId(Tool.CreateID());
        zkUserMachine.setCreateTime(new Date());
        return zkUserMachineDao.insertZkUserMachine(zkUserMachine);
    }

    /**
     * 修改用户对应机器
     *
     * @param zkUserMachine 用户对应机器
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateZkUserMachine(ZkUserMachine zkUserMachine)
    {
        return zkUserMachineDao.updateZkUserMachine(zkUserMachine);
    }

    /**
     * 删除用户对应机器对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteZkUserMachineByIds(String ids)
    {
        return zkUserMachineDao.deleteZkUserMachineById(ids);
    }

    /**
     * 删除用户对应机器信息
     *
     * @param id 用户对应机器ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteZkUserMachineById(String id)
    {
        return zkUserMachineDao.deleteZkUserMachineById(id);
    }

    /**
     * 查询用户设备列表
     * @param zkUserMachine
     * @return
     */
    @Override
    public List<ZkMachine> selectZkUserMachineByPage(ZkUserMachine zkUserMachine) {

        return zkUserMachineDao.selectZkUserMachineByPage(zkUserMachine);
    }

    @Override
    public Integer selectZkUserMachineByPageCount(ZkUserMachine zkUserMachine) {
        return zkUserMachineDao.selectZkUserMachineByPageCount(zkUserMachine);
    }

    /**
     * 查询用户在线设备
     * @param zm
     * @return
     */
    @Override
    public ZkUserMachine selectZkUserMachineByEntity(ZkMachine zm) {
        return zkUserMachineDao.selectZkUserMachineByEntity(zm);
    }

    /**
     * 查询统计数据
     * @return
     */
    @Override
    public Map<String, Object> selectMachineDataStatistics() {
        User user = getShiroUser.getUser();
        ZkUserMachine zkUserMachine = new ZkUserMachine();
        zkUserMachine.setUmUserId(user.getId());
        Map<String,Object> map = new HashMap<>();
        //查询当前用户设备数量
        List<ZkMachine> zkMachine = zkUserMachineDao.selectZkUserMachineByPage(zkUserMachine);
        int machineCount = zkMachine.size();
        //查询当前用户测量总面积

        float measureAreaSum = 0f;
        Long measureDateSum = 0l;

        for (ZkMachine zm : zkMachine) {
            SocketMeasurResult socketMeasurResult = new SocketMeasurResult();
            socketMeasurResult.setMachineNum(zm.getMNumber());
            List<SocketMeasurResult> socketMeasurResults = socketMeasureResultDao.selectSocketMeasurResultList(socketMeasurResult);
            for (SocketMeasurResult measurResult : socketMeasurResults) {
                measureAreaSum+=measurResult.getMeasureArea();
                measureDateSum += measurResult.getEndTime().getTime() - measurResult.getStartTime().getTime();
            }
        }

        float d = measureDateSum;

        map.put("machineCount",machineCount);
        map.put("measureAreaSum",measureAreaSum);
        map.put("measureDateSum",(float)(Math.round((d/(1000*60*60))*100))/100);
        return map;
    }
}
