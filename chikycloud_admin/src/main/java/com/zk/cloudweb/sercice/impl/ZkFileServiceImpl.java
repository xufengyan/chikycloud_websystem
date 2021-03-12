package com.zk.cloudweb.sercice.impl;

import com.zk.cloudweb.dao.ZkFileDao;
import com.zk.cloudweb.entity.ZkFile;
import com.zk.cloudweb.sercice.IZkFileService;
import com.zk.cloudweb.util.Tool;
import com.zk.cloudweb.util.getShiroUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/9/2 10:21
 */
@Service
public class ZkFileServiceImpl implements IZkFileService {
    @Autowired
    private ZkFileDao zkFileDao;


    @Override
    public List<ZkFile> selectListByEntity(ZkFile zkFile) {
        return zkFileDao.selectZkFileListByEntity(zkFile);
    }

    @Override
    public ZkFile selectEntityByEntity(ZkFile zkFile) {
        return zkFileDao.selectZkFileByEntity(zkFile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByEntity(ZkFile zkFile) {
        return zkFileDao.updateZkFileByEntity(zkFile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertByEntity(ZkFile zkFile) {
        zkFile.setFileAdmin(getShiroUser.getUser().getUName());
        zkFile.setId(Tool.CreateID());
        zkFile.setCreateTime(new Date());
        return zkFileDao.insertZkFileByEntity(zkFile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String id) {
        return zkFileDao.deleteZkFileById(id);
    }

    @Override
    public int selectZkFileListByEntityCount(ZkFile zkFile) {
        return zkFileDao.selectZkFileListByEntityCount(zkFile);
    }
}
