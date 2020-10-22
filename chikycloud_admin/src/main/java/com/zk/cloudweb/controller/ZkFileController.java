package com.zk.cloudweb.controller;

import com.zk.cloudweb.controller.socket.util.Hex_to_Decimal;
import com.zk.cloudweb.entity.ZkFile;
import com.zk.cloudweb.sercice.IZkFileService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.ftpFile.FTPUtil;
import com.zk.cloudweb.util.ftpFile.FileCRC32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.net.www.content.image.png;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/9/2 13:39
 */
@RequestMapping("zkFile")
@Controller
public class ZkFileController {


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
    private IZkFileService zkFileService;

    /**
     * 跳转到升级包文件列表页面
     */
    @RequestMapping("/getZkFileListHtml")
    public String getZkFileListHtml(){
        return "uploadFile/uploadFileList";
    }
    /**
     * 跳转到上传文件页面
     */
    @RequestMapping("/addZkFileHtml")
    public String addZkFileHtml(){
        return "uploadFile/uploadFileAdd";
    }


    /**
     * 调转到在线设备升级页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/getMachineUpgradeHtml")
    public ModelAndView getMachineUpgradeHtml(String fileName,String fileVersion,String id,ModelAndView model){
        model.addObject("id",id);
        model.addObject("fileName",fileName);
        model.addObject("fileVersion",fileVersion);
        model.setViewName("uploadFile/machineUpgrade");
        return model;
    }

    /**
     * 查询文件列表
     * @param zkFile
     * @return
     */
    @RequestMapping("/getZkFileList")
    @ResponseBody
    public Result getZkFileList(ZkFile zkFile){
        zkFile.setFileType(0);
        List<ZkFile> zkFiles = zkFileService.selectListByEntity(zkFile);
        Result result = new Result(ResultEnum.OK,zkFiles,true);
        result.setCount(zkFileService.selectZkFileListByEntityCount(zkFile));
        return result;
    }


    /**
     * 文件上传
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Result uploadFile(MultipartFile file, HttpServletRequest request , HttpServletResponse response) throws IOException {
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyyMMdd");
        String fileName = sd.format(new Date())+".png";
        upgradeFile = upgradeFile+sd2.format(new Date())+"/";
        boolean resBool  = FTPUtil.uploadFile(host,port,userName,passWord,basePath,upgradeFile,fileName,file);
        String crc32 = FileCRC32.getCRC32(file.getInputStream());
        if(resBool){
            return new Result(ResultEnum.OK,basePath+upgradeFile+fileName,crc32,true);
        }else {
            return new Result(ResultEnum.UNKONW_ERR,false);
        }
    }

    /**
     * 添加文件信息
     * @param zkFile
     * @return
     */
    @RequestMapping("/addZkFile")
    @ResponseBody
    public Result addZkFile(ZkFile zkFile){
        zkFile.setFileCRC32(Hex_to_Decimal.threeHex16ToSmall(zkFile.getFileCRC32()).replaceAll(" ",""));
        int res = zkFileService.insertByEntity(zkFile);
        Result result = new Result(ResultEnum.OK,true);
        return result;
    }

}
