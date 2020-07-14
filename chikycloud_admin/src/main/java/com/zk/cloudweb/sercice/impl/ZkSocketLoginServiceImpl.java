package com.zk.cloudweb.sercice.impl;

import com.zk.cloudweb.dao.ZkSocketLoginDao;
import com.zk.cloudweb.entity.ZkSocketLogin;
import com.zk.cloudweb.entity.ZkUserMachine;
import com.zk.cloudweb.sercice.IZkSocketLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author xf
 * @version 1.0
 * @date 2020/6/10 10:05
 */
@Service
public class ZkSocketLoginServiceImpl implements IZkSocketLoginService {

    @Autowired
    ZkSocketLoginDao zkSocketLoginDao;

    @Override
    public ZkSocketLogin selectZkMachine(ZkSocketLogin zkSocketLogin) {
        return null;
    }

    @Override
    public int insertZkMachine(ZkSocketLogin zkSocketLogin) {
        return 0;
    }

}
