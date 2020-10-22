package com.zk.cloudweb.controller;

import com.zk.cloudweb.controller.socket.client.NettyClient;
import com.zk.cloudweb.controller.socket.client.NettyClientSend;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 测试机器
 * @author xf
 * @version 1.0
 * @date 2020/9/16 14:43
 */
@RequestMapping("machineTest")
@Controller
public class zkMachineTestController {


    private static Logger logger = LoggerFactory.getLogger(zkMachineTestController.class);


    /**
     * 跳转到测试机器页面
     */
    @RequestMapping("/machineTestHtml")
    public String machineTestHtml(){
        return "testMachine/testMachine";
    }

    /**
     * 测试机器连接服务
     * @param host
     * @param port
     * @return
     */
    @RequestMapping("/testMachineConnect")
    @ResponseBody
    public Result testMachineConnect(String host,Integer port){
        Result result = null;
        try {
            NettyClient nettyClient = NettyClient.getNettyClient(host,port,"157.128.121:90");
            result = new Result(ResultEnum.OK,"客户端：服务器连接成功",host+":"+port+"-"+"157.128.121:90",true);
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(ResultEnum.UNKONW_ERR,"客户端：服务器连接失败："+e,false);
        }

        return result;
    }

    /**
     * 测试机器发送登录包
     * @param host
     * @param port
     * @return
     */
    @RequestMapping("/testMachineSendLogin")
    @ResponseBody
    public Result testMachineSendLogin(String host,Integer port,String socketKey){
        Result result = null;
        NettyClient nettyClient = NettyClient.getNettyClient(host,port,"157.128.121:90");
        Map<String, Channel> channelMap=  nettyClient.getChannelMap();
        Channel channel = channelMap.get(host+":"+port+"-"+"157.128.121:90");
        String loginHexStr = NettyClientSend.sendLoginData();
        try {
            NettyClientSend.unifySend(loginHexStr,channel,"登录包：");
            result = new Result(ResultEnum.OK,loginHexStr,true);
            logger.info("客户端：测试机器登录包发送完毕...");
        }catch (Exception e){
            logger.info("客户端：测试机器登录包发送失败...");
            result = new Result(ResultEnum.OK,false);
        }
        return result;
    }


    /**
     * 测试机器发送心跳包
     * @param host
     * @param port
     * @return
     */
    @RequestMapping("/testMachineSendHeartbeat")
    @ResponseBody
    public Result testMachineSendHeartbeat(String host,Integer port){
        Result result = null;
        NettyClient nettyClient = NettyClient.getNettyClient(host,port,"157.128.121:90");
        Map<String, Channel> channelMap=  nettyClient.getChannelMap();
        Channel channel = channelMap.get(host+":"+port+"-"+"157.128.121:90");
        String heartbeatStr = NettyClientSend.sendHeartbeatData();
        try {
            NettyClientSend.unifySend(heartbeatStr,channel,"心跳包");
            result = new Result(ResultEnum.OK,heartbeatStr,true);
            logger.info("客户端：测试机器心跳包发送完毕...");
        }catch (Exception e){
            logger.info("客户端：测试机器心跳包发送失败...");
            result = new Result(ResultEnum.OK,false);
        }
        return result;
    }


    /**
     * 测试机器发送测量数据包
     * @param host
     * @param port
     * @return
     */
    @RequestMapping("/testMachineSendMeasure")
    @ResponseBody
    public Result testMachineSendMeasure(String host,Integer port){
        Date startTime = new Date();
        Result result = null;
        NettyClient nettyClient = NettyClient.getNettyClient(host,port,"157.128.121:90");
        Map<String, Channel> channelMap=  nettyClient.getChannelMap();
        Channel channel = channelMap.get(host+":"+port+"-"+"157.128.121:90");
        List<String> measureStrList = NettyClientSend.sendMeasureData();
        try {
            nettyClient.setStartTime(new Date());
            for (String s : measureStrList) {
                NettyClientSend.unifySend(s,channel,"测量数据包:");
                result = new Result(ResultEnum.OK,s,startTime,true);
                logger.info("客户端：测试机器测量数据包发送完毕...");
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            nettyClient.setEndTime(new Date());
        }catch (Exception e){
            logger.info("客户端：测试机器测量数据包发送失败...");
            result = new Result(ResultEnum.OK,false);
        }
        return result;
    }
    /**
     * 测试机器发送测量结果包
     * @param host
     * @param port
     * @return
     */
    @RequestMapping("/testMachineSendMeasureResult")
    @ResponseBody
    public Result testMachineSendMeasureResult(String host, Integer port, Date startTime){
        Date endTime = new Date();
        Result result = null;
        NettyClient nettyClient = NettyClient.getNettyClient(host,port,"157.128.121:90");
        Map<String, Channel> channelMap=  nettyClient.getChannelMap();
        Channel channel = channelMap.get(host+":"+port+"-"+"157.128.121:90");
        String measureResultStr = NettyClientSend.sendMeasureResultData(nettyClient.getStartTime()
                ,nettyClient.getEndTime());
        try {
            NettyClientSend.unifySend(measureResultStr,channel,"测量结果包:");
            result = new Result(ResultEnum.OK,measureResultStr,true);
            logger.info("客户端：测试机器测量结果包发送完毕...");
        }catch (Exception e){
            logger.info("客户端：测试机器测量结果包发送失败...");
            result = new Result(ResultEnum.OK,false);
        }
        return result;
    }
}
