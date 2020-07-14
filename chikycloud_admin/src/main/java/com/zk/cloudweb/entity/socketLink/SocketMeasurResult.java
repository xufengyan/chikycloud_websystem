package com.zk.cloudweb.entity.socketLink;

import com.zk.cloudweb.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 测量结果包
 * @author xf
 * @version 1.0
 * @date 2020/6/18 14:10
 */
@Data
public class SocketMeasurResult extends BaseEntity {

    //测量开始时间
    private Date startTime;

    //测量结束时间
    private Date endTime;

    //剩余可用面积
    private float surplusArea;

    //测量面积
    private float measureArea;

    //测量路程
    private float measureJourney;

    //累计作业面积
    private float cumulativeArea;

    //累计作业时间
    private float cumulativeTime;

    //总重量
    private float totalWeight;

    //设备终端id
    private String machineNum;

    private List<SocketGPSDataPackage> socketGPSDataPackages;
}
