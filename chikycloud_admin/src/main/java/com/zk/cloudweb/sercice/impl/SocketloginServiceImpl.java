package com.zk.cloudweb.sercice.impl;

import com.zk.cloudweb.dao.ZkMachineDao;
import com.zk.cloudweb.dao.ZkSocketLoginDao;
import com.zk.cloudweb.entity.ZkMachine;
import com.zk.cloudweb.entity.ZkSocketLogin;
import com.zk.cloudweb.entity.ZkUserMachine;
import com.zk.cloudweb.entity.socketLink.SocketPackage;
import com.zk.cloudweb.entity.socketLink.Socketlogin;
import com.zk.cloudweb.sercice.ISocketloginService;
import com.zk.cloudweb.sercice.IZkMachineService;
import com.zk.cloudweb.util.Tool;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/9 14:19
 */
@Service
public class SocketloginServiceImpl implements ISocketloginService {

    @Autowired
    IZkMachineService zkMachineService;

    @Autowired
    ZkSocketLoginDao zkSocketLoginDao;

    /**
     * 添加登录信息
     * @param socketPackage
     * @param socketlogin
     * @return
     */
    @Override
    public String insertSocketlogin(SocketPackage socketPackage, Socketlogin socketlogin) {
        ZkMachine zkMachine = new ZkMachine();
        zkMachine.setMNumber(socketlogin.getTerminalID().replaceAll(" ",""));
        ZkMachine resZkMachine = zkMachineService.selectZkMachine(zkMachine);
        if (resZkMachine == null){
            //如果设备不存在则添加
        zkMachine.setMName(socketPackage.getStartNum()+"_"+socketlogin.getTerminalID().replaceAll(" ",""));
        zkMachineService.insertZkMachine(zkMachine);
        }

        //添加登录信息
        ZkSocketLogin zkSocketLogin = new ZkSocketLogin();
        zkSocketLogin.setId(Tool.CreateID());
        zkSocketLogin.setMachineName(socketPackage.getStartNum()+"_"+socketlogin.getTerminalID().replaceAll(" ",""));
        zkSocketLogin.setMachineNum(socketlogin.getTerminalID().replaceAll(" ",""));
        zkSocketLogin.setCreateTime(new Date());
        zkSocketLogin.setLoginIp(socketPackage.getClientIP());
        zkSocketLogin.setLoginType(0);//登录
        //添加在线设备
        zkSocketLoginDao.insertZkSocketLogin(zkSocketLogin);
        //添加登录信息
        zkSocketLoginDao.insertZkSocketLoginHistory(zkSocketLogin);

        return zkSocketLogin.getId();
    }

    /**
     * 添加登出信息
     * @param socketPackage
     * @param socketlogin
     * @return
     */
    @Override
    public void insertSocketlogout(SocketPackage socketPackage, Socketlogin socketlogin) {
        //添加登录信息
        ZkSocketLogin zkSocketLogin = new ZkSocketLogin();
        zkSocketLogin.setId(Tool.CreateID());
        zkSocketLogin.setMachineName(socketPackage.getStartNum()+"_"+socketlogin.getTerminalID().replaceAll(" ",""));
        zkSocketLogin.setMachineNum(socketlogin.getTerminalID().replaceAll(" ",""));
        zkSocketLogin.setCreateTime(new Date());
        zkSocketLogin.setLoginIp(socketPackage.getClientIP());
        zkSocketLogin.setLoginType(1);//登出
        //添加登出信息
        zkSocketLoginDao.insertZkSocketLoginHistory(zkSocketLogin);
    }


    /**
     * 删除设备在线信息
     * @param socketlogin
     * @return
     */
    @Override
    public int delZkSocketLoginById(Socketlogin socketlogin) {
        return zkSocketLoginDao.delZkSocketLoginById(socketlogin.getId());
    }

    /**
     * 查询用户在线设备
     * @param zkUserMachine
     * @return
     */
    @Override
    public List<ZkSocketLogin> selectOnLineZkSocketLogin(ZkUserMachine zkUserMachine) {
        if(null!=zkUserMachine.getLimit()){
            Integer limit = zkUserMachine.getLimit();
            zkUserMachine.setLimit(zkUserMachine.getLimit()*(zkUserMachine.getPage()-1));
            zkUserMachine.setCount(limit*zkUserMachine.getPage());
        }
        return zkSocketLoginDao.selectOnLineZkSocketLogin(zkUserMachine);
    }

    /**
     * 查询用户在线设备条数
     * @param zkUserMachine
     * @return
     */
    @Override
    public int selectOnLineZkSocketLoginCount(ZkUserMachine zkUserMachine) {
        return zkSocketLoginDao.selectOnLineZkSocketLoginCount(zkUserMachine);
    }


}
