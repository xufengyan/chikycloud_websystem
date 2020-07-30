package com.zk.cloudweb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/15 17:59
 */
public class dateFormat {
    //服务器返回的值往往是秒，但是计算的时候要求毫秒，需要*1000L才能得到正确的日期结果。

    public static Date miaoToDate(Integer m){

        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");//制定日期的显示格式

        Date date = new Date(((m-28800)*1000L));

        String time=sdf.format(date);//m为从服务器返回的数据转换后的值（往往是将服务器返回的字符串形式的值，需要转化为int型或者long型）

        //格林时间是以1970-01-01 00:00:00为基准计起的，服务器返回的就是某一时刻到这个基准的秒数（如果是毫秒那就更好了，直接使用不用*1000L）

        return date;
    }


}
