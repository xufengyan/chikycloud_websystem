package com.zk.cloudweb.sercice;

import com.zk.cloudweb.entity.ZkMachine;
import com.zk.cloudweb.entity.ZkMachineSet;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/8/21 16:00
 */
public interface IZkMachineSetService {
    /**
     * 查询
     * @param zkMachineSet
     * @return
     */
    public ZkMachineSet selectZkMachineSet(ZkMachineSet zkMachineSet);

    public List<ZkMachineSet> selectZkMachineSetList(ZkMachineSet zkMachineSet);

    /**
     * 添加
     * @param zkMachineSet
     * @return
     */
    public int insertZkMachineSet(ZkMachineSet zkMachineSet);


    int updateZkMachineSet(ZkMachineSet zkMachineSet);

}
