package com.zk.cloudweb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/15 17:59
 */
public class dateFormat {
    //服务器返回的值往往是秒，但是计算的时候要求毫秒，需要*1000L才能得到正确的日期结果。

    public static Date miaoToDate(Integer m){

        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");//制定日期的显示格式

        Date date = new Date(((m)*1000L));

        String time=sdf.format(date);//m为从服务器返回的数据转换后的值（往往是将服务器返回的字符串形式的值，需要转化为int型或者long型）

        //格林时间是以1970-01-01 00:00:00为基准计起的，服务器返回的就是某一时刻到这个基准的秒数（如果是毫秒那就更好了，直接使用不用*1000L）

        return date;
    }

    /**
     * 将时间格式转换为字符串时间格式
     * @param date
     * @return
     */
    public static String Date_DateStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    /**
     * 将时间格式转换为字符串时间格式
     * @param date
     * @return
     */
    public static String Date_yearStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    /**
     * 将字符串时间转换为时间格式
     * @param dateStr
     * @return
     */
    public static Date Date_StrToDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将字符串时间转换为时间格式
     * @param dateStr
     * @return
     */
    public static Date Date_yearStrToDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 将时间转换为秒
     * @param date
     * @return
     */
    public static Long dateToGMT(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        TimeZone tz = TimeZone.getTimeZone("GMT");
        calendar.setTimeZone(tz);
        return calendar.getTimeInMillis()/1000;
    }
}
