package com.zk.cloudweb.util.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zk.cloudweb.entity.wx.AccessToken;
import com.zk.cloudweb.entity.wx.JspTicket;
import com.zk.cloudweb.entity.wx.WxJSPTicketConfig;
import com.zk.cloudweb.util.RedisKey;
import com.zk.cloudweb.util.RedisUtil;
import com.zk.cloudweb.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author xf
 * @version 1.0
 * @date 2020/11/23 17:38
 */
@Component
public class getAccess {

    @Autowired
    RedisUtil redisUtil;

    private String AppSecrct = "4a6f3cbf17067bc1499dac792ee336bf";
    private static String appId = "wx8a2736cf38508762";
    //获取access_tokent
    public AccessToken getAccessToken() throws IOException {
        Jedis jedis = null;
        AccessToken accessToken = new AccessToken();
        String acc = null;
        try{
            jedis=redisUtil.getJedis();
            acc = jedis.get(RedisKey.WXACCESSTOKENKEY);
            if (StringUtils.isEmpty(acc)){
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8a2736cf38508762&secret=4a6f3cbf17067bc1499dac792ee336bf");
                CloseableHttpResponse response = httpClient.execute(httpGet);
                //String access_token ="{"access_token":"39_lILO-sesWbkP3S5WcHxGlpLPiWV-AUgkc776NIaaflqA4t8EKD5IiGn-SIpd1SJQzpJV2NKY5eG5qPdKrDpibs1NLxTBrJoqtR6P7Ss102h1rKURqC3hx2uLzMkgWcWupslF6cdKxy2GdY1sEWMcAIAJBR","expires_in":7200}";
                HttpEntity entity = response.getEntity();
                //使用工具类EntityUtils，从响应中取出实体表示的内容并转换成字符串
                String string = EntityUtils.toString(entity, "utf-8");
                accessToken = JSON.toJavaObject(JSONObject.parseObject(string),AccessToken.class);
                if(null!= accessToken&&!StringUtils.isEmpty(accessToken.getAccess_token())){
                    jedis.set(RedisKey.WXACCESSTOKENKEY,accessToken.getAccess_token());
                    //设置过期时间为7200秒
                    jedis.expire(RedisKey.WXACCESSTOKENKEY,7200);
                }
            }else {
                accessToken.setAccess_token(acc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }

        return accessToken;
    }

    //获取jsapi_ticket
    public JspTicket getJsApiTicket(AccessToken access_token) throws IOException {

        Jedis jedis = null;
        JspTicket jspTicket = new JspTicket();
        String ticket = null;
        try {
            jedis = redisUtil.getJedis();
            ticket = jedis.get(RedisKey.WXJSPTICKETKEY);
            if(StringUtils.isEmpty(ticket)){
                String url ="https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+access_token.getAccess_token();
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                HttpGet httpGet = new HttpGet(url);
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                //使用工具类EntityUtils，从响应中取出实体表示的内容并转换成字符串
                String string = EntityUtils.toString(entity, "utf-8");
                jspTicket = JSON.toJavaObject(JSONObject.parseObject(string),JspTicket.class);
                if(null!= jspTicket&&!StringUtils.isEmpty(jspTicket.getTicket())){
                    jedis.set(RedisKey.WXJSPTICKETKEY,jspTicket.getTicket());
                    //设置过期时间为7200秒
                    jedis.expire(RedisKey.WXJSPTICKETKEY,7200);
                }
            }else {
                jspTicket.setTicket(ticket);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }


        return jspTicket;
    }


    public static WxJSPTicketConfig createConfig(String jspTicket) throws Exception {
        WxJSPTicketConfig config = new WxJSPTicketConfig();
        String nonce = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String src = "jsapi_ticket=" + jspTicket + "&noncestr="
                + nonce + "&timestamp=" + timestamp + "&url="
                + config.getLink();
        String signature = SHA1(src);
        config.setAppId(appId);
        config.setDebug(true);
        config.setNonce(nonce);
        config.setTimestamp(timestamp);
        config.setSignature(signature);
        return config;
    }

    //获取api_ticket
    public static String getApiTicket(String access_token) throws IOException {

        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=wx_card";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //String access_token ="{"access_token":"39_lILO-sesWbkP3S5WcHxGlpLPiWV-AUgkc776NIaaflqA4t8EKD5IiGn-SIpd1SJQzpJV2NKY5eG5qPdKrDpibs1NLxTBrJoqtR6P7Ss102h1rKURqC3hx2uLzMkgWcWupslF6cdKxy2GdY1sEWMcAIAJBR","expires_in":7200}";
        HttpEntity entity = response.getEntity();
        //使用工具类EntityUtils，从响应中取出实体表示的内容并转换成字符串
        String string = EntityUtils.toString(entity, "utf-8");
        return "";
    }

    //Airkiss生成签名
    public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



}
