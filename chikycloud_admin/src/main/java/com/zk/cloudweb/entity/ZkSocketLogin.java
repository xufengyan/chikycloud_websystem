package com.zk.cloudweb.entity;

import lombok.Data;

/**
 * 设备登录在线表
 * @author xf
 * @version 1.0
 * @date 2020/6/10 9:54
 */
@Data
public class ZkSocketLogin extends BaseEntity{
    //设备名称
    private String machineName;
    //设备终端ID
    private String machineNum;
    //登录ip
    private String loginIp;
    //登录登出状态 (0=登录，1=登出)
    private Integer loginType;
}
