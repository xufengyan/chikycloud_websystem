package com.zk.cloudweb.sercice.impl;

import com.zk.cloudweb.dao.ZkMachineDao;
import com.zk.cloudweb.entity.ZkMachine;
import com.zk.cloudweb.sercice.IZkMachineService;
import com.zk.cloudweb.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/10 10:04
 */
@Service
public class ZkMachineServiceImpl implements IZkMachineService {

    @Autowired
    ZkMachineDao zkMachineDao;

    @Override
    public ZkMachine selectZkMachine(ZkMachine zkMachine) {
        return zkMachineDao.selectZkMachine(zkMachine);
    }

    @Override
    public int insertZkMachine(ZkMachine zkMachine) {
        zkMachine.setId(Tool.CreateID());
        zkMachine.setCreateTime(new Date());
        return zkMachineDao.insertZkMachine(zkMachine);
    }
}
