package com.zk.cloudweb.entity;

import lombok.Data;

/**
 * 角色权限对象 user_role_menu
 * 
 * @author xf
 * @date 2020-05-20
 */
@Data
public class UserRoleMenu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 菜单id */
    private String menuId;

    /** 角色id */
    private String roleId;


}
