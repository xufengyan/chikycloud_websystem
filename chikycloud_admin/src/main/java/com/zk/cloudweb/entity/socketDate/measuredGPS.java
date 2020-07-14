package com.zk.cloudweb.entity.socketDate;

import com.zk.cloudweb.entity.BaseEntity;
import lombok.Data;

/**
 * 测量GPS数据
 * @author xf
 * @version 1.0
 * @date 2020/6/3 11:16
 */
@Data
public class measuredGPS extends BaseEntity {

    //经度
    private String longitude;

    //纬度
    private String latitude;

}
