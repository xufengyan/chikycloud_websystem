package com.zk.cloudweb.dao;

import com.zk.cloudweb.entity.ZkMachine;
import org.springframework.stereotype.Component;

/**
 * 设备表DAO
 * @author xf
 * @version 1.0
 * @date 2020/6/10 10:06
 */
@Component
public interface ZkMachineDao {

    public ZkMachine selectZkMachine(ZkMachine zkMachine);

    public int insertZkMachine(ZkMachine zkMachine);

    int updateZkMachine(ZkMachine zkMachine);

}
