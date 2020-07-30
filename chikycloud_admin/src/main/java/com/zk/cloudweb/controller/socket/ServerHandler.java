package com.zk.cloudweb.controller.socket;

import com.zk.cloudweb.controller.socket.service.DeviceSession;
import com.zk.cloudweb.controller.socket.util.Analysis;
import com.zk.cloudweb.controller.socket.util.Hex_to_Decimal;
import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;
import com.zk.cloudweb.entity.socketLink.SocketPackage;
import com.zk.cloudweb.entity.socketLink.Socketlogin;
import com.zk.cloudweb.sercice.ISocketGPSDataPackageService;
import com.zk.cloudweb.sercice.ISocketMeasureResultService;
import com.zk.cloudweb.sercice.ISocketloginService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 自定义服务端处理器 
 */
@Component
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    //日志
    private static Logger logger = LoggerFactory.getLogger(com.zk.cloudweb.controller.socket.ServerHandler.class);

    //想当于session
    public static final AttributeKey<DeviceSession> KEY = AttributeKey.valueOf("ZK_SOCKET_IO");

    //转换时间的工具
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ISocketloginService socketloginService;

    @Autowired
    private ISocketGPSDataPackageService socketGPSDataPackageService;

    @Autowired
    private ISocketMeasureResultService socketMeasureResultService;


    /**
     * 客户端注册
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        logger.info(String.format("# client registered...：   %s ...", ctx.channel()));
        DeviceSession session = new DeviceSession(ctx.channel());
        session.setHeartbeatNum(0);
        // 绑定客户端到SOCKET
        ctx.channel().attr(KEY).set(session);
    }


    /**
     * 在与客户端的连接已经建立之后将被调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("netty客户端与服务端连接开始...");
    }
 
    /**
     * 当从客户端接收到一个消息时被调用
     * msg 就是硬件传送过来的数据信息
     */   
    @Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DeviceSession deviceSession = ctx.channel().attr(KEY).get();
        //将客户端发送的数据先做统一的处理
        System.out.println(msg.toString());
        SocketPackage socketPackage = Analysis.socket_data(msg.toString());
        //如果消息为空或者不是正常情况下连接的用户直接关闭连接
        if(msg==null||deviceSession==null){
            closeClient(ctx);
            return;
        }

        switch(socketPackage.getPackageType()){
            case "00":
                //语句
                break; //可选
            case "01":
                //语句
                break; //可选
            case "02":
                //语句
                break;
            case "03":
                //语句
                break;
            case "04":
                break;
            case "0B":
                break;
            default : //可选
                //语句
        }
        if (socketPackage!=null){
            System.out.println("数据包类型："+socketPackage.getPackageType());
            if("00".equals(socketPackage.getPackageType())){//登录包
                //判断当前设备是否登录过
                if(deviceSession.getSocketlogin()==null){
                    //获取客户端的ip和端口
                    InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
                    String clientIP = insocket.getAddress().getHostAddress();
                    int clientPort = insocket.getPort();
                    socketPackage.setClientIP(clientIP+":"+clientPort);
                    //解析登陆数据包
                    Socketlogin socketlogin = Analysis.socket_login_data(socketPackage.getData());
                    //将设备信息和登录信息保存到数据库
                    String id =  socketloginService.insertSocketlogin(socketPackage,socketlogin);
                    socketlogin.setId(id);
                    deviceSession.setSocketlogin(socketlogin);
                }
                deviceSession.setHeartbeatNum(0);
                System.out.println("登录包");
            }else if("01".equals(socketPackage.getPackageType())){//心跳包
                //判断如果服务器
                System.out.println("心跳包");

                deviceSession.setHeartbeatNum(0);

            }else if("02".equals(socketPackage.getPackageType())){//测量结果包
                //获取到处理过的测量结果
                SocketMeasurResult socketMeasurResult = Analysis.socket_measure_result(socketPackage.getData(),deviceSession.getSocketlogin().getTerminalID().replaceAll(" ",""));
                //将测量结果包保存到数据库
                if(socketMeasurResult!=null){
                    socketMeasureResultService.insertSocketMeasureResult(socketMeasurResult);
                }
                System.out.println("测量结果包");
                deviceSession.setHeartbeatNum(0);
            } else if ("03".equals(socketPackage.getPackageType())){

                deviceSession.setHeartbeatNum(0);

                System.out.println("设备信息包");
            }
            else if("04".equals(socketPackage.getPackageType())){//GPS数据包
                System.out.println(socketPackage.getData());
                //获取到处理过的测量数据
                List<SocketGPSDataPackage> socketGPSDataPackages = Analysis.socket_gps_data(socketPackage.getData(),deviceSession.getSocketlogin().getTerminalID().replaceAll(" ",""));
                //将测量数据保存到数据库
                if (socketGPSDataPackages.size()>0){
                    socketGPSDataPackageService.insertSocketGPSDataPackageList(socketGPSDataPackages);
                }
                System.out.println("测量数据包");
                deviceSession.setHeartbeatNum(0);

            }else if ("0B".equals(socketPackage.getPackageType())){
                System.out.println("结果不处理");
                deviceSession.setHeartbeatNum(0);

            } else {
                System.out.println("类型不存在，准备关闭连接..................");
                closeClient(ctx);
                return;
            }
        }
        deviceSession.setSocketPackage(socketPackage);
        ctx.channel().attr(KEY).set(deviceSession);

        //处理完客户端发来的时候后对数据进行响应给客户端
        logger.info("业务处理");
    }



