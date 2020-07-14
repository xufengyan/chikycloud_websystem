package com.zk.cloudweb.dao;

import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;

import java.util.List;

/**
 * 测量数据dao层
 * @author xf
 * @version 1.0
 * @date 2020/6/17 15:39
 */
public interface SocketGPSDataPackageDao {

    int insertSocketGPSDataPackageList(List<SocketGPSDataPackage> socketGPSDataPackages);

    int updateSocketGPSDataPackageList(List<SocketGPSDataPackage> socketGPSDataPackages);

    List<SocketGPSDataPackage> selectSocketGPSDataPackageList(SocketMeasurResult socketMeasurResult);

    SocketGPSDataPackage selectMachineMeasureLactionByDate(SocketGPSDataPackage socketGPSDataPackage);
}
