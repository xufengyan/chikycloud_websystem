package com.zk.cloudweb.controller;

import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;
import com.zk.cloudweb.sercice.ISocketGPSDataPackageService;
import com.zk.cloudweb.sercice.ISocketMeasureResultService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.dateFormat;
import com.zk.cloudweb.util.excel.CommonExcel;
import com.zk.cloudweb.util.export.ZipBacthExport;
import com.zk.cloudweb.util.export.exportCAD;
import com.zk.cloudweb.util.export.exportKML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

/**
 * 查询测量结果列表Controller
 * @author xf
 * @version 1.0
 * @date 2020/6/28 9:16
 */
@Controller
@RequestMapping("/ZKSocketMeasureResult")
public class ZKSocketMeasureResultController {


    @Value("${com.zk.file.ftpFileUploadPath}")
    private String basePath;
    @Autowired
    private ISocketMeasureResultService socketMeasureResultService;

    @Autowired
    private ISocketGPSDataPackageService socketGPSDataPackageService;



    @RequestMapping("/getBeforDateHtml")
    public String getBeforDateHtml(){
        return "admin/measureTiming";
    }
    /**
     * 跳转到测量列表页面
     * @return
     */
    @RequestMapping("/getSocketMeasureResultListHtml")
    public ModelAndView getSocketMeasureResultListHtml(SocketMeasurResult socketMeasurResult, ModelAndView model){
        model.addObject("machineNum",socketMeasurResult.getMachineNum());
        model.setViewName("admin/measurResultList2");
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

        List<SocketMeasurResult> socketMeasurResults = socketMeasureResultService.selectSocketMeasurResultList(socketMeasurResult);

        if (socketMeasurResults.size()>0){
            List<SocketGPSDataPackage> socketGPSDataPackages  = socketGPSDataPackageService.selectSocketGPSDataPackageList(socketMeasurResults.get(0));
            socketMeasurResults.get(0).setSocketGPSDataPackages(socketGPSDataPackages);
        }
        Result result = new Result(ResultEnum.OK,socketMeasurResults,true);
        return result;
    }


    /**
     * 删除测量结果
     * @param socketMeasurResult
     * @return
     */
    @RequestMapping("/deleteSocketMeasureResultById")
    @ResponseBody
    public Result deleteSocketMeasureResultById(SocketMeasurResult socketMeasurResult){
//        List<SocketGPSDataPackage> socketGPSDataPackages  = socketGPSDataPackageService.selectSocketGPSDataPackageList(socketMeasurResult);
        socketMeasurResult.setDelType(1);
        int res = socketMeasureResultService.updateSocketMeasurResult(socketMeasurResult);
        Result result = new Result(ResultEnum.OK,true);
        return result;
    }



    /**
     * 导出CAD文件
     * @param socketMeasurResult
     */
    @RequestMapping("/exportMeasureResultCAD")
    public void exportMeasureResultCAD(SocketMeasurResult socketMeasurResult, HttpServletResponse response , HttpServletRequest request){
        //查询对应测量结果的测量数据
        List<SocketGPSDataPackage> socketGPSDataPackages = socketGPSDataPackageService.selectSocketGPSDataPackageList(socketMeasurResult);
        //下载CAD文件
        exportCAD.generateCAD(socketGPSDataPackages,"CAD工程文件",request,response,basePath);
    }

