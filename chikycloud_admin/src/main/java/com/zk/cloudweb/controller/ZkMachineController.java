package com.zk.cloudweb.controller;

import com.zk.cloudweb.entity.User;
import com.zk.cloudweb.entity.ZkMachine;
import com.zk.cloudweb.entity.ZkSocketLogin;
import com.zk.cloudweb.entity.ZkUserMachine;
import com.zk.cloudweb.sercice.ISocketloginService;
import com.zk.cloudweb.sercice.IZkMachineService;
import com.zk.cloudweb.sercice.IZkSocketLoginService;
import com.zk.cloudweb.sercice.IZkUserMachineService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.getShiroUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    };

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








}
