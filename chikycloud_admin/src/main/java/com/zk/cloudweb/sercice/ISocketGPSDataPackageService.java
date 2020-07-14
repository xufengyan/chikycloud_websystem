package com.zk.cloudweb.sercice;

import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/17 15:30
 */
public interface ISocketGPSDataPackageService {

    /**
     * 添加GPS测量数据
     *
     * @param socketGPSDataPackages
     * @return
     */
    public int insertSocketGPSDataPackageList(List<SocketGPSDataPackage> socketGPSDataPackages);

    /**
     * 批量修改测量数据
     * @param socketGPSDataPackages
     * @return
     */
    public int updateSocketGPSDataPackageList(List<SocketGPSDataPackage> socketGPSDataPackages);

    /**
     * 查询当前测量结果对应的测量数据
     */
    public List<SocketGPSDataPackage> selectSocketGPSDataPackageList(SocketMeasurResult socketMeasurResult);

    /**
     * 根据时间查询当前设备的位置
     * @param socketGPSDataPackage
     * @return
     */
    SocketGPSDataPackage selectMachineMeasureLactionByDate(SocketGPSDataPackage socketGPSDataPackage);
}