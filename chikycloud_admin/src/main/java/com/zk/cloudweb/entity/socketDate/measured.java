package com.zk.cloudweb.entity.socketDate;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.zk.cloudweb.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 测量数据
 * @author xf
 * @version 1.0
 * @date 2020/6/3 11:01
 */

@Data
public class measured extends BaseEntity {

    //测量时间
    private Date meas_date;

    //测量开始时间
    private Date startTime;

    //测量结束时间
    private Date endTime;

    //剩余可用面积
    private BigDecimal surplusArea;

    //测量面积
    private BigDecimal meas_area;

    //测量亩数
    private BigDecimal meas_munum;

    //曲线
    private BigDecimal curve;

    //直线
    private BigDecimal line;




}
