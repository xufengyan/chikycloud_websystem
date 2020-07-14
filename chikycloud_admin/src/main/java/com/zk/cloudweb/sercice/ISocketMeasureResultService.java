package com.zk.cloudweb.sercice;

import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/19 9:20
 */
public interface ISocketMeasureResultService {
    public int insertSocketMeasureResult(SocketMeasurResult socketMeasurResult);
    
    List<SocketMeasurResult> selectSocketMeasurResultList(SocketMeasurResult socketMeasurResult);
}
