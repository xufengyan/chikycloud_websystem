package com.zk.cloudweb.util.export;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author xf
 * @version 1.0
 * @date 2020/4/7 10:38
 */
public class ZipBacthExport {

    public  static void batchZip(String filePath,String fileName, List<String> filePathArr, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileZip =fileName+".zip";
        filePath = filePath+"/"+fileZip;
        File zipFile = new File(filePath);
        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        try {
            //构造最终压缩包的输出流
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for(int i =0;i<filePathArr.size();i++){
                File file = new File(filePathArr.get(i));
                //File file = new File(pathtytytyt[i]);
                //将需要压缩的文件格式化为输入流
                zipSource = new FileInputStream(file);
                //压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样
                //这里的name就是文件名，文件名和之前的重复就会导致文件被覆盖，在这用i加文件名进行单一文件识别
                ZipEntry zipEntry = new ZipEntry(i+file.getName());
                //定位该压缩条目位置，开始写入文件到压缩包中
                zipStream.putNextEntry(zipEntry);
                //输入缓冲流
                bufferStream = new BufferedInputStream(zipSource, 1024 * 10);
                int read = 0;
                //创建读写缓冲区
                byte[] buf = new byte[1024 * 10];
                while((read = bufferStream.read(buf, 0, 1024 * 10)) != -1)
                {
                    zipStream.write(buf, 0, read);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if(null != bufferStream) bufferStream.close();
                if(null != zipStream) zipStream.close();
                if(null != zipSource) zipSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*// 写流文件到前端浏览器
		ServletOutputStream os = response.getOutputStream();
		response.setContentType("application/x-octet-stream");
		response.setContentLength((int) zipFile.length());
		response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileZip, "UTF-8"));
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(filePath));
			bos = new BufferedOutputStream(os);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
			File obj = new File(filePath);
			if (obj.exists()) {
				obj.delete();//删除服务器本地产生的临时压缩文件
			}
		}*/


        //进行浏览器下载
        //获得浏览器代理信息
        final String userAgent = request.getHeader("USER-AGENT");
        //判断浏览器代理并分别设置响应给浏览器的编码格式
        String finalFileName = null;
        if(StringUtils.contains(userAgent, "MSIE")||StringUtils.contains(userAgent,"Trident")){//IE浏览器
            finalFileName = URLEncoder.encode(fileZip,"UTF-8");
            System.out.println("IE浏览器");
        }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
            finalFileName = URLEncoder.encode(fileZip,"UTF-8");
        }else{
            finalFileName = URLEncoder.encode(fileZip,"UTF-8");//其他浏览器
        }
        response.setContentType("application/x-octet-stream");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
        response.setHeader("Content-Disposition" ,"attachment;filename=" +finalFileName);//下载文件的名称

        ServletOutputStream servletOutputStream=response.getOutputStream();
        DataOutputStream temps = new DataOutputStream(servletOutputStream);

        DataInputStream in = new DataInputStream(new FileInputStream(filePath));//浏览器下载文件的路径
        byte[] b = new byte[2048];
        File reportZip=new File(filePath);//之后用来删除临时压缩文件
        try {
            while ((in.read(b)) != -1) {
                temps.write(b);
            }
            temps.flush();
        } catch (Exception e) {
            e.printStackTrace();
//            optLogsvc.saveLog(au.getUsername(), au.getAttribute("F_BRNO"), au.getAttribute("F_LSTIP"),
//                    TOptlogService.TYPE_MR, "", au.getUsername() + "批量下载图片"+fileZip+"失败！");
        }finally{
            if(temps!=null) temps.close();
            if(in!=null) in.close();
            if(reportZip!=null) reportZip.delete();//删除服务器本地产生的临时压缩文件
            servletOutputStream.close();
        }

    }


}
