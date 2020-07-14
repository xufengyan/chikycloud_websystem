package com.zk.cloudweb.util;

/**
 * @author xf
 * @version 1.0
 * @date 2020/4/1 11:00
 * 对解析.nmea的经纬度进行BD09转换
 */
public class libgeodesy_ct {

    private  double bd09_lat;
    private  double bd09_lon;

    public double getBd09_lat() {
        return bd09_lat;
    }

    public double getBd09_lon() {
        return bd09_lon;
    }

    Boolean geodesy_is_point_out_of_china(double lat, double lon)
    {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }



    double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 *
                Math.sqrt(Math.abs(x));//Math.sqrt求非负数平方根，Math.abs求绝对值
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }


    double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y +
                0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }



//    public void libgeodesy_wgs84_to_mars(double wgs84_lat,double wgs84_lon){
//
//        double tmlat, tmplon;
//        double a = 6378245.0;
//        double ee = 0.00669342162296594323;
//
//        if (geodesy_is_point_out_of_china(wgs84_lat, wgs84_lon))
//        {
//        tmlat = wgs84_lat;
//        tmplon = wgs84_lon;
//            return ;
//        }
//        double dLat = transformLat(wgs84_lon - 105.0, wgs84_lat - 35.0);
//        double dLon = transformLon(wgs84_lon - 105.0, wgs84_lat - 35.0);
//        double radLat = wgs84_lat / 180.0 * Math.PI;
//        double magic = Math.sin(radLat);
//        magic = 1 - ee * magic * magic;
//        double sqrtMagic = Math.sqrt(magic);
//        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
//        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
//        tmlat = wgs84_lat + dLat;
//        tmplon = wgs84_lon + dLon;
//
//        libgeodesy_mars_to_bd09(tmlat,tmplon);
//    }


    /**
     * 对解析.nmea的经纬度进行BD09转换
     * @param wgs84_lat 纬度
     * @param wgs84_lon 经度
     * @return
     */
    public void libgeodesy_wgs84_to_mars(double wgs84_lat, double wgs84_lon){

        double tmlat, tmplon;
        double a = 6378245.0;
        double ee = 0.00669342162296594323;

        if (geodesy_is_point_out_of_china(wgs84_lat, wgs84_lon))
        {
            tmlat = wgs84_lat;
            tmplon = wgs84_lon;
            return ;
        }
        double dLat = transformLat(wgs84_lon - 105.0, wgs84_lat - 35.0);
        double dLon = transformLon(wgs84_lon - 105.0, wgs84_lat - 35.0);
        double radLat = wgs84_lat / 180.0 * Math.PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
        tmlat = wgs84_lat + dLat;
        tmplon = wgs84_lon + dLon;

        libgeodesy_mars_to_bd09(tmlat,tmplon);

    }




     public void libgeodesy_mars_to_bd09(double mars_lat, double mars_lon) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = mars_lon, y = mars_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        this.bd09_lon = z * Math.cos(theta) + 0.0065;
        this.bd09_lat = z * Math.sin(theta) + 0.006;
    }



}
