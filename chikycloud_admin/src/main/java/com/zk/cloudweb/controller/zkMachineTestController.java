package com.zk.cloudweb.controller;

import com.zk.cloudweb.controller.socket.client.NettyClient;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试机器
 * @author xf
 * @version 1.0
 * @date 2020/9/16 14:43
 */
@RequestMapping("machineTest")
@Controller
public class zkMachineTestController {

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
    @RequestMapping("/testMachineconnect")
    @ResponseBody
    public Result testMachineconnect(String host,Integer port){
        Result result = null;
        try {
            NettyClient nettyClient = NettyClient.getNettyClient(host,port,"157.128.121:90");
            result = new Result(ResultEnum.OK,"服务器连接成功",true);
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(ResultEnum.UNKONW_ERR,"服务器连接失败："+e,false);
        }

        return result;
    }

    /**
     * 测试机器发送登录包
     * @param host
     * @param port
     * @return
     */
    public Result testMachineSendLogin(String host,Integer port){
        Result result = new Result(ResultEnum.OK,true);

        return result;
    }


    /**
     * 测试机器发送心跳包
     * @param host
     * @param port
     * @return
     */
    public Result testMachineSendHeartbeat(String host,Integer port){
        Result result = new Result(ResultEnum.OK,true);

        return result;
    }


    /**
     * 测试机器发送测量数据包
     * @param host
     * @param port
     * @return
     */
    public Result testMachineSendMeasure(String host,Integer port){
        Result result = new Result(ResultEnum.OK,true);

        return result;
    }
    /**
     * 测试机器发送测量结果包
     * @param host
     * @param port
     * @return
     */
    public Result testMachineSendMeasureResult(String host,Integer port){
        Result result = new Result(ResultEnum.OK,true);

        return result;
    }
}
