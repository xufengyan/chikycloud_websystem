package com.zk.cloudweb.entity;

import lombok.Data;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/10 9:50
 */
@Data
public class ZkMachine extends BaseEntity {
    //机器名称
    private String mName;

    //机器编号（终端ID）
    private String mNumber;


}
