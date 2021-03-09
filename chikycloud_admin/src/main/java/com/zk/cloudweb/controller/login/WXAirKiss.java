package com.zk.cloudweb.controller.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zk.cloudweb.entity.wx.AccessToken;
import com.zk.cloudweb.entity.wx.JspTicket;
import com.zk.cloudweb.entity.wx.WxJSPTicketConfig;
import com.zk.cloudweb.util.RedisKey;
import com.zk.cloudweb.util.RedisUtil;
import com.zk.cloudweb.util.StringUtils;
import com.zk.cloudweb.util.wx.getAccess;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.util.UUID;

/**
 * @author xf
 * @version 1.0
 * @date 2020/11/13 15:37
 */
@Controller
public class WXAirKiss {
//    String a = {"access_token":"39_SBgp9rJuw63jXF87R-oBNa3MKeGcgn1qk5ypZRx_l9vGgBxUKbSSIa6ccuBQyyrbg2NZ67rNsaNhdkxgmfjNBqPR3prQWAPlxsVONzfFnlThuC3Q3k4zzl9b2a5D2DA0XGSe9ywMH35TS32CHUGgAEAPCO","expires_in":7200}
//    String b = {"errcode":0,"errmsg":"ok","ticket":"HoagFKDcsGMVCIY2vOjf9qvnHDNZxQPKOITZL_JUx1Bhesig3pvxEStqq_mJEpprcULaZAGweIhgs6qlvD8tKw","expires_in":7200}
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    getAccess access;
    @RequestMapping("/getMachineConnect.html")
    public ModelAndView getWxAirKissHtml(ModelAndView model) throws Exception {
        //获取AccessToken
        AccessToken accessToken = access.getAccessToken();
        //获取JSPTicket
        JspTicket jspTicket = access.getJsApiTicket(accessToken);
        //处理生成WxJSPTicketConfig类，
        WxJSPTicketConfig wxJSPTicketConfig = getAccess.createConfig(jspTicket.getTicket());
        model.setViewName("wx/wxAirkiss");
        model.addObject("wxJspConfig",wxJSPTicketConfig);
        return model;
    }


    @RequestMapping("/WXAirkiss")
    @ResponseBody
    public String getWXAirkissData(String signature,String timestamp,String nonce,String echostr){
        System.out.println("微信请求参数："+signature+" "+timestamp+" "+nonce+" "+echostr);
        return echostr;
    }



//
//    public static void main(String[] args) throws Exception {
//
//        //获取AccessToken
//        AccessToken accessToken = WXAirKiss.getAccessToken();
//        //获取JSPTicket
//        JspTicket jspTicket = WXAirKiss.getJsApiTicket(accessToken);
//        //处理生成WxJSPTicketConfig类，
//        WxJSPTicketConfig wxJSPTicketConfig = WXAirKiss.createConfig(jspTicket.getTicket());
//        //将 处理生成的数据返回到前端
//
//    }





}
