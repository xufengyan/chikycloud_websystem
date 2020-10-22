package com.zk.cloudweb.util.webSocket.Util;

import com.alibaba.fastjson.JSON;
import com.zk.cloudweb.controller.socket.client.NettyClient;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/webSocketConConcurrent/{key}")
@Slf4j
public class WebSocketConcurrent {

    /**
     * 需要用来给客户端发送消息
     */
    private Session session;
    /**
     * 标识当前连接的用户名
     */
    private String name;


    /**
     * 用来存储所有连接服务器的客户端，使用的是线程安全的HashMap来存储的
     */
    private static ConcurrentHashMap<Channel, WebSocketConcurrent> webSocketMap = new ConcurrentHashMap<>();


    /**
     * 连接websocket
     * @param session
     * @param key
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "key") String key){
        NettyClient nettyClient = NettyClient.getNettyClient("",0,"");
        Channel channel = nettyClient.getChannelMap().get(key);
        this.session = session;
        this.name = name;
        webSocketMap.put(channel,this);
        log.info(this.name+"：[WebSocket] 连接成功，当前在线人数："+webSocketMap.size());
    }

    /**
     * 关闭websocket连接
     * @param session
     * @param key
     */
    @OnClose
    public void onClose(Session session, @PathParam("key") String key){
        NettyClient nettyClient = NettyClient.getNettyClient("",0,"");
        Channel channel = nettyClient.getChannelMap().get(key);
        this.session = session;
        this.name = name;
        webSocketMap.remove(channel);
        log.info(this.name+"[WebSocket] 退出成功，当前在线人数："+webSocketMap.size());
    }

    @OnMessage
    public void onMessage(String m){

    }


    /**
     * 群发消息
     * @param m
     */
    public void GroupSending(String m){

    }


    /**
     * 指定人发送
     */
    public static void AppointSending(Channel channel,String massage,boolean bool){
        try {

            webSocketMap.get(channel).session.getBasicRemote().sendText(massage);
            if(!bool){
                webSocketMap.get(channel).session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