    /**
     * 批量导出CAD文件
     * @param socketMeasurResult
     * @param response
     * @param request
     */
    @RequestMapping("/exportBatchMeasureResultCAD")
    public void exportBatchMeasureResultCAD(String  machineNum,String startTime,String endTime,
                                            HttpServletResponse response,HttpServletRequest request){

        SocketMeasurResult socketMeasurResult = new SocketMeasurResult();
        socketMeasurResult.setMachineNum(machineNum);
        socketMeasurResult.setStartTime(dateFormat.Date_StrToDate(startTime));
        socketMeasurResult.setEndTime(dateFormat.Date_StrToDate(endTime));

        List<String> pathArr = new ArrayList<>();
        List<SocketMeasurResult> socketMeasurResults = socketMeasureResultService.selectSocketMeasurResultList(socketMeasurResult);
        if (socketMeasurResults.size()>0) {
            for (SocketMeasurResult measurResult : socketMeasurResults) {
                List<SocketGPSDataPackage> socketGPSDataPackages = socketGPSDataPackageService.selectSocketGPSDataPackageList(measurResult);
                if(null!=socketGPSDataPackages&&socketGPSDataPackages.size()>0) {
                    pathArr.add(exportCAD.batchGenerateCAD(socketGPSDataPackages,"工程文件",request,response,basePath));
                }
            }
        }
        try {
            //导出压缩包文件
            ZipBacthExport.batchZip(basePath,"CAD工程文件",pathArr,request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 导出KML文件
     * @param socketMeasurResult
     */
    @RequestMapping("/exportMeasureResultKML")
    public void exportMeasureResultKML(SocketMeasurResult socketMeasurResult,HttpServletResponse response,HttpServletRequest request){
        //查询对应测量结果的测量数据
        List<SocketGPSDataPackage> socketGPSDataPackages = socketGPSDataPackageService.selectSocketGPSDataPackageList(socketMeasurResult);
        try {
            exportKML.setTravelsKml(socketGPSDataPackages,"KML文件",response,request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 批量导出KML文件
     * @param response
     * @param request
     */
    @RequestMapping("/exportBatchMeasureResultKml")
    public void exportBatchMeasureResultKml(String  machineNum,String startTime,String endTime,
                                            HttpServletResponse response,HttpServletRequest request){

        SocketMeasurResult socketMeasurResult = new SocketMeasurResult();
        socketMeasurResult.setMachineNum(machineNum);
        socketMeasurResult.setStartTime(dateFormat.Date_StrToDate(startTime));
        socketMeasurResult.setEndTime(dateFormat.Date_StrToDate(endTime));

        List<String> pathArr = new ArrayList<>();
        List<SocketMeasurResult> socketMeasurResults = socketMeasureResultService.selectSocketMeasurResultList(socketMeasurResult);
        if (socketMeasurResults.size()>0) {
            for (SocketMeasurResult measurResult : socketMeasurResults) {
                List<SocketGPSDataPackage> socketGPSDataPackages = socketGPSDataPackageService.selectSocketGPSDataPackageList(measurResult);
                try {
                    if(null!=socketGPSDataPackages&&socketGPSDataPackages.size()>0){
                        pathArr.add(exportKML.bacthKml(socketGPSDataPackages,"工程文件",request,response,basePath));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            //导出压缩包文件
            ZipBacthExport.batchZip(basePath,"KML文件",pathArr,request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 导出excel
      * @param socketMeasurResult
     * @param response
     * @param request
     */
    @RequestMapping("/exportMeasureResultExcel")
    public void exportMeasureResultExcel(SocketMeasurResult socketMeasurResult,HttpServletResponse response,HttpServletRequest request){

        List<SocketGPSDataPackage> socketGPSDataPackages = socketGPSDataPackageService.selectSocketGPSDataPackageList(socketMeasurResult);

        List<Object> list= new ArrayList<>();

        for (SocketGPSDataPackage measured : socketGPSDataPackages){
            list.add(dateFormat.Date_DateStr(measured.getMeasureTime()));
            list.add(measured.getLongitude());
            list.add(measured.getLatitude());
        }
        String [] head={"时间","经度坐标","纬度坐标"};
        Integer [] lenght={5000,5000,5000};
        try {
            CommonExcel.importExcelliebiao(response,"Excel表格.csv","测量数据",list,head,lenght);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量导出Excel文件
     * @param response
     * @param request
     */
    @RequestMapping("/exportBatchMeasureResultExcel")
    public void exportBatchMeasureResultExcel(String  machineNum,String startTime,String endTime,
                                              HttpServletResponse response,HttpServletRequest request) throws IOException {

        String fileName = "Excelx文件.zip";
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename="
                + fileName);// 指定下载的文件名
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        OutputStream output = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(output);

        SocketMeasurResult socketMeasurResult = new SocketMeasurResult();
        socketMeasurResult.setMachineNum(machineNum);
        socketMeasurResult.setStartTime(dateFormat.Date_StrToDate(startTime));
        socketMeasurResult.setEndTime(dateFormat.Date_StrToDate(endTime));

        List<String> pathArr = new ArrayList<>();
        List<SocketMeasurResult> socketMeasurResults = socketMeasureResultService.selectSocketMeasurResultList(socketMeasurResult);
        int i = 0;
        if (socketMeasurResults.size()>0) {
            for (SocketMeasurResult measurResult : socketMeasurResults) {
                List<SocketGPSDataPackage> socketGPSDataPackages = socketGPSDataPackageService.selectSocketGPSDataPackageList(measurResult);
                try {
                    if(null!=socketGPSDataPackages&&socketGPSDataPackages.size()>0){

                        List<Object> list= new ArrayList<>();

                        for (SocketGPSDataPackage measured : socketGPSDataPackages){
                            list.add(dateFormat.Date_DateStr(measured.getMeasureTime()));
                            list.add(measured.getLongitude());
                            list.add(measured.getLatitude());
                        }
                        String [] head={"时间","经度坐标","纬度坐标"};
                        Integer [] lenght={5000,5000,5000};
                        try {
                            zipOutputStream = CommonExcel.importBacthExcelliebiao(response,"Excel表格.csv","测量数据",list,head,lenght,i,zipOutputStream);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        pathArr.add(exportKML.bacthKml(socketGPSDataPackages,"工程文件",request,response,basePath));
                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        zipOutputStream.flush();
        zipOutputStream.close();

    }





}
