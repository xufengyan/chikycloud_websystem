package com.zk.cloudweb.dao;

import com.zk.cloudweb.entity.ZkMachine;
import com.zk.cloudweb.entity.ZkMachineSet;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/8/21 16:03
 */
@Component
public interface ZkMachineSetDao {
    public ZkMachineSet selectZkMachineSet(ZkMachineSet zkMachineSet);

    public List<ZkMachineSet> selectZkMachineSetList(ZkMachineSet zkMachineSet);

    public int insertZkMachineSet(ZkMachineSet zkMachineSet);

    int updateZkMachineSet(ZkMachineSet zkMachineSet);
}
