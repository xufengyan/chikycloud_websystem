package com.zk.cloudweb.controller.socket.util;

import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.entity.socketLink.SocketMeasurResult;
import com.zk.cloudweb.entity.socketLink.SocketPackage;
import com.zk.cloudweb.entity.socketLink.Socketlogin;
import com.zk.cloudweb.util.dateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 对设备数据进行解析
 * @author xf
 * @version 1.0
 * @date 2020/6/8 9:42
 */
public class Analysis {

    /**
     * 统一数据解析
     */
    public static SocketPackage socket_data(String hex){
        String [] hexArr = hex.split(" ");
        String start = "";//起始位
        String num = "";//序列号
        String type = "";//数据包类型
        String lenghtHexStr = "";
        Integer lenght = null;//数据包长度
        String data = "";//数据包
        String checkSum = "";
        String stopBit = "";
        for (int i = 0; i < hexArr.length; i++) {
            if (i<4){//起始
                start+=hexArr[i];
            }else if(4<=i&&i<6){//序列号
                num += hexArr[i];
            }else if(6<=i&&i<7){//获取数据包类型
                type = hexArr[i];
            }else if(7<=i&&i<9){//获取数据包长度
                lenghtHexStr+=hexArr[i]+" ";
                if(lenghtHexStr.split(" ").length==2){
                    String [] le = lenghtHexStr.split(" ");
                    lenghtHexStr = le[1]+le[0];
                    lenght = Hex_to_Decimal.hexToInteger(lenghtHexStr);
                }
            }else if(9<=i&&i<(9+lenght)){//获取数据包
                data+=hexArr[i]+" ";
            }else if((9+lenght)<=i&&i<(9+lenght+2)){//获取检验和
                checkSum += hexArr[i]+" ";
            } else if ((9+lenght+2)<=i&&i<hexArr.length) {//停止位
                stopBit+=hexArr[i]+" ";
            }
        }
        //通过CRC16算法计算检验和
        byte [] hexByte = Hex_to_Decimal.hex2Bytes(data.replaceAll(" ",""));
        String newCheckSum = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(CRC16Util.calcCrc16(hexByte)))+" ";
        SocketPackage socketPackage = null;
        if (newCheckSum.equals(checkSum)){
            //将解析的数据放入到对象中返回
            socketPackage = new SocketPackage();
            socketPackage.setStartNum(Hex_to_Decimal.hexToString(start));
            socketPackage.setSerialNum(Hex_to_Decimal.hexToInteger(num));
            socketPackage.setPackageType(type);
            socketPackage.setPackagelenght(lenght);
            socketPackage.setData(data);
//            socketPackage.setCheckSum(checkSum);
            socketPackage.setStopBit(stopBit);
        }
        return socketPackage;
    }

    /**
     * 处理登录包数据
     * @param data
     * @return
     */
    public static Socketlogin socket_login_data(String data){
        Socketlogin socketlogin = new Socketlogin();
        String [] dataArr = data.split(" ");
        String terminalID = "";
        String terminalType = "";
        String loginNumStr = "";
        Integer loginNum = null;
        String keepByte = "";
        for (int i = 0; i < dataArr.length; i++) {
            if (i<8){//获取终端ID
                if (i==0){
                    String [] idstr = dataArr[i].split("");
                    terminalID+= idstr[1]+" ";
                }else {
                    terminalID += dataArr[i]+" ";
                }
            } else if(8<=i&&i<9){//获取终端类型
                terminalType = dataArr[i];
            } else if(9<=i&&i<11){//获取开机后的登录次数
                loginNumStr += dataArr[i]+" ";
                if (loginNumStr.split(" ").length==2){
                    String [] n = loginNumStr.split(" ");
                    loginNumStr = n[1]+n[0];
                    loginNum = Hex_to_Decimal.hexToInteger(loginNumStr);
                }
            } else if(11<=i&&i<12){//保留字节
                keepByte = dataArr[i];
            }
        }
        socketlogin.setTerminalID(terminalID);
        socketlogin.setTerminalType(terminalType);
        socketlogin.setLoginNum(loginNum);
        socketlogin.setKeepByte(keepByte);
        return socketlogin;
    }


    /**
     * 处理GPS测量数据
     * @param data
     */
    public static List<SocketGPSDataPackage> socket_gps_data(String data, String machineNum){
        //5A 4B 43 54 03 00 04 6F 00 05 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 C0 89 0C 5E 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 06 B4 E8 5E 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 09 B4 E8 5E 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 0A B4 E8 5E 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 0B B4 E8 5E 00 00 00 00 48 A1 0D 0A
        System.out.println("测量数据包："+data);
        String [] dataArr = data.split(" ");

        List<SocketGPSDataPackage> socketGPSDataPackages = new ArrayList<>();
        //获取GPS数据包的个数
        Integer dataNum = Integer.parseInt(dataArr[1]+dataArr[0]);
        for (int i = 0; i < dataNum; i++) {
            //GPS数据包
            //定位状态
            String GPSType = dataArr[(i*21+2)] ;
            //纬度
            float latitude = Hex_to_Decimal.hexToFloat(dataArr[(i*21+2)+4]+dataArr[(i*21+2)+3]+dataArr[(i*21+2)+2]+dataArr[(i*21+2)+1]);
            //经度
            float longitude = Hex_to_Decimal.hexToFloat(dataArr[(i*21+2)+8]+dataArr[(i*21+2)+7]+dataArr[(i*21+2)+6]+dataArr[(i*21+2)+5]);
            //速率
            float rate = Hex_to_Decimal.hexToFloat(dataArr[(i*21+2)+12]+dataArr[(i*21+2)+11]+dataArr[(i*21+2)+10]+dataArr[(i*21+2)+9]);
            //航向
            float course = Hex_to_Decimal.hexToFloat(dataArr[(i*21+2)+16]+dataArr[(i*21+2)+15]+dataArr[(i*21+2)+14]+dataArr[(i*21+2)+13]);
            //时间戳=秒数

//            System.out.println("测量时间戳："+dataArr[(i*21+2)+20]+dataArr[(i*21+2)+19]+dataArr[(i*21+2)+18]+dataArr[(i*21+2)+17]);
            Integer dataM = null;
            try {
                 dataM = Hex_to_Decimal.hexToInteger(dataArr[(i*21+2)+20]+dataArr[(i*21+2)+19]+dataArr[(i*21+2)+18]+dataArr[(i*21+2)+17]);
            }catch (Exception e){
                System.out.println("时间错误："+e);
            }
            //测量时间
            if ("01".equals(GPSType)&&dataM!=null){
                Date measDate = dateFormat.miaoToDate(dataM);
                SocketGPSDataPackage socketGPSDataPackage = new SocketGPSDataPackage();
                socketGPSDataPackage.setDataNum(dataNum);
                socketGPSDataPackage.setGPSType(GPSType);
                socketGPSDataPackage.setLatitude(latitude);
                socketGPSDataPackage.setLongitude(longitude);
                socketGPSDataPackage.setRate(rate);
                socketGPSDataPackage.setCourse(course);
                socketGPSDataPackage.setMeasureTime(measDate);
                socketGPSDataPackage.setMachineNum(machineNum);
                socketGPSDataPackages.add(socketGPSDataPackage);

            }
        }
        return socketGPSDataPackages;
    }


    /**
     * 解析测量结果包
     * @param data
     * @return
     */
    public static SocketMeasurResult socket_measure_result(String data, String machineNum){
        System.out.println(data);
        String [] dataArr = data.split(" ");
        //开始测量时间秒数
        Integer startM = Hex_to_Decimal.hexToInteger(dataArr[3]+dataArr[2]+dataArr[1]+dataArr[0]);
        //结束测量时间秒数
        Integer endM = Hex_to_Decimal.hexToInteger(dataArr[7]+dataArr[6]+dataArr[5]+dataArr[4]);

        //剩余可用面积
        float surplusArea = Hex_to_Decimal.hexToFloat(dataArr[11]+dataArr[10]+dataArr[9]+dataArr[8]);

        //测量面积
        float measArea = Hex_to_Decimal.hexToFloat(dataArr[15]+dataArr[14]+dataArr[13]+dataArr[12]);

        //测量路程
        float measureJourney = Hex_to_Decimal.hexToFloat(dataArr[19]+dataArr[18]+dataArr[17]+dataArr[16]);

        //累计作业面积
        float cumulativeArea = Hex_to_Decimal.hexToFloat(dataArr[23]+dataArr[22]+dataArr[21]+dataArr[20]);

        //累计作业时间
        float cumulativeTime = Hex_to_Decimal.hexToFloat(dataArr[27]+dataArr[26]+dataArr[25]+dataArr[24]);

        //深松深度
        float totalWeight = Hex_to_Decimal.hexToFloat(dataArr[31]+dataArr[30]+dataArr[29]+dataArr[28]);

        SocketMeasurResult socketMeasurResult = new SocketMeasurResult();
        socketMeasurResult.setStartTime(dateFormat.miaoToDate(startM));
        socketMeasurResult.setEndTime(dateFormat.miaoToDate(endM));
        socketMeasurResult.setSurplusArea(surplusArea);
        socketMeasurResult.setMeasureArea(measArea);
        socketMeasurResult.setMeasureJourney(measureJourney);
        socketMeasurResult.setCumulativeArea(cumulativeArea);
        socketMeasurResult.setCumulativeTime(cumulativeTime);
        socketMeasurResult.setTotalWeight(totalWeight);
        socketMeasurResult.setMachineNum(machineNum);
        return socketMeasurResult;
    }









}
