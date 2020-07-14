package com.zk.cloudweb.sercice.impl;

import com.zk.cloudweb.dao.SocketGPSDataPackageDao;
import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;
import com.zk.cloudweb.sercice.ISocketGPSDataPackageService;
import com.zk.cloudweb.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/17 15:34
 */
@Service
public class SocketGPSDataPackageServiceImpl implements ISocketGPSDataPackageService {

    @Autowired
    SocketGPSDataPackageDao socketGPSDataPackageDao;

    /**
     * 批量添加测量数据
     * @param socketGPSDataPackages
     * @return
     */
    @Override
    public int insertSocketGPSDataPackageList(List<SocketGPSDataPackage> socketGPSDataPackages) {
        for (SocketGPSDataPackage socketGPSDataPackage : socketGPSDataPackages) {
            if (!"00".equals(socketGPSDataPackage.getGPSType())){
                socketGPSDataPackage.setId(Tool.CreateID());
            }
        }
        return socketGPSDataPackageDao.insertSocketGPSDataPackageList(socketGPSDataPackages);
    }

    /**
     * 修改GPS测量数据
     * @param socketGPSDataPackages
     * @return
     */
    @Override
    public int updateSocketGPSDataPackageList(List<SocketGPSDataPackage> socketGPSDataPackages) {
        return socketGPSDataPackageDao.updateSocketGPSDataPackageList(socketGPSDataPackages);
    }

    /**
     * 查询GPS测量数据
     * @param socketMeasurResult
     * @return
     */
    @Override
    public List<SocketGPSDataPackage> selectSocketGPSDataPackageList(SocketMeasurResult socketMeasurResult) {
        return socketGPSDataPackageDao.selectSocketGPSDataPackageList(socketMeasurResult);
    }

    /**
     * 根据时间查询当前设备的位置
     */
    @Override
    public SocketGPSDataPackage selectMachineMeasureLactionByDate(SocketGPSDataPackage socketGPSDataPackage) {
        return socketGPSDataPackageDao.selectMachineMeasureLactionByDate(socketGPSDataPackage);
    }
}
