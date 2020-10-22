package com.zk.cloudweb.controller.socket.client;

import com.zk.cloudweb.util.dateFormat;
import com.zk.cloudweb.util.webSocket.Util.WebSocketConcurrent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;


/**
 * 处理服务端返回的数据
 *
 * @author Administrator
 *
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 在与服务端的连接已经建立之后将被调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }
    /**
     * 处理服务端返回的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        WebSocketConcurrent.AppointSending(ctx.channel(), dateFormat.Date_DateStr(new Date())+"服务器回复：<p style='color:red'>"+msg.toString()+"</p>",true);
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        WebSocketConcurrent.AppointSending(ctx.channel(),dateFormat.Date_DateStr(new Date())+"长时间未发送数据或者发生错误，连接断开",false);
        NettyClient.setNettyClient(null);
        System.out.println("长时间未发送数据或者发生错误，连接断开");
    }
}
