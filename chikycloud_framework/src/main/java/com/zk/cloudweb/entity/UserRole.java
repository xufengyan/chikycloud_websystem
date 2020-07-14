package com.zk.cloudweb.entity;

import lombok.Data;


/**
 * 角色对象 user_role
 * 
 * @author ruoyi
 * @date 2020-05-20
 */
@Data
public class UserRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 角色名称 */
    private String roleName;

    /** $column.columnComment */
    private String rileKey;


}
