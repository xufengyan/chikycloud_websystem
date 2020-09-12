package com.zk.cloudweb.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/4/1 11:00
 * GPS坐标转为像素坐标
 */
public class wgs84_px {
    /**
     * 将WGS84坐标转换为像素坐标
     * @param longitude
     * @param latitude
     * @return
     */
    public static List<List<Integer>> returnXY(List<Double> longitude, List<Double> latitude){
        List<List<Integer>> LDxy=new ArrayList<>();
        List<Integer> Lx = new ArrayList<>();
        List<Integer> Ly = new ArrayList<>();

        Double newLx = null;
        Double newLy = null;
        Double MAXSIZE = 1000d;
        Double maxL = longitude.get(0);
        Double maxB= latitude.get(0);
        Double minL = longitude.get(0);
        Double minB=latitude.get(0);

        for(int i=0,pointsLen=longitude.size();i<pointsLen;i++){
            newLx = longitude.get(i);
            newLy = latitude.get(i);
            maxL = maxL < newLx?newLx :maxL;
            maxB = maxB < newLy?newLy :maxB;
            minL = minL > newLx?newLx :minL;
            minB = minB > newLy?newLy :minB;
        }
        Double diffL = maxL - minL;//经度差
        Double diffB = maxB - minB;//纬度差
        Double width,height,Rate,diff;

        //计算坐标区域height width;

        if(diffL == 0){
            width = MAXSIZE;
            height = MAXSIZE;
            Rate = MAXSIZE/diffB;
        }
        else if (diffB == 0) {
            width = MAXSIZE;
            height = MAXSIZE;
            Rate = MAXSIZE/diffL;
        }else if(diffL >= diffB){
            diff = diffL;
            width = MAXSIZE;
            Rate = MAXSIZE/diffL;//单位坐标的有多少个px值。
            height = diffB/diffL*MAXSIZE;
        }else {
            diff = diffB;
            height = MAXSIZE;
            Rate = MAXSIZE/diffB;//单位坐标的有多少个px值。
            width = diffL/diffB*MAXSIZE;
        }

        // 根据B,L计算像素位置。计算应该有px。
        for(int i=0,pointsLen=longitude.size();i<pointsLen;i++){
            newLx = longitude.get(i);
            newLy = latitude.get(i);
            if(diffL == 0){
                Lx.add((int)(MAXSIZE/2));
                Ly.add((int)(height - (newLy - minB)*Rate));
//                points[k]['Bpx']  = parseInt(height - (value['B'] - minB)*Rate);
            }
            else if (diffB == 0) {
                Lx.add((int)((newLx - minL)* Rate));
                Ly.add((int)(MAXSIZE/2));
            } else {
                Lx.add((int)((newLx - minL)* Rate));
                Ly.add((int)(height - (newLy - minB)*Rate));
            }
        }
        LDxy.add(Lx);
        LDxy.add(Ly);
        return LDxy;
    }
}
