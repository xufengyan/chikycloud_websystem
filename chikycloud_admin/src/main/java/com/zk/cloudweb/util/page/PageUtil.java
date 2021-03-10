package com.zk.cloudweb.util.page;

import com.github.pagehelper.PageInfo;
import com.zk.cloudweb.entity.ZkImageDw;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 分页返回工具
 * @author xf
 * @version 1.0
 * @date 2021/3/10 15:45
 */
public class PageUtil {

    public static Result setpage(List<?> list){
        PageInfo page = new PageInfo(list);
        Result result = new Result(ResultEnum.OK,list,true);
        result.setCount((int)page.getTotal());
        return result;
    }



}
