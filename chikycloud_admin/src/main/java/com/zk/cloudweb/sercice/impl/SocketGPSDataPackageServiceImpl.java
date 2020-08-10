package com.zk.cloudweb.sercice.impl;

import com.alibaba.fastjson.JSON;
import com.zk.cloudweb.dao.SocketGPSDataPackageDao;
import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;
import com.zk.cloudweb.sercice.ISocketGPSDataPackageService;
import com.zk.cloudweb.util.RedisKey;
import com.zk.cloudweb.util.RedisUtil;
import com.zk.cloudweb.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/17 15:34
 */
@Service
public class SocketGPSDataPackageServiceImpl implements ISocketGPSDataPackageService {

    @Autowired
    SocketGPSDataPackageDao socketGPSDataPackageDao;


    @Autowired
    RedisUtil redisUtil;
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
        List<SocketGPSDataPackage> socketGPSDataPackages = new ArrayList<>();

        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            //查询redis缓存中是否存在当前测量数据
            String resData =jedis.get(RedisKey.MEASURDATAKEY +":"+ socketMeasurResult.getMachineNum()+":"+socketMeasurResult.getId());
            //判断当前数据是否在缓存中
            if (null!=resData){
                socketGPSDataPackages = JSON.parseArray(resData,SocketGPSDataPackage.class);

            }else {
                socketGPSDataPackages = socketGPSDataPackageDao.selectSocketGPSDataPackageList(socketMeasurResult);
                String dataStr = "";
                if (socketGPSDataPackages.size()>0){
                    dataStr = JSON.toJSONString(socketGPSDataPackages);
                    //将数据放入redis里面
                    jedis.set(RedisKey.MEASURDATAKEY+":"+socketMeasurResult.getMachineNum() +":"+socketMeasurResult.getId(),dataStr);

                }else {
                    jedis.set(RedisKey.MEASURDATAKEY+":"+socketMeasurResult.getMachineNum() +":"+socketMeasurResult.getId(),dataStr);
                    jedis.expire(RedisKey.MEASURDATAKEY+":"+socketMeasurResult.getMachineNum() +":"+socketMeasurResult.getId(),60);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return socketGPSDataPackages;
    }

    /**
     * 根据时间查询当前设备的位置
     */
    @Override
    public List<SocketGPSDataPackage> selectMachineMeasureLactionByDate(SocketGPSDataPackage socketGPSDataPackage) {
        return socketGPSDataPackageDao.selectMachineMeasureLactionByDate(socketGPSDataPackage);
    }
}
