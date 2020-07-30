package com.zk.cloudweb.dao;

import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/19 16:23
 */
public interface SocketMeasureResultDao {

    /**
     * 添加测量结果
     * @param socketMeasurResult
     * @return
     */
    int insertSocketMeasureResult(SocketMeasurResult socketMeasurResult);

    /**
     * 查询测量结果List
     */
    List<SocketMeasurResult> selectSocketMeasurResultList(SocketMeasurResult socketMeasurResult);

    /**
     * 查询测量结果
     * @param socketMeasurResult
     * @return
     */
    SocketMeasurResult selectSocketMeasurResult(SocketMeasurResult socketMeasurResult);
}
