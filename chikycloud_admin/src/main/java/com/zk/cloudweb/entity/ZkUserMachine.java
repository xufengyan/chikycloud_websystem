package com.zk.cloudweb.entity;

import lombok.Data;

/**
 * 用户对应机器对象 zk_user_machine
 * 
 * @author ruoyi
 * @date 2020-06-11
 */
@Data
public class ZkUserMachine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private String umUserId;

    /** 机器id */
    private String umMachineId;


}
