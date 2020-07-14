package com.zk.cloudweb.entity.socketLink;

import lombok.Data;

/**
* @author xf
* @version 1.0
* @date 2020/6/2 11:56
* socket登录包数据
 */
@Data
public class Socketlogin {
    private String id;
    //终端ID
    private String terminalID;

    //终端类型
    private String terminalType;

    //登录计数
    private Integer loginNum;

    //保留字节
    private String keepByte;

    //功能类型
    private String functionType;
}
