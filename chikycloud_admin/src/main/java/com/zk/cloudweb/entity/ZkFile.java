package com.zk.cloudweb.entity;

import lombok.Data;

/**
 * @author xf
 * @version 1.0
 * @date 2020/9/2 9:57
 */
@Data
public class ZkFile extends BaseEntity{

    private String  id;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件地址
     */
    private String filePath;
    /**
     * 版本
     */
    private String fileVersion;
    /**
     * 文件描述
     */
    private String fileAccount;

    /**
     * 文件crc32校验值
     */
    private String fileCRC32;
    /**
     * 文件类型（0为设备升级文件,1为图片文件）
     */
    private Integer fileType;
    /**
     * 上传人
     */
    private String fileAdmin;

    /**
     * 升级包类型（针对不同的机型）
     */
    private String fileUpgradeType;

    /**
     * imageBin文件地址
     */
    private String imageBinPath;
}
