package com.zk.cloudweb.sercice;

import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/19 9:20
 */
public interface ISocketMeasureResultService {
    /**
     * 添加测量结果
     * @param socketMeasurResult
     * @return
     */
    public int insertSocketMeasureResult(SocketMeasurResult socketMeasurResult);

    /**
     * 查询测量结果List
     * @param socketMeasurResult
     * @return
     */
    List<SocketMeasurResult> selectSocketMeasurResultList(SocketMeasurResult socketMeasurResult);

    /**
     * 查询测量结果
     * @param socketMeasurResult
     * @return
     */
    SocketMeasurResult selectSocketMeasurResult(SocketMeasurResult socketMeasurResult);
}
