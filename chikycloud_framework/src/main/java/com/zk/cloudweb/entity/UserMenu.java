package com.zk.cloudweb.entity;

import lombok.Data;

/**
 * 菜单对象 user_menu
 * 
 * @author xf
 * @date 2020-05-20
 */
@Data
public class UserMenu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 菜单名称 */
    private String title;

    /** 菜单地址 */
    private String href;

    private String icon;

    private String target;

    private String sercondarymenuId;
}
