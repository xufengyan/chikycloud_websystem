package com.zk.cloudweb.sercice.impl;

import java.util.Date;
import java.util.List;

import com.zk.cloudweb.dao.ZkImageDwDao;
import com.zk.cloudweb.entity.ZkImageDw;
import com.zk.cloudweb.sercice.IZkImageDwService;
import com.zk.cloudweb.util.Convert;
import com.zk.cloudweb.util.getShiroUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2021-03-10
 */
@Service
public class ZkImageDwServiceImpl implements IZkImageDwService
{
    @Autowired
    private ZkImageDwDao zkImageDwMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param zId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public ZkImageDw selectZkImageDwById(Long zId)
    {
        return zkImageDwMapper.selectZkImageDwById(zId);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param zkImageDw 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ZkImageDw> selectZkImageDwList(ZkImageDw zkImageDw)
    {
        return zkImageDwMapper.selectZkImageDwList(zkImageDw);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param zkImageDw 【请填写功能名称】
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertZkImageDw(ZkImageDw zkImageDw)
    {
        zkImageDw.setuName(getShiroUser.getUser().getUName());
//        zkImageDw.setCreateTime(new Date());
        return zkImageDwMapper.insertZkImageDw(zkImageDw);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param zkImageDw 【请填写功能名称】
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateZkImageDw(ZkImageDw zkImageDw)
    {
        return zkImageDwMapper.updateZkImageDw(zkImageDw);
    }

    /**
     * 删除【请填写功能名称】对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteZkImageDwByIds(String ids)
    {
        return zkImageDwMapper.deleteZkImageDwByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param zId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteZkImageDwById(Long zId)
    {
        return zkImageDwMapper.deleteZkImageDwById(zId);
    }
}