/**
 * 客户端与服务端断开连接时调用
 */
@Override
public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        closeClient(ctx);
        logger.info("netty客户端与服务端连接关闭...");
    }
 
    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        DeviceSession deviceSession = ctx.channel().attr(KEY).get();
        SocketPackage socketPackage  = deviceSession.getSocketPackage();
        if (socketPackage!=null){
//            String result = Hex_to_Decimal.StringToByte(socketPackage);
        byte [] b = Hex_to_Decimal.StringToByte(socketPackage);
            //接收完客户端端发送的消息后，对客户端做出回应
            ByteBuf bufff = Unpooled.buffer();
            bufff.writeBytes(b);
            ctx.channel().write(bufff);
            ctx.flush();
            logger.info("信息接收完毕...");
        }
    }
 
    /**
     * 在处理过程中引发异常时被调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        closeClient(ctx);
        System.out.println("异常信息：rn " + cause.getMessage());
    }

    /**
     * 心跳机制  用户事件触发
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        DeviceSession deviceSession = ctx.channel().attr(KEY).get();
        Integer heartbeatNum  = deviceSession.getHeartbeatNum();
        if (evt instanceof IdleStateEvent)
        {
            IdleStateEvent e = (IdleStateEvent) evt;

            //检测 是否 这段时间没有和服务器联系
            if (e.state() == IdleState.ALL_IDLE)
            {
                //判断当前socket请求4次没有回复则将改连接关闭
                if (heartbeatNum>6){
                    closeClient(ctx);
                }else {
                    heartbeatNum++;
                    deviceSession.setHeartbeatNum(heartbeatNum);
                    ctx.channel().attr(KEY).set(deviceSession);
                    System.out.println(new Date()+" "+ctx);
                }
                //检测心跳
//                checkIdle(ctx);


            }
        }

        super.userEventTriggered(ctx, evt);
    }


    /**
     * 关闭客户端连接
     * @param ctx
     */
    public void closeClient(ChannelHandlerContext ctx){
        DeviceSession deviceSession = ctx.channel().attr(KEY).get();
        if (null!=deviceSession.getSocketlogin()){
            if(null!=deviceSession.getSocketlogin().getId()){
                System.out.println("删除在线机器");
                //删除设置在线信息
                socketloginService.delZkSocketLoginById(deviceSession.getSocketlogin());
                //添加登出信息
                socketloginService.insertSocketlogout(deviceSession.getSocketPackage(),deviceSession.getSocketlogin());
            }
        }
        ctx.close();
        System.out.println(ctx+"-----连接关闭");
    }

}

