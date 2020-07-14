package com.zk.cloudweb.entity.socketLink;

import com.zk.cloudweb.entity.BaseEntity;
import com.zk.cloudweb.entity.socketDate.measuredGPS;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * socket测量GPS数据包
 * @author xf
 * @version 1.0
 * @date 2020/6/15 17:26
 */
@Data
public class SocketGPSDataPackage extends BaseEntity {

    //GPS数据包的个数
    private Integer dataNum;

    //定位状态
    private String GPSType;

    //经度
    private float longitude;

    //纬度
    private float latitude;

    //速率
    private float rate;

    //航向
    private float course;

    //时间戳---测量时间
    private Date measureTime;

    //对应测量结果包id
    private String measureResId;

    //对应的机器编号
    private String machineNum;
}
