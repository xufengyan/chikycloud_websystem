package com.zk.cloudweb.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public enum UploadFolderEnum {

    IMAGE("imageWeb","jpg","jpg,png,jpeg"),
//    PDF("userPdf","pdf","pdf"),
//    HTML("userHtml","html","html"),
//    IMAGE_APP("imageApp","jpg","jpg,png,jpeg"),
//    DOC("userDoc","doc","doc"),
//    EXCEL("config","xlsx","xlsx");

    MP3("mp3","mp3");


    UploadFolderEnum(String folderName, String suffix) {
        this.folderName =folderName;
        this.suffix =suffix;
    }
 
    UploadFolderEnum(String folderName, String suffix, String fileType) {
        // 文件路径
        this.folderName = folderName;
        // 默认后缀
        this.suffix=suffix;
        // 文件 准许后缀类型
        this.fileType=fileType;
    }
 
    private String  folderName;
    private String  suffix;
    private String  fileType;

    public String getFolderName() {
        return folderName;
    }
 
    public String  doDateFilePath(){
        return  "/"+this.folderName+"/"+ getNowDate();
    }
    public String getNowDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format( new Date(  ) );
        return time;
    }
 
    public String getDefaultFileName(String suffix){
        if(fileType.contains(suffix)){
            return UUID.randomUUID().toString()+"."+suffix;
        }
        return  getDefaultFileName();
    }
 
    public String getDefaultFileName(){
       return UUID.randomUUID().toString()+"."+suffix;
    }
}
