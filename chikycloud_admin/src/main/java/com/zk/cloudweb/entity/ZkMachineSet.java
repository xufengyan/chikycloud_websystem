package com.zk.cloudweb.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户机器设置
 * @author xf
 * @version 1.0
 * @date 2020/8/19 16:44
 */
@Data
public class ZkMachineSet extends BaseEntity{

    /**
     * 机器编号
     */
    private String machineNum;

    /**
     * 剩余可用面积
     */
    private float residueArea = -1f;

    /**
     * 喷幅
     */
    private float spraying = -1f;

    /**
     * 恢复出厂设置
     * 当等于0的时候表示恢复出厂设置
     */
    private int factoryReset = 1;

    /**
     * 恢复应急时间
     * 0表示恢复
     */
    private int factoryTime = 1;


    /**
     * 臂长 1-250cm
     */
    private  int reach = 251;

    /**
     * 语音播报
     */
    private int voice = 11;

    /**
     * 数据是否已发送
     */
    private int sendType = 0;

    /**
     * 发送时间
     */
    private Date sendTime;
}
