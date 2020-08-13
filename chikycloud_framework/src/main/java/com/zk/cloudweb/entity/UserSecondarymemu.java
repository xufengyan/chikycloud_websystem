package com.zk.cloudweb.entity;

import lombok.Data;

import java.util.List;


/**
 * 二级菜单对象 user_secondarymemu
 * 
 * @author xf
 * @date 2020-05-21
 */
@Data
public class UserSecondarymemu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 菜单名称 */
    private String title;

    /** 标签类型 */
    private String icon;

    /** $column.columnComment */
    private String target;

    /** $column.columnComment */
    private String href;

    /** $column.columnComment */
    private String headmenuId;

    private List<UserMenu> child;
}
