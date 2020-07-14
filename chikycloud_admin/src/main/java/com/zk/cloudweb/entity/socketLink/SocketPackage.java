package com.zk.cloudweb.entity.socketLink;

import lombok.Data;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/2 11:56
 * socket包数据
 */
@Data
public class SocketPackage {
    private String id;
    //起始位号
    private String startNum;

    //序列号
    private Integer serialNum;

    //数据包类型
    private String packageType;

    //数据包长度
    private Integer packagelenght;

    //数据包
    private String  data;

    //检验和
    private String checkSum;

    //停止位
    private String stopBit;

    private String clientIP;

}
