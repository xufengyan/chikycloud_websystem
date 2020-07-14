package com.zk.cloudweb.dao;

import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/19 16:23
 */
public interface SocketMeasureResultDao {
    int insertSocketMeasureResult(SocketMeasurResult socketMeasurResult);

    List<SocketMeasurResult> selectSocketMeasurResultList(SocketMeasurResult socketMeasurResult);

}
