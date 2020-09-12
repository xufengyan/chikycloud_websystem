package com.zk.cloudweb.dao;

import com.zk.cloudweb.entity.ZkSocketLogin;
import com.zk.cloudweb.entity.ZkUserMachine;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/10 10:06
 */
@Component
public interface ZkSocketLoginDao {

    ZkSocketLogin selectZkMachine(ZkSocketLogin zkSocketLogin);

    int insertZkSocketLogin(ZkSocketLogin zkSocketLogin);

    int insertZkSocketLoginHistory(ZkSocketLogin zkSocketLogin);

    int delZkSocketLoginById(String id);

    List<ZkSocketLogin> selectOnLineZkSocketLogin(ZkUserMachine zkUserMachine);

    int selectOnLineZkSocketLoginCount(ZkUserMachine zkUserMachine);

    List<ZkSocketLogin> selectMachineLoginHistiryList(ZkUserMachine zkUserMachine);
}
