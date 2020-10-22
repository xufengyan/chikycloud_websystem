package com.zk.cloudweb.controller.socket.client;

import com.zk.cloudweb.controller.socket.util.CRC16Util;
import com.zk.cloudweb.controller.socket.util.Hex_to_Decimal;
import com.zk.cloudweb.entity.socketDate.measured;
import com.zk.cloudweb.entity.socketDate.measuredGPS;
import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.util.dateFormat;
import com.zk.cloudweb.util.webSocket.Util.WebSocketConcurrent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户端发送数据
 * @author xf
 * @version 1.0
 * @date 2020/9/14 16:54
 */
public class NettyClientSend {

    private static String machineNum = "08 61 11 11 11 11 11 11";
    private static String start = "5A 4B 43 54";//起始位
    private static String num = "00 00";//序列号

    private static List<SocketGPSDataPackage> socketGPSDataPackageList = new ArrayList<>();

    public static void unifySend(String hexStr, Channel channel,String head){
        WebSocketConcurrent.AppointSending(channel,dateFormat.Date_DateStr(new Date())+head+"：</br>"+hexStr.toString(),true);
        hexStr = hexStr.replaceAll("</br>","").replaceAll(" ","");
        byte [] setBytes = Hex_to_Decimal.hex2Bytes(hexStr);
        ByteBuf bufff = Unpooled.buffer();
        bufff.writeBytes(setBytes);
        channel.write(bufff);
        channel.flush();
    }


    //模拟发送登录包
    public static String  sendLoginData(){
        String dataType = "00";//数据包类型
        String length = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(12));
        String loginNum = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(1));
        String idType = "00";
        String data = machineNum+" "+idType+" "+loginNum+" 00";
        String checkSum = null;
        byte [] hexByte = Hex_to_Decimal.hex2Bytes(data.replaceAll(" ",""));
        checkSum = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(CRC16Util.calcCrc16(hexByte)));
        String resultStr = start+" </br>"+num+" </br>"+dataType+" </br>"+length+" </br>"+data+" </br>"+checkSum+" </br>"+"0D 0A";
        return resultStr;
    }

    //模拟发送心跳包
    public static String sendHeartbeatData(){
        String type = "01";//数据包类型
        String length = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(4));
        String loginNum = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(1));
        String data = Hex_to_Decimal.intOneHex16(1)+" 00 00 00";
        String checkSum = null;
        byte [] hexByte = Hex_to_Decimal.hex2Bytes(data.replaceAll(" ",""));
        checkSum = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(CRC16Util.calcCrc16(hexByte)));
        String resultStr = start+" </br>"+num+" </br>"+type+" </br>"+length+" </br>"+data+" </br>"+checkSum+" </br>"+"0D 0A";
        return resultStr;
    }


    //模拟发送gps测量数据包
    public static List<String> sendMeasureData(){

        for (String s : measureDataStr.split("\n")) {
            String [] lonlat = s.split(",");
            SocketGPSDataPackage socketGPSDataPackage= new SocketGPSDataPackage();
            socketGPSDataPackage.setLatitude(Float.parseFloat(lonlat[2]));
            socketGPSDataPackage.setLongitude(Float.parseFloat(lonlat[3]));
            socketGPSDataPackage.setCourse(0.005000f);
            socketGPSDataPackage.setRate(0.000001f);
            socketGPSDataPackage.setMeasureTime(new Date());
            socketGPSDataPackageList.add(socketGPSDataPackage);
        }
        int size = socketGPSDataPackageList.size()/5;//保证每次发五个测量数据包---------太多会导致粘包
        int remSize = socketGPSDataPackageList.size()%5;
        List<String> resultStrList = new ArrayList<>();
        for (int i = 0; i < socketGPSDataPackageList.size(); i+=5) {

            if (i==5*size){

                String dataType = "04";//数据包类型
                String length = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(6+5*21));
                String data = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(5));
                for (int j = i;j<(i+remSize);j++){
                    String lon = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(socketGPSDataPackageList.get(j).getLongitude()));
                    String lat = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(socketGPSDataPackageList.get(j).getLatitude()));
                    String course = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(socketGPSDataPackageList.get(j).getCourse()));
                    String rate = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(socketGPSDataPackageList.get(j).getRate()));
                    String measureTime = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.intFourHex16(Math.toIntExact(dateFormat.dateToGMT(socketGPSDataPackageList.get(j).getMeasureTime()))));
                    data+=" 01 "+lat+" "+lon+" "+course+" "+rate+" "+measureTime;
                }
