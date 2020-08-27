package com.zk.cloudweb.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zk.cloudweb.controller.socket.service.serviceSend;
import com.zk.cloudweb.entity.*;
import com.zk.cloudweb.sercice.ISocketloginService;
import com.zk.cloudweb.sercice.IZkMachineService;
import com.zk.cloudweb.sercice.IZkMachineSetService;
import com.zk.cloudweb.sercice.IZkUserMachineService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.getShiroUser;
import com.zk.cloudweb.util.socketChannel.channelSingle;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 设备Controller
 * @author xf
 * @version 1.0
 * @date 2020/6/11 10:39
 */
@Controller
@RequestMapping("zkMachine")
public class ZkMachineController {


    @Autowired
    private IZkMachineService zkMachineService;

    @Autowired
    private IZkUserMachineService zkUserMachineService;

    @Autowired
    private ISocketloginService socketLoginService;

    @Autowired
    private IZkMachineSetService zkMachineSetService;
    /**
     * 跳转列表页
     * @return
     */
    @RequestMapping("/getZkMachineListHtml")
    public String getOnLineZkMachineListHtml(){
        return "admin/machineList";
    }

    /**
     * 跳转用户绑定设备页面
     * @return
     */
    @RequestMapping("/addZkMachineHtml")
    public String addZkMachineHtml(){
        return "admin/machineAdd";
    }

    /**
     * 跳转到在线设备页面
     * @return
     */
    @RequestMapping("/getZkMachineLoginListHtml")
    public String getOnLineZkMachineLoginListHtml(){
        return "admin/onLineMachineList";
    }


    /**
     * 跳转到查询设备位置页面
     * @return
     */
    @RequestMapping("/getMachineLocationHtml")
    public ModelAndView getMachineLocationHtml(String mnumber, ModelAndView model){
        model.addObject("mnumber",mnumber);
        model.setViewName("admin/machineLocation");
        return model;
    }

    /**
     * 跳转设备修改页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/updateZkMachineHtml")
    public ModelAndView updateZkMachineHtml(ModelAndView model,String id){
        model.addObject("id",id);
        model.setViewName("admin/machineUpdate");
        return model;
    }


    @RequestMapping("userMachineSetHtml")
    public ModelAndView userMachineSetHtml(ModelAndView model,String machineNum){
        model.addObject("machineNum",machineNum);
        model.setViewName("admin/machineSet");
        return model;
    }


    /**
     * 查询机器列表
     * @param zkMachine
     * @return
     */
    @RequestMapping("/getZkMachineList")
    @ResponseBody
    public Result getZkMachineList(ZkMachine zkMachine){
        /**
         * 从shiro中查询用户信息
         */
        User user = getShiroUser.getUser();
        //查询当前用户的设备
        ZkUserMachine zkUserMachine = new ZkUserMachine();
        zkUserMachine.setUmUserId(user.getId());
        zkUserMachine.setLimit(zkMachine.getLimit());
        zkUserMachine.setPage(zkMachine.getPage());
        List<ZkMachine> restZkMachine = zkUserMachineService.selectZkUserMachineByPage(zkUserMachine);
        Result result = new Result(ResultEnum.OK,restZkMachine,true);
        result.setCount(zkUserMachineService.selectZkUserMachineByPageCount(zkUserMachine));
        return result;
    }



    /**
     * 查询在线设备列表
     * @return
     */
    @RequestMapping("/getOnLineZkMachineLoginList")
    @ResponseBody
    public Result getOnLineZkMachineLoginList(ZkUserMachine zkUserMachine){

        //查询当前登录的用户
        User user = getShiroUser.getUser();
        zkUserMachine.setUmUserId(user.getId());
        //查询当前用户的在线设备
       List<ZkSocketLogin> zkSocketLogin = socketLoginService.selectOnLineZkSocketLogin(zkUserMachine);
       int count = socketLoginService.selectOnLineZkSocketLoginCount(zkUserMachine);

        Result result = new Result(ResultEnum.OK,zkSocketLogin,true);
        result.setCount(count);
        return result;
    }


    @RequestMapping("/getMachineLoginHistory")
    @ResponseBody
    public Result getMachineLoginHistory(){
        Result result = null;
        ZkUserMachine zkUserMachine = new ZkUserMachine();
        //查询当前登录的用户
        User user = getShiroUser.getUser();
        zkUserMachine.setUmUserId(user.getId());
        List<ZkSocketLogin> zkSocketLogins = socketLoginService.selectMachineLoginHistiryList(zkUserMachine);

        if (zkSocketLogins.size()>0){
            result = new Result(ResultEnum.OK,zkSocketLogins,true);
        }else {
            result = new Result(ResultEnum.PARAMETER_ERROR,false);
        }

        return result;
    }




