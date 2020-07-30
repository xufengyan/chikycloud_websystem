package com.zk.cloudweb.controller;

import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;
import com.zk.cloudweb.sercice.ISocketGPSDataPackageService;
import com.zk.cloudweb.sercice.ISocketMeasureResultService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 查询测量结果列表Controller
 * @author xf
 * @version 1.0
 * @date 2020/6/28 9:16
 */
@Controller
@RequestMapping("/ZKSocketMeasureResult")
public class ZKSocketMeasureResultController {

    @Autowired
    private ISocketMeasureResultService socketMeasureResultService;

    @Autowired
    private ISocketGPSDataPackageService socketGPSDataPackageService;

    /**
     * 跳转到测量列表页面
     * @return
     */
    @RequestMapping("/getSocketMeasureResultListHtml")
    public ModelAndView getSocketMeasureResultListHtml(SocketMeasurResult socketMeasurResult, ModelAndView model){
        model.addObject("machineNum",socketMeasurResult.getMachineNum());
        model.setViewName("admin/measurResultList");
        return model;
    }


    /**
     * 查询测量列表
     * @param socketMeasurResult
     * @return
     */
    @RequestMapping("/getSocketMeasureResultList")
    @ResponseBody
    public Result getSocketMeasureResultList(SocketMeasurResult socketMeasurResult){

        System.out.println(socketMeasurResult.getMachineNum());
        List<SocketMeasurResult> socketMeasurResults = socketMeasureResultService.selectSocketMeasurResultList(socketMeasurResult);

        if (socketMeasurResults.size()>0){
            List<SocketGPSDataPackage> socketGPSDataPackages  = socketGPSDataPackageService.selectSocketGPSDataPackageList(socketMeasurResults.get(0));
            socketMeasurResults.get(0).setSocketGPSDataPackages(socketGPSDataPackages);
        }
        Result result = new Result(ResultEnum.OK,socketMeasurResults,true);
        return result;
    }
}
