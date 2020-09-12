package com.zk.cloudweb.dao;

import com.zk.cloudweb.entity.ZkFile;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/9/2 10:07
 */
public interface ZkFileDao  {

    List<ZkFile> selectZkFileListByEntity(ZkFile zkFile);

    ZkFile selectZkFileByEntity(ZkFile zkFile);

    int selectZkFileListByEntityCount(ZkFile zkFile);

    int updateZkFileByEntity(ZkFile zkFile);

    int insertZkFileByEntity(ZkFile zkFile);

    int deleteZkFileById(String id);

}
