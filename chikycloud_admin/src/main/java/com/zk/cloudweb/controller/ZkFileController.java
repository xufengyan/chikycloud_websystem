package com.zk.cloudweb.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zk.cloudweb.controller.socket.util.Hex_to_Decimal;
import com.zk.cloudweb.entity.ZkFile;
import com.zk.cloudweb.entity.ZkImageDw;
import com.zk.cloudweb.sercice.IZkFileService;
import com.zk.cloudweb.sercice.IZkImageDwService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.StringUtils;
import com.zk.cloudweb.util.dateFormat;
import com.zk.cloudweb.util.export.ImageFile;
import com.zk.cloudweb.util.ftpFile.FTPUtil;
import com.zk.cloudweb.util.ftpFile.FileCRC32;
import com.zk.cloudweb.util.getShiroUser;
import com.zk.cloudweb.util.page.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.net.www.content.image.png;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @Value("${com.zk.file.fileImageUploadPath}")
    private String fileImageUploadPath;

    @Autowired
    private IZkFileService zkFileService;
    @Autowired
    private IZkImageDwService zkImageDwService;
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
     * 跳转将图片转为RGB556文件到图片上传页面
     * @return
     */
    @RequestMapping("/uploadImageToRgb565Html")
    public String uploadImageToRgb565Html(){
        return "image/logImage";
    }
    /**
     * 跳转图片文件下载页面
     * @return
     */
    @RequestMapping("/imageDwListHtml")
    public String imageDwListHtml(){
        return "image/imageDw";
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
        PageHelper.startPage(zkFile.getPage(),zkFile.getLimit());
        List<ZkFile> zkFiles = zkFileService.selectListByEntity(zkFile);
        return PageUtil.setpage(zkFiles);
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


    /**
     * 将用户上传的图片保存到本地
     * @param file
     * @return
     */
    @RequestMapping("/UploadImage")
    @ResponseBody
    public Result UploadImage(MultipartFile file) throws IOException {
        BufferedImage bi = ImageIO.read(file.getInputStream());
        Result result = null;
        if((bi.getHeight()==272&&bi.getWidth()==480)||(bi.getHeight()==800&&bi.getWidth()==480)){
            String path = fileImageUploadPath +"logIamge/"+ dateFormat.Date_yearStr(new Date())+"/";
            File fileUrl = new File(path);
            //判断当前文件夹是否存在
            if(!fileUrl.exists()){
                fileUrl.mkdirs();
            }
            String newFileName = UUID.randomUUID().toString().replace("-", "")+"."+file.getOriginalFilename().split("\\.")[1];
            path = path + newFileName;
            file.getInputStream();
            File dest = new File(path);
            file.transferTo(dest);
            String crc32 = FileCRC32.getCRC32(file.getInputStream());
            ZkFile zkFile = new ZkFile();
            zkFile.setFileCRC32(crc32);
            zkFile.setFileName(file.getOriginalFilename());
            zkFile.setFilePath(path);
            zkFile.setFileType(1);
            zkFileService.insertByEntity(zkFile);
            result = new Result(ResultEnum.OK,true);
        }else {
            result = new Result(ResultEnum.PARAMETER_ERROR,"上传图片像素错误:参照（272x480或800x480）",false);
        }
        return result;
    }

    /**
     * 查询当前用上传过的图片
     * @return
     */
    @RequestMapping("/getImageFileList")
    @ResponseBody
    public Result getImageFileList(ZkFile zkFile) {
        zkFile.setFileAdmin(getShiroUser.getUser().getUName());
        zkFile.setFileType(1);
        PageHelper.startPage(zkFile.getPage(),zkFile.getLimit());
        List<ZkFile> zkFiles = zkFileService.selectListByEntity(zkFile);
        return PageUtil.setpage(zkFiles);
    }




    /**
     * 下载bin升级文件
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("/downloadImageRGB565BinFile")
    @ResponseBody
    public void downloadImageRGB565BinFile(String id,HttpServletResponse response) throws IOException {
        ZkFile zkFile = new ZkFile();
        zkFile.setId(id);
        zkFile = zkFileService.selectEntityByEntity(zkFile);
        String imageBinPath = zkFile.getImageBinPath();
        if(StringUtils.isEmpty(zkFile.getImageBinPath())) {
            File file = new File(zkFile.getFilePath());
            BufferedImage bi = ImageIO.read(file);
            // 获取当前图片的高,宽,ARGB
            int h = bi.getHeight();//800x480 272x480
            int w = bi.getWidth();
            int[] rgbarr = new int[3];
            String binPath = fileImageUploadPath+"/bin/"+dateFormat.Date_yearStr(new Date())+"/"+UUID.randomUUID().toString().replace("-", "")+".bin";
            File target = new File(binPath);
            if(!new File(fileImageUploadPath+"/bin/"+dateFormat.Date_yearStr(new Date())).exists()){
                new File(fileImageUploadPath+"/bin/"+dateFormat.Date_yearStr(new Date())).mkdirs();
            }
            if (target.exists() && target.isFile()) {
                boolean flag = target.delete();
            }
            DataOutputStream out = null;
            try {
                if (target.createNewFile()) {
                    out = new DataOutputStream(new FileOutputStream(binPath, true));
                    for (int i = 0; i < h; i++) {
                        for (int j = 0; j < w; j++) {
                            // getRGB()返回默认的RGB颜色模型(十进制)
                            int pixel = bi.getRGB(j, i);
                            rgbarr[2] = ((pixel & 0xff0000) >> (16));
                            rgbarr[1] = ((pixel & 0xff00) >> (8));
                            rgbarr[0] = ((pixel & 0xff));
                            int d = (((rgbarr[0] >> 3) & 0x1F) << 11) + (((rgbarr[1] >> 2) & 0x3F) << 5) + (((rgbarr[2] >> 3) & 0x1F) << 0);

                            String dd = Hex_to_Decimal.intOneHex16((d & 0xFF) ^ 0x49);
                            String dd2 = Hex_to_Decimal.intOneHex16((d >> 8 & 0xFF) ^ 0x49);
                            byte[] b = Hex_to_Decimal.hex2Bytes(dd);
                            byte[] b2 = Hex_to_Decimal.hex2Bytes(dd2);
                            out.write(b);
                            out.write(b2);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
            zkFile.setImageBinPath(binPath);
            zkFileService.updateByEntity(zkFile);
        }

        // 下载本地文件
        String fileName = "ZK-INKJET-UI.bin"; // 文件的默认保存名
        // 读到流中
        InputStream inStream = new FileInputStream(zkFile.getImageBinPath());// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();

            //下载文件后记录下载记录
            ZkImageDw zkImageDw = new ZkImageDw();
            zkImageDw.setFileName(zkFile.getFileName());
            zkImageDwService.insertZkImageDw(zkImageDw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询图片文件下载列表
     * 当前用户
     */
    @RequestMapping("/getImageDwList")
    @ResponseBody
    public Result getImageDwList(ZkImageDw zkImageDw){
        zkImageDw.setuName(getShiroUser.getUser().getUName());
        PageHelper.startPage(zkImageDw.getPage(),zkImageDw.getLimit());
        List<ZkImageDw> zkImageDws = zkImageDwService.selectZkImageDwList(zkImageDw);
        return  PageUtil.setpage(zkImageDws);
    }

}
