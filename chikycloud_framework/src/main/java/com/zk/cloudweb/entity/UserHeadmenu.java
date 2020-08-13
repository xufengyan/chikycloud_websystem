package com.zk.cloudweb.entity;

import lombok.Data;

import java.util.List;

/**
 * 头部菜单对象 user_headmenu
 * 
 * @author xf
 * @date 2020-05-21
 */
@Data
public class UserHeadmenu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 菜单名称 */
    private String title;

    /** 菜单地址 */
    private String href;

    private String icon;

    /** $column.columnComment */
    private String target;

    private List<UserSecondarymemu> child;
}
