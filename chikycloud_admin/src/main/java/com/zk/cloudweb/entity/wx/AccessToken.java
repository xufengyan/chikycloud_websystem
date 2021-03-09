package com.zk.cloudweb.entity.wx;

/**
 * 微信Access_token
 * @author xf
 * @version 1.0
 * @date 2020/11/20 17:25
 */
public class AccessToken {

    private String access_token;
    private Integer expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
