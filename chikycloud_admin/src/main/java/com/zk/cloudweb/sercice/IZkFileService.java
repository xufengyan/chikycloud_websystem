package com.zk.cloudweb.sercice;

import com.zk.cloudweb.entity.ZkFile;
import com.zk.cloudweb.sercice.baseService.BaseService;

/**
 * @author xf
 * @version 1.0
 * @date 2020/9/2 10:20
 */
public interface IZkFileService extends BaseService<ZkFile> {

    int selectZkFileListByEntityCount(ZkFile zkFile);

}
