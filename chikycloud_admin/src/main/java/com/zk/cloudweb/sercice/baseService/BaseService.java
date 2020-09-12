package com.zk.cloudweb.sercice.baseService;

import com.zk.cloudweb.entity.ZkFile;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/9/2 10:08
 */
public interface BaseService<T> {

    List<T> selectListByEntity(T t);

    T selectEntityByEntity(T t);

    int updateByEntity(T t);

    int insertByEntity(T t);

    int deleteById(String id);
}
