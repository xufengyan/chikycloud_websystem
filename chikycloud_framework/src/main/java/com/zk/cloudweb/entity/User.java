package com.zk.cloudweb.entity;

import lombok.Data;

import java.util.Date;


/**
 * 用户对象 user
 * 
 * @author ruoyi
 * @date 2020-05-19
 */
@Data
public class User extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 电话 */
    private String uName;

    /** 密码 */
    private String uPassword;

    /** 邮箱 */
    private String uTel;

    /** 权限等级 */
    private String roleId;

}
