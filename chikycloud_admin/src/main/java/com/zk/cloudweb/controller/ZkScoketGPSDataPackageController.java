package com.zk.cloudweb.controller;

import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;
import com.zk.cloudweb.sercice.ISocketGPSDataPackageService;
import com.zk.cloudweb.sercice.ISocketMeasureResultService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.libgeodesy_ct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 查询GPS测量数据包Controller
 * @author xf
 * @version 1.0
 * @date 2020/6/24 16:02
 */
@RequestMapping("/ZkScoketGPSDataPackage")
@Controller
public class ZkScoketGPSDataPackageController {
    @Autowired
    private ISocketGPSDataPackageService socketGPSDataPackageService;

    @Autowired
    private ISocketMeasureResultService socketMeasureResultService;



    /**
     * 根据时间查询测量位置
     * @param socketGPSDataPackage
     * @return
     */
    @RequestMapping("/getMachineMeasureLocation")
    @ResponseBody
    public Result getMachineMeasureLocation(@RequestBody(required = true) SocketGPSDataPackage socketGPSDataPackage){
        SocketGPSDataPackage gpsDataPackage = socketGPSDataPackageService.selectMachineMeasureLactionByDate(socketGPSDataPackage);
        Result result = new Result(ResultEnum.OK,gpsDataPackage,true);
        return result;
    }


    /**
     * 根据测量结果插叙测量数据
     * @param socketMeasurResult
     * @return
     */
    @RequestMapping("/getMachineMeasureGPSData")
    @ResponseBody
    public Result getMachineMeasureGPSData(SocketMeasurResult socketMeasurResult){
        DecimalFormat df = new DecimalFormat("#.0000000");
        Result result = null;
        if ((null!=socketMeasurResult.getId()&&""!=socketMeasurResult.getId())||(null!=socketMeasurResult.getMachineNum()&&""!=socketMeasurResult.getMachineNum())){
            List<SocketGPSDataPackage> socketGPSDataPackages = socketGPSDataPackageService.selectSocketGPSDataPackageList(socketMeasurResult);
            SocketMeasurResult measurResult = socketMeasureResultService.selectSocketMeasurResult(socketMeasurResult);
            libgeodesy_ct libgeodesy_ct = new libgeodesy_ct();
            for (SocketGPSDataPackage socketGPSDataPackage : socketGPSDataPackages) {
                if (socketGPSDataPackage.getLatitude()!=0&&socketGPSDataPackage.getLongitude()!=0) {
                    libgeodesy_ct.libgeodesy_wgs84_to_mars(Double.parseDouble(""+socketGPSDataPackage.getLatitude()), Double.parseDouble(""+socketGPSDataPackage.getLongitude()));
                    socketGPSDataPackage.setLatitude(Float.parseFloat(df.format(libgeodesy_ct.getBd09_lat())));
                    socketGPSDataPackage.setLongitude(Float.parseFloat(df.format(libgeodesy_ct.getBd09_lon())));
                }
            }
            result =new Result(ResultEnum.OK,socketGPSDataPackages,measurResult,true);
        }else {
            result = new Result(ResultEnum.PARAMETER_ERROR);
        }
        return result;
    }


}