//                for (SocketGPSDataPackage socketGPSDataPackage : socketGPSDataPackageList) {
//
//                }
                data = data+" 00 00 00 00";
                String checkSum = null;
                byte [] hexByte = Hex_to_Decimal.hex2Bytes(data.replaceAll(" ",""));
                checkSum = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(CRC16Util.calcCrc16(hexByte)));
                String resultStr = start+" </br>"+num+" </br>"+dataType+" </br>"+length+" </br>"+data+" </br>"+checkSum+" </br>"+"0D 0A";
                resultStrList.add(resultStr);

            }else if(i<5*size){
                String dataType = "04";//数据包类型
                String length = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(6+5*21));
                String data = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(5));
                for (int j = i;j<(i+5);j++){
                    String lon = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(socketGPSDataPackageList.get(j).getLongitude()));
                    String lat = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(socketGPSDataPackageList.get(j).getLatitude()));
                    String course = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(socketGPSDataPackageList.get(j).getCourse()));
                    String rate = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(socketGPSDataPackageList.get(j).getRate()));
                    String measureTime = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.intFourHex16(Math.toIntExact(dateFormat.dateToGMT(socketGPSDataPackageList.get(j).getMeasureTime()))));
                    data+=" 01 "+lat+" "+lon+" "+course+" "+rate+" "+measureTime;
                }

                data = data+" 00 00 00 00";
                String checkSum = null;
                byte [] hexByte = Hex_to_Decimal.hex2Bytes(data.replaceAll(" ",""));
                checkSum = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(CRC16Util.calcCrc16(hexByte)));
                String resultStr = start+" </br>"+num+" </br>"+dataType+" </br>"+length+" </br>"+data+" </br>"+checkSum+" </br>"+"0D 0A";
                resultStrList.add(resultStr);

            }
        }

        return resultStrList;
    }


    /**
     * 发送测量结果包
     * @param startTime
     * @param endTime
     * @return
     */
    public static String sendMeasureResultData(Date startTime, Date endTime){
        String dataType = "02";//数据包类型
        String length = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(32));
        String startTimeStr = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.intFourHex16(Math.toIntExact(dateFormat.dateToGMT(startTime))));
        String endTimeStr = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.intFourHex16(Math.toIntExact(dateFormat.dateToGMT(endTime))));
        String surplusArea = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(100.1f));
        String measureArea = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(35.26f));
        String measureJourney = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(150.6f));
        String cumulativeArea = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(1584f));
        String cumulativeTime = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(510));
        String totalWeight = Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(56.2f));
        String data = startTimeStr+" "+endTimeStr+" "+surplusArea+" "+measureArea+" "+measureJourney+" "+cumulativeArea+" "+cumulativeTime+" "+totalWeight;
        String checkSum = null;
        byte [] hexByte = Hex_to_Decimal.hex2Bytes(data.replaceAll(" ",""));
        checkSum = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(CRC16Util.calcCrc16(hexByte)));
        String resultStr = start+" </br>"+num+" </br>"+dataType+" </br>"+length+" </br>"+data+" </br>"+checkSum+" </br>"+"0D 0A";
        return resultStr;
    }

    public static void main(String[] args) {
        String resStr = sendMeasureResultData(new Date(),new Date());
        System.out.println(resStr.replaceAll("</br>",""));

    }
    private static String measureDataStr = "2016-12-28,15:20:28,21.139125,110.309718\n" +
            "2016-12-28,15:20:29,21.139127,110.309714\n" +
            "2016-12-28,15:20:30,21.139131,110.309707\n" +
            "2016-12-28,15:20:31,21.139137,110.309701\n" +
            "2016-12-28,15:20:32,21.139144,110.309696\n" +
            "2016-12-28,15:20:33,21.139149,110.309688\n" +
            "2016-12-28,15:20:34,21.139154,110.309681\n" +
            "2016-12-28,15:20:35,21.139159,110.309675\n" +
            "2016-12-28,15:20:36,21.139165,110.309670\n" +
            "2016-12-28,15:20:37,21.139171,110.309665\n" +
            "2016-12-28,15:20:38,21.139177,110.309661\n" +
            "2016-12-28,15:20:39,21.139180,110.309659\n" +
            "2016-12-28,15:20:40,21.139178,110.309658\n" +
            "2016-12-28,15:20:41,21.139181,110.309652\n" +
            "2016-12-28,15:20:42,21.139186,110.309647\n" +
            "2016-12-28,15:20:43,21.139190,110.309644\n" +
            "2016-12-28,15:20:49,21.139192,110.309642\n" +
            "2016-12-28,15:20:50,21.139199,110.309644\n" +
            "2016-12-28,15:20:51,21.139206,110.309644\n" +
            "2016-12-28,15:20:52,21.139210,110.309642\n" +
            "2016-12-28,15:20:56,21.139217,110.309638\n" +
            "2016-12-28,15:20:58,21.139222,110.309635\n" +
            "2016-12-28,15:20:59,21.139225,110.309631\n" +
            "2016-12-28,15:21:00,21.139229,110.309626\n" +
            "2016-12-28,15:21:01,21.139234,110.309625\n" +
            "2016-12-28,15:21:02,21.139239,110.309623\n" +
            "2016-12-28,15:21:03,21.139243,110.309622\n" +
            "2016-12-28,15:21:04,21.139246,110.309618\n" +
            "2016-12-28,15:21:06,21.139249,110.309609\n" +
            "2016-12-28,15:21:07,21.139254,110.309608\n" +
            "2016-12-28,15:21:08,21.139259,110.309613\n" +
            "2016-12-28,15:21:10,21.139264,110.309617\n" +
            "2016-12-28,15:21:11,21.139267,110.309622\n" +
            "2016-12-28,15:21:12,21.139270,110.309625\n" +
            "2016-12-28,15:21:13,21.139274,110.309629\n" +
            "2016-12-28,15:21:14,21.139275,110.309638\n" +
            "2016-12-28,15:21:15,21.139276,110.309643\n" +
            "2016-12-28,15:21:16,21.139274,110.309645\n" +
            "2016-12-28,15:21:18,21.139272,110.309651\n" +
            "2016-12-28,15:21:19,21.139271,110.309654\n" +
            "2016-12-28,15:21:26,21.139276,110.309654\n" +
            "2016-12-28,15:21:27,21.139278,110.309659\n" +
            "2016-12-28,15:21:29,21.139281,110.309662\n" +
            "2016-12-28,15:21:51,21.139284,110.309671\n" +
            "2016-12-28,15:21:55,21.139285,110.309673\n" +
            "2016-12-28,15:21:57,21.139282,110.309680\n" +
            "2016-12-28,15:22:01,21.139280,110.309683\n" +
            "2016-12-28,15:22:02,21.139280,110.309687\n" +
            "2016-12-28,15:22:04,21.139275,110.309691\n" +
            "2016-12-28,15:22:05,21.139274,110.309696\n" +
            "2016-12-28,15:22:06,21.139275,110.309699\n" +
            "2016-12-28,15:22:07,21.139275,110.309703\n" +
            "2016-12-28,15:22:09,21.139274,110.309708\n" +
            "2016-12-28,15:22:10,21.139276,110.309714\n" +
            "2016-12-28,15:22:12,21.139281,110.309721\n" +
            "2016-12-28,15:22:13,21.139283,110.309724\n" +
            "2016-12-28,15:22:14,21.139285,110.309729\n" +
            "2016-12-28,15:22:15,21.139286,110.309737\n" +
            "2016-12-28,15:22:16,21.139289,110.309744\n" +
            "2016-12-28,15:22:17,21.139291,110.309752\n" +
            "2016-12-28,15:22:18,21.139289,110.309761\n" +
            "2016-12-28,15:22:19,21.139289,110.309769\n" +
            "2016-12-28,15:22:20,21.139288,110.309777\n" +
            "2016-12-28,15:22:21,21.139286,110.309785\n" +
            "2016-12-28,15:22:22,21.139286,110.309791\n" +
            "2016-12-28,15:22:23,21.139286,110.309796\n" +
            "2016-12-28,15:22:24,21.139287,110.309801\n" +
            "2016-12-28,15:22:26,21.139291,110.309804\n" +
            "2016-12-28,15:22:27,21.139293,110.309809\n" +
            "2016-12-28,15:22:28,21.139297,110.309811\n" +
            "2016-12-28,15:22:29,21.139304,110.309817\n" +
            "2016-12-28,15:22:30,21.139308,110.309822\n" +
            "2016-12-28,15:22:31,21.139313,110.309830\n" +
            "2016-12-28,15:22:32,21.139319,110.309837\n" +
            "2016-12-28,15:22:33,21.139323,110.309843\n" +
            "2016-12-28,15:22:34,21.139327,110.309850\n" +
            "2016-12-28,15:22:35,21.139333,110.309859\n" +
            "2016-12-28,15:22:36,21.139338,110.309865\n" +
            "2016-12-28,15:22:37,21.139338,110.309874\n" +
            "2016-12-28,15:22:38,21.139330,110.309873\n" +
            "2016-12-28,15:22:39,21.139322,110.309868\n" +
            "2016-12-28,15:22:41,21.139315,110.309863\n" +
            "2016-12-28,15:22:42,21.139305,110.309860\n" +
            "2016-12-28,15:22:43,21.139295,110.309856\n" +
            "2016-12-28,15:22:44,21.139286,110.309851\n" +
            "2016-12-28,15:22:45,21.139280,110.309848\n" +
            "2016-12-28,15:22:46,21.139273,110.309844\n" +
            "2016-12-28,15:22:47,21.139264,110.309838\n" +
            "2016-12-28,15:22:48,21.139256,110.309832\n" +
            "2016-12-28,15:22:49,21.139249,110.309826\n" +
            "2016-12-28,15:22:50,21.139242,110.309822\n" +
            "2016-12-28,15:22:51,21.139233,110.309815\n" +
            "2016-12-28,15:22:52,21.139228,110.309808\n" +
            "2016-12-28,15:22:53,21.139222,110.309802\n" +
            "2016-12-28,15:22:54,21.139214,110.309797\n" +
            "2016-12-28,15:22:55,21.139207,110.309790\n" +
            "2016-12-28,15:22:56,21.139201,110.309786\n" +
            "2016-12-28,15:22:57,21.139194,110.309779\n" +
            "2016-12-28,15:22:58,21.139189,110.309772\n" +
            "2016-12-28,15:22:59,21.139182,110.309767\n" +
            "2016-12-28,15:23:00,21.139173,110.309760\n" +
            "2016-12-28,15:23:01,21.139166,110.309752\n" +
            "2016-12-28,15:23:02,21.139160,110.309744\n" +
            "2016-12-28,15:23:03,21.139151,110.309739\n" +
            "2016-12-28,15:23:04,21.139141,110.309734\n" +
            "2016-12-28,15:23:05,21.139132,110.309729\n" +
            "2016-12-28,15:23:06,21.139124,110.309726\n" +
            "2016-12-28,15:23:07,21.139117,110.309723\n" +
            "2016-12-28,15:23:08,21.139112,110.309722";
}
