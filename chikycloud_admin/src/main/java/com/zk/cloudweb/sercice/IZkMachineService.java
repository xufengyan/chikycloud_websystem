package com.zk.cloudweb.sercice;

import com.zk.cloudweb.entity.ZkMachine;

/**
 * 设备表service
 * @author xf
 * @version 1.0
 * @date 2020/6/10 9:59
 */
public interface IZkMachineService {

    /**
     * 查询
     * @param zkMachine
     * @return
     */
    public ZkMachine selectZkMachine(ZkMachine zkMachine);

    /**
     * 添加
     * @param zkMachine
     * @return
     */
    public int insertZkMachine(ZkMachine zkMachine);



}
