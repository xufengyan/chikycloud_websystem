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
    private Integer limit;

    private Integer count;

    private Integer page;

    private Date createTime;

}
