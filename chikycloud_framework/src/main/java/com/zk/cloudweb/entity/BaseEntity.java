package com.zk.cloudweb.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xf
 * @version 1.0
 * @date 2020/5/18 10:18
 */
@Data
public class BaseEntity {
    //id
    private String id;
    //每页显示条数
    private Integer limit=100;
    //条数
    private Integer count;
    //页数
    private Integer page = 100;

    //创建时间
    private Date createTime;
    //删除标识
    private Integer delType;
}
