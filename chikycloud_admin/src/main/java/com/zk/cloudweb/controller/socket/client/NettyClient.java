package com.zk.cloudweb.controller.socket.client;

import com.zk.cloudweb.controller.socket.util.Hex_to_Decimal;
import com.zk.cloudweb.util.socketChannel.channelSingle;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xf
 * @version 1.0
 * @date 2020/9/14 15:03
 */
public class NettyClient {

    /**
     * 日志记录
     */
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    //当前连接channel
    private Map<String,Channel>  channelMap = new HashMap();
    //开始测量时间
    private Date startTime;
    //结束测量时间
    private Date endTime;
    //单利对象
    private static NettyClient nettyClient =null;
    //创建当前对象
    public static NettyClient getNettyClient(String host, int port,String UserHost){
        if (null == nettyClient){
            try {
                nettyClient = new NettyClient(host, port, UserHost);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return nettyClient;
    }


    public NettyClient(){

    }
    /**
     * 客户端连接
     * @param host
     * @param port
     * @throws InterruptedException
     */
    public NettyClient(String host, int port ,String UserHost) throws InterruptedException {
        // 首先，netty通过ServerBootstrap启动服务端
        Bootstrap client = new Bootstrap();

        //第1步 定义线程组，处理读写和链接事件，没有了accept事件
        EventLoopGroup group = new NioEventLoopGroup();
        client.group(group );

        //第2步 绑定客户端通道
        client.channel(NioSocketChannel.class);

        //第3步 给NIoSocketChannel初始化handler， 处理读写事件
        client.handler(new ChannelInitializer<NioSocketChannel>() {  //通道是NioSocketChannel
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                //字符串编码器，一定要加在SimpleClientHandler 的上面
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(
                        Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                //找到他的管道 增加他的handler
                ch.pipeline().addLast(new SimpleClientHandler());
            }
        });

        //连接服务器
        ChannelFuture future = client.connect(host, port).sync();

        if (future.isSuccess()){
            this.channelMap.put(host+""+port+"-"+UserHost,future.channel());
            logger.info("模拟器连接成功");
        }else {
            logger.info("模拟器连接失败");
        }
        this.channelMap.put(host+":"+port+"-"+UserHost,future.channel());
        //发送数据给服务器
//        User user = new User();
//        user.setAge(12);
//        user.setId(1);
//        user.setName("sssss");
//        future.channel().writeAndFlush("");
        //当通道关闭了，就继续往下走
//        future.channel().closeFuture().sync();

        //接收服务端返回的数据
//        AttributeKey<String> key = AttributeKey.valueOf("ServerData");
//        Object result = future.channel().attr(key).get();
//        System.out.println(result.toString());
    }

    /**
     * 客户端发送消息
     * @param
     * @return
     * @throws InterruptedException
     * @throws
     */
//    public NettyMessage send(NettyMessage message) throws InterruptedException, ExecutionException {
//        ChannelPromise promise = clientHandler.sendMessage(message);
//        promise.await(3, TimeUnit.SECONDS);
//        return clientHandler.getResponse();
//    }
    public static void main(String[] args) {

        NettyClient nettyClient = NettyClient.getNettyClient("192.168.161.1",8899,"157.128.121:90");
        String loginStr = NettyClientSend.sendLoginData();

        byte [] setBytes = Hex_to_Decimal.hex2Bytes((loginStr.replaceAll("</br>","").replaceAll(" ","")));

        ByteBuf bufff = Unpooled.buffer();
        bufff.writeBytes(setBytes);
        nettyClient.channelMap.get("192.168.161.1:8899-157.128.121:90").write(bufff);
        nettyClient.channelMap.get("192.168.161.1:8899-157.128.121:90").flush();

        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> me = NettyClientSend.sendMeasureData();
        byte [] setBytes2 = Hex_to_Decimal.hex2Bytes((me.get(0).replaceAll("</br>","").replaceAll(" ","")));
//
        ByteBuf bufff2 = Unpooled.buffer();
        bufff2.writeBytes(setBytes2);
        nettyClient.channelMap.get("192.168.161.1:8899-157.128.121:90").write(bufff2);
        nettyClient.channelMap.get("192.168.161.1:8899-157.128.121:90").flush();



//        String heartbeatDataStr = NettyClientSend.sendHeartbeatData();
//        System.out.println("loginStr:"+loginStr);
//        System.out.println("loginStr"+loginStr.replaceAll("</br>",""));
//        System.out.println("heartbeatDataStr:"+heartbeatDataStr);
//        System.out.println("heartbeatDataStr"+heartbeatDataStr.replaceAll("</br>",""));
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Map<String, Channel> getChannelMap() {
        return channelMap;
    }

    public void setChannelMap(Map<String, Channel> channelMap) {
        this.channelMap = channelMap;
    }
}
