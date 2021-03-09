package com.zk.cloudweb.entity.wx;

/**
 * 微信Js-JDK实体类
 * @author xf
 * @version 1.0
 * @date 2020/11/20 17:23
 */
public class JspTicket {
        private Integer errcode;
        private String errmsg;
        private String ticket;
        private Integer expires_in;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
