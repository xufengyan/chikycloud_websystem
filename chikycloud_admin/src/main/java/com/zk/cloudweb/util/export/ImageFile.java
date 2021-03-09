package com.zk.cloudweb.util.export;

import com.zk.cloudweb.util.dateFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author xf
 * @version 1.0
 * @date 2021/3/9 15:48
 */
public class ImageFile {

    public static String uploadImage(MultipartFile file, String basePath) throws IOException {
        String path = basePath +"logIamge/"+ dateFormat.Date_yearStr(new Date())+"/";
        File fileUrl = new File(path);
        //判断当前文件夹是否存在
        if(!fileUrl.exists()){
            fileUrl.mkdirs();
        }
//        String [] s = file.getOriginalFilename().split("\\.");
        String newFileName = UUID.randomUUID().toString().replace("-", "")+"."+file.getOriginalFilename().split("\\.")[1];
        path = path + newFileName;
        File dest = new File(path);
        file.transferTo(dest);
        return path;
    }


}
