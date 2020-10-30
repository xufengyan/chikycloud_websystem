package com.zk.cloudweb.sercice.impl;

import com.zk.cloudweb.dao.SocketGPSDataPackageDao;
import com.zk.cloudweb.dao.SocketMeasureResultDao;
import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;
import com.zk.cloudweb.sercice.ISocketMeasureResultService;
import com.zk.cloudweb.util.Tool;
import com.zk.cloudweb.util.dateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/19 9:20
 */
@Service
public class SocketMeasureResultServiceImpl implements ISocketMeasureResultService {

    @Autowired
    SocketMeasureResultDao socketMeasureResultDao;
    @Autowired
    SocketGPSDataPackageDao socketGPSDataPackageDao;

    /**
     * 添加测量结果
     * @param socketMeasurResult
     * @return
     */
    @Override
    public int insertSocketMeasureResult(SocketMeasurResult socketMeasurResult){

        Integer num = 0;

        /**
         * 查询当前测量结果范围内的GPS数据
         */
        List<SocketGPSDataPackage> socketGPSDataPackages = socketGPSDataPackageDao.selectSocketGPSDataPackageList(socketMeasurResult);

        if (socketGPSDataPackages.size()>0){
            socketMeasurResult.setId(Tool.CreateID());
            for (SocketGPSDataPackage socketGPSDataPackage : socketGPSDataPackages) {
                socketGPSDataPackage.setMeasureResId(socketMeasurResult.getId());
            }
            //将当前对应测量结果的数据进行修改
            System.out.println("测量数据："+socketGPSDataPackages.toString());
            socketMeasureResultDao.insertSocketMeasureResult(socketMeasurResult);

            socketGPSDataPackageDao.updateSocketGPSDataPackageList(socketGPSDataPackages);

            num = 1;
        }
        return num;
    }

    @Override
    public List<SocketMeasurResult> selectSocketMeasurResultList(SocketMeasurResult socketMeasurResult) {
        if(socketMeasurResult.getEndTime()!=null){
            String endTimeStr = dateFormat.Date_yearStr(socketMeasurResult.getEndTime());
            socketMeasurResult.setEndTime(dateFormat.Date_yearStrToDate(endTimeStr+" 23:59:59"));
        }
        return socketMeasureResultDao.selectSocketMeasurResultList(socketMeasurResult);
    }

    @Override
    public SocketMeasurResult selectSocketMeasurResult(SocketMeasurResult socketMeasurResult) {
        return socketMeasureResultDao.selectSocketMeasurResult(socketMeasurResult);
    }

    @Override
    public int deleteSocketMeasurById(String id) {
        return socketMeasureResultDao.deleteSocketMeasurById(id);
    }

    @Override
    public int updateSocketMeasurResult(SocketMeasurResult socketMeasurResult) {
        return socketMeasureResultDao.updateSocketMeasurResult(socketMeasurResult);
    }

}
