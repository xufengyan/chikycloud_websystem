package com.zk.cloudweb.sercice;

import com.zk.cloudweb.entity.ZkMachine;
import com.zk.cloudweb.entity.ZkSocketLogin;
import com.zk.cloudweb.entity.ZkUserMachine;

/**
 * 登录表service
 * @author xf
 * @version 1.0
 * @date 2020/6/10 10:01
 */
public interface IZkSocketLoginService {

    /**
     * 查询
     * @param zkSocketLogin
     * @return
     */
    public ZkSocketLogin selectZkMachine(ZkSocketLogin zkSocketLogin);

    /**
     * 添加
     * @param zkSocketLogin
     * @return
     */
    public int insertZkMachine(ZkSocketLogin zkSocketLogin);


}
