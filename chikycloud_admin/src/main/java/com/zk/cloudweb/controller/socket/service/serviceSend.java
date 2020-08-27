package com.zk.cloudweb.controller.socket.service;

import com.zk.cloudweb.controller.socket.ServerHandler;
import com.zk.cloudweb.controller.socket.util.CRC16Util;
import com.zk.cloudweb.controller.socket.util.Hex_to_Decimal;
import com.zk.cloudweb.entity.ZkMachineSet;
import com.zk.cloudweb.entity.socketLink.SocketPackage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xf
 * @version 1.0
 * @date 2020/8/20 17:55
 */
public class serviceSend {

    private static Logger logger = LoggerFactory.getLogger(com.zk.cloudweb.controller.socket.ServerHandler.class);

    /**
     * 将要返回给设备的数据进行转换
     * @param socketPackage
     * @return
     */
    public static byte [] StringToByte(SocketPackage socketPackage){
        String start = "";//起始位
        String num = "";//序列号
        String type = "";//数据包类型
        String lenghtHexStr = "";
        Integer lenght = null;//数据包长度
        String data = "";//数据包
        String checkSum = "";
        String stopBit = "";
        start = Hex_to_Decimal.strToHexStr(socketPackage.getStartNum());
        num = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(socketPackage.getSerialNum()));
        type = socketPackage.getPackageType();
//        if (lenghtHexStr.length()==2){
//            lenghtHexStr = "00 "+lenghtHexStr;
//        }else if (lenghtHexStr.length()<4&&lenghtHexStr.length()>2){
//            String [] lArr = lenghtHexStr.split("");
//            lenghtHexStr = "0"+lArr[0]+" "+lArr[1]+lArr[2];
//        }else if (lenghtHexStr.length()==4){
//            String [] lArr = lenghtHexStr.split("");
//            lenghtHexStr = lArr[0]+lArr[1]+ " " +lArr[2]+lArr[3];
//        }

        //判断回复数据包的格式
        if ("00".equals(socketPackage.getPackageType())){
            for (int i = 0;i<8;i++){
                data += "00"+" ";
            }
            lenghtHexStr =  Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(8));
        }else{
            for (int i = 0;i<4;i++){
                data += "00"+" ";
            }
            lenghtHexStr =  Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(4));

        }
        /**
         * 计算返回机器时的crc值
         */
        byte [] hexByte = Hex_to_Decimal.hex2Bytes(data.replaceAll(" ",""));
        checkSum = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(CRC16Util.calcCrc16(hexByte)))+" ";
//        checkSum = socketPackage.getCheckSum();
        stopBit = socketPackage.getStopBit();
        String resultStr = start+" "+num+" "+type+" "+lenghtHexStr+" "+data+checkSum+stopBit;
        byte [] b = Hex_to_Decimal.hex2Bytes((resultStr.replaceAll(" ","")));
        return b;
    }


    /**
     * 设置机器数据包
     * @param ctx
     * @return
     */
    public static void socket_setMachine_Data(ChannelHandlerContext ctx, ZkMachineSet zkMachineSet){
        DeviceSession deviceSession = ctx.channel().attr(ServerHandler.KEY).get();
        SocketPackage socketPackage = deviceSession.getSocketPackage();
        String start = "";//起始位
        String num = "";//序列号
        String type = "05";//数据包类型
        String lenghtHexStr = "";
        String checkSum = "";

        start = Hex_to_Decimal.strToHexStr(socketPackage.getStartNum());
        num = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(socketPackage.getSerialNum()));
        lenghtHexStr = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(12));
        //数据包
        String data =Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(zkMachineSet.getResidueArea()))
                +
                Hex_to_Decimal.threeHex16ToSmall(Hex_to_Decimal.floatThreeHex16(zkMachineSet.getSpraying()))
                +
                Hex_to_Decimal.intOneHex16(zkMachineSet.getFactoryReset())
                +
                Hex_to_Decimal.intOneHex16(zkMachineSet.getFactoryTime())
                +
                Hex_to_Decimal.intOneHex16(zkMachineSet.getReach())
                +
                Hex_to_Decimal.intOneHex16(zkMachineSet.getVoice())
                ;
        byte [] hexByte = Hex_to_Decimal.hex2Bytes(data.replaceAll(" ",""));
        checkSum = Hex_to_Decimal.towHex16ToSmall(Hex_to_Decimal.intToHex16(CRC16Util.calcCrc16(hexByte)))+" ";
        String resultStr = start+" "+num+" "+type+" "+lenghtHexStr+" "+data+checkSum+"0D0A";
        byte [] setBytes = Hex_to_Decimal.hex2Bytes((resultStr.replaceAll(" ","")));

        //将设置id存入DeviceSession
        deviceSession.setMachineSetId(zkMachineSet.getId());
        ctx.channel().attr(ServerHandler.KEY).set(deviceSession);

        ByteBuf bufff = Unpooled.buffer();
        bufff.writeBytes(setBytes);
        ctx.channel().write(bufff);
        ctx.flush();
        logger.info("设置信息发送完毕...");

    }

}
