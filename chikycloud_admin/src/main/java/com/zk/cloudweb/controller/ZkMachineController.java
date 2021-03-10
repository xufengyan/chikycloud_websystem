package com.zk.cloudweb.controller;

import com.github.pagehelper.PageHelper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zk.cloudweb.controller.socket.service.serviceSend;
import com.zk.cloudweb.entity.*;
import com.zk.cloudweb.sercice.*;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.getShiroUser;
import com.zk.cloudweb.util.page.PageUtil;
import com.zk.cloudweb.util.socketChannel.channelSingle;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.epoll.EpollServerChannelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * 设备Controller
 * @author xf
 * @version 1.0
 * @date 2020/6/11 10:39
 */
@Controller
@RequestMapping("zkMachine")
public class ZkMachineController {

    @Value("${ftp.upload.host}")
    private String host;

    @Value("${ftp.upload.port}")
    private int port;

    @Value("${ftp.upload.userName}")
    private String userName;

    @Value("${ftp.upload.passWord}")
    private String passWord;

    @Value("${ftp.upload.basePath}")
    private String basePath;

    @Value("${ftp.upload.upgradeFile}")
    private String upgradeFile;

    @Autowired
    private IZkMachineService zkMachineService;

    @Autowired
    private IZkUserMachineService zkUserMachineService;

    @Autowired
    private ISocketloginService socketLoginService;

    @Autowired
    private IZkMachineSetService zkMachineSetService;

    @Autowired
    private IZkFileService zkFileService;
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
        PageHelper.startPage(zkMachine.getPage(),zkMachine.getLimit());
        List<ZkMachine> restZkMachine = zkUserMachineService.selectZkUserMachineByPage(zkUserMachine);
        return PageUtil.setpage(restZkMachine);
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
        PageHelper.startPage(zkUserMachine.getPage(),zkUserMachine.getLimit());
        List<ZkSocketLogin> zkSocketLogin = socketLoginService.selectOnLineZkSocketLogin(zkUserMachine);
        return PageUtil.setpage(zkSocketLogin);
    }


    /**
     * 查询设备登录记录
     * @return
     */
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


    /**
     * 查询在线机器
     */
    @RequestMapping("/getOnLineMachine")
    @ResponseBody
    public Result getOnLineMachine(){
        channelSingle channelSingleUtil = channelSingle.getChannelUtil();
        Map<String, ChannelHandlerContext>  channelMap = channelSingleUtil.getChannelMap();
        Iterator<String> iter = channelMap.keySet().iterator();
        Map<String,Object> machineNumMap = new HashMap<>();
        Set<Map<String,Object>> machineNumArr = new HashSet<>();
        while(iter.hasNext()) {
            machineNumMap.put("machineNum",iter.next());
            machineNumArr.add(machineNumMap);
        }
        Result result = new Result(ResultEnum.OK,machineNumArr,true);
        return result;
    }


    /**
     * 向设备发送升级包
     */
    @RequestMapping("/MachineUpgrade")
    @ResponseBody
    public Result MachineUpgrade(String [] machineNumArr,String fileId){

        ZkFile zkFile = new ZkFile();
        zkFile.setId(fileId);
        channelSingle channelSingleUtil = channelSingle.getChannelUtil();
        Map<String, ChannelHandlerContext>  channelMap = channelSingleUtil.getChannelMap();
        ZkFile zf = zkFileService.selectEntityByEntity(zkFile);
        if (null != zf){
            for (String s : machineNumArr) {
                if(channelMap.containsKey(s)){
                    ChannelHandlerContext ctx = channelMap.get(s);
                    serviceSend.socket_machine_upgrade(ctx,host,port,userName,passWord,zf.getFilePath(),zf.getFileCRC32(),0);
                    //发送升级包数据
                }
            }
        }
        Result result = new Result(ResultEnum.OK,true);
        return result;
    }





}
