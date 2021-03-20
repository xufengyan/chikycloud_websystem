package com.zk.cloudweb.entity;

import java.util.Date;

/**
 * 【请填写功能名称】对象 zk_image_dw
 *
 * @author xf
 * @date 2021-03-10
 */
public class ZkImageDw extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long zId;

    /** 下载用户名 */
    private String uName;

    /** 文件名 */
    private String fileName;

    public void setzId(Long zId)
    {
        this.zId = zId;
    }

    public Long getzId()
    {
        return zId;
    }
    public void setuName(String uName)
    {
        this.uName = uName;
    }

    public String getuName()
    {
        return uName;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }

}
