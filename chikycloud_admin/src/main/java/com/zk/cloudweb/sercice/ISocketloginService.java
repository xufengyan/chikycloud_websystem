package com.zk.cloudweb.sercice;

import com.zk.cloudweb.entity.ZkSocketLogin;
import com.zk.cloudweb.entity.ZkUserMachine;
import com.zk.cloudweb.entity.socketLink.SocketPackage;
import com.zk.cloudweb.entity.socketLink.Socketlogin;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/9 14:15
 */

public interface ISocketloginService {

    /**
     * 添加在线设备和登录记录
     * @param socketPackage
     * @param socketlogin
     * @return
     */
    public String insertSocketlogin(SocketPackage socketPackage, Socketlogin socketlogin);

    /**
     * 添加登出设备记录
     * @param socketPackage
     * @param socketlogin
     */
    public void insertSocketlogout(SocketPackage socketPackage, Socketlogin socketlogin);

    /**
     * 删除在线设备
     * @param socketlogin
     * @return
     */
    public int delZkSocketLoginById(Socketlogin socketlogin);

    /**
     * 查询用户在线设备
     * @param zkUserMachine
     * @return
     */
    List<ZkSocketLogin> selectOnLineZkSocketLogin(ZkUserMachine zkUserMachine);

    /**
     * 查询条数
     * @param zkUserMachine
     * @return
     */
    int selectOnLineZkSocketLoginCount(ZkUserMachine zkUserMachine);

    List<ZkSocketLogin> selectMachineLoginHistiryList(ZkUserMachine zkUserMachine);
}
