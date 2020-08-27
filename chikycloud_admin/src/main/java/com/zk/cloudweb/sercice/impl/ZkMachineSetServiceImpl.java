package com.zk.cloudweb.sercice.impl;

import com.zk.cloudweb.dao.ZkMachineSetDao;
import com.zk.cloudweb.entity.ZkMachineSet;
import com.zk.cloudweb.sercice.IZkMachineSetService;
import com.zk.cloudweb.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/8/21 16:03
 */
@Service
public class ZkMachineSetServiceImpl implements IZkMachineSetService {

    @Autowired
    private ZkMachineSetDao zkMachineSetDao;

    @Override
    public ZkMachineSet selectZkMachineSet(ZkMachineSet zkMachineSet) {
        return zkMachineSetDao.selectZkMachineSet(zkMachineSet);
    }

    @Override
    public List<ZkMachineSet> selectZkMachineSetList(ZkMachineSet zkMachineSet) {
        return zkMachineSetDao.selectZkMachineSetList(zkMachineSet);
    }

    @Override
    public int insertZkMachineSet(ZkMachineSet zkMachineSet) {

        zkMachineSet.setId(Tool.CreateID());
        zkMachineSet.setCreateTime(new Date());

        return zkMachineSetDao.insertZkMachineSet(zkMachineSet);
    }

    @Override
    public int updateZkMachineSet(ZkMachineSet zkMachineSet) {
        zkMachineSet.setSendTime(new Date());
        zkMachineSet.setSendType(1);
        return zkMachineSetDao.updateZkMachineSet(zkMachineSet);
    }
}
