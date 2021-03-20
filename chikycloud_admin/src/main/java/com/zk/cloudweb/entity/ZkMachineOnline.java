package com.zk.cloudweb.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 连接记录对象 zk_machine_online
 *
 * @author xf
 * @date 2021-03-19
 */
public class ZkMachineOnline extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 历史最高在线数量 */
    private Integer num;

    /** $column.columnComment */
    private Date lineTime;

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNum()
    {
        return num;
    }
    public void setLineTime(Date lineTime)
    {
        this.lineTime = lineTime;
    }

    public Date getLineTime()
    {
        return lineTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("num", getNum())
            .append("lineTime", getLineTime())
            .toString();
    }
}