    /**
     * 用户添加设备
     * @param zkMachine
     * @return
     */
    @RequestMapping("/addZkMachine")
    @ResponseBody
    public Result addZkMachine(ZkMachine zkMachine){
        //查询当前用户
        User user = getShiroUser.getUser();
        Result result = null;
        ZkMachine zm = new ZkMachine();
        zm.setMNumber(zkMachine.getMNumber());
        //验证设备是否添加过
        ZkMachine resZkMachine = zkMachineService.selectZkMachine(zm);
        //查询用户是否添加过设备
        ZkUserMachine zkUserMachine = zkUserMachineService.selectZkUserMachineByEntity(zm);
        //验证设备是否添加
        if (null!=resZkMachine){
            zkMachine = resZkMachine;
//            result = new Result(ResultEnum.INMACHINE_ERR,false);
        }else {
            zkMachineService.insertZkMachine(zkMachine);
        }
        //验证当前用户是否添加过改设备
        if(null != zkUserMachine){//添加过当前设备
            result = new Result(ResultEnum.INMACHINE_ERR,false);
        }else {
            //为添加过
            ZkUserMachine getZkUserMachine = new ZkUserMachine();
            getZkUserMachine.setUmMachineId(zkMachine.getId());
            getZkUserMachine.setUmUserId(user.getId());
            zkUserMachineService.insertZkUserMachine(getZkUserMachine);
            result = new Result(ResultEnum.OK,"设备添加成功",true);
        }
        return result;
    }

    /**
     * 修改
     * @param zkMachine
     * @return
     */
    @RequestMapping("/updateZkMachine")
    @ResponseBody
    public Result updateZkMachine(ZkMachine zkMachine){
        int res = zkMachineService.updateZkMachine(zkMachine);
        Result result = new Result(ResultEnum.OK,true);
        return result;
    }

    /**
     * 查询单条设备
     * @param zkMachine
     * @return
     */
    @RequestMapping("/selectzkMachineById")
    @ResponseBody
    public Result selectzkMachineById(ZkMachine zkMachine){
        Result result =null;
        if (null!=zkMachine.getId()&&""!=zkMachine.getId()){
            ZkMachine resZkMachine = zkMachineService.selectZkMachine(zkMachine);
            result = new Result(ResultEnum.OK,resZkMachine,true);
        }else {
            result = new Result(ResultEnum.COMMON_NULL,false);
        }
        return result;
    }

    /**
     * 删除用户绑定设备
     * @param zkMachine
     * @return
     */
    @RequestMapping("/deleteUserMachineById")
    @ResponseBody
    public Result deleteUserMachineById(ZkMachine zkMachine){

        ZkUserMachine zkUserMachine = new ZkUserMachine();
        zkUserMachine.setUmMachineId(zkMachine.getId());
        List<ZkUserMachine> zkUserMachines = zkUserMachineService.selectZkUserMachineList(zkUserMachine);
        zkUserMachineService.deleteZkUserMachineById(zkUserMachines.get(0).getId());

        Result result = new Result(ResultEnum.OK,true);
        return result;
    }


    /**
     * 查询首页统计数据
     * @return
     */
    @RequestMapping("/getMachineDataStatistics")
    @ResponseBody
    public Result getMachineDataStatistics(){
        Map<String,Object> map = zkUserMachineService.selectMachineDataStatistics();
        Result result = new Result(ResultEnum.OK,map,true);
        return result;
    }




    /**
     * 设置机器数据
     * @return
     */
    @RequestMapping("/setUserMachine")
    @ResponseBody
    public Result setUserMachine(ZkMachineSet zkMachineSet){

        Result result = null;
        channelSingle channelSingleUtil = channelSingle.getChannelUtil();
        Map<String, ChannelHandlerContext>  channelMap = channelSingleUtil.getChannelMap();
        int res = zkMachineSetService.insertZkMachineSet(zkMachineSet);
        if(channelMap.containsKey(zkMachineSet.getMachineNum())){
            serviceSend.socket_setMachine_Data(channelMap.get(zkMachineSet.getMachineNum()),zkMachineSet);
            result = new Result(ResultEnum.OK,true);
        }
        return result;
    }


}
