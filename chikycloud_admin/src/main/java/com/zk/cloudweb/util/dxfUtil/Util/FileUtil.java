package com.zk.cloudweb.util.dxfUtil.Util;

import lombok.extern.log4j.Log4j;

import java.io.File;

@Log4j
public class FileUtil {

    public static void deleteFiles(File file) {
        //判断文件是否存在
        if (file.exists()) {
            // dir type
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null || files.length == 0) {
                    if (!file.delete()) {
                        log.error("delete dir fail," + file.getAbsolutePath());
                    }
                } else {
                    log.error("can't delete a not empty dir," + file.getAbsolutePath());
                }
            } else { // file type
                if (!file.delete()) {
                    log.error("delete File fail" + file.getAbsolutePath());
                }
            }
        } else {
            log.warn("file not exists or not's a file" + file.getAbsolutePath());
        }
    }


    /**
     * 删除单个文件
     */
    public static Boolean dalFile(String filePath){
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
            System.out.println("文件已删除");
            return true;
        }
        return false;
    }

}
