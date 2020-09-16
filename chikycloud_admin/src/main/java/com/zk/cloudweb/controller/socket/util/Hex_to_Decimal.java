package com.zk.cloudweb.controller.socket.util;

import com.zk.cloudweb.entity.socketLink.SocketPackage;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/8 11:13
 * 将16进制的数转换为10进制
 */
public class Hex_to_Decimal {

    /**
     * 16进制字符串转Integer
     * @param hex
     * @return
     */
    public static Integer hexToInteger(String hex){
        return Integer.valueOf(hex, 16);
    }

    /**
     * 16进制字符串转float
     * @param hex
     * @return
     */
    public static float hexToFloat(String hex){
        return Float.intBitsToFloat(Integer.valueOf(hex.trim(), 16));
    }


    /**
     * float转16进制字符串4个字节
     * @param b
     * @return
     */
    public static String floatThreeHex16(float b) {

        return Integer.toHexString(Float.floatToIntBits(b)).toUpperCase();
    }

    /**
     * 将整形转换为16进制的2个字节的字符串
     * @param b
     * @return
     */
    public static String intFourHex16(int b) {

        return String.format("%08x", b).toUpperCase();
    }

    /**
     * 将整形转换为16进制的2个字节的字符串
     * @param b
     * @return
     */
    public static String intToHex16(int b) {

        return String.format("%04x", b).toUpperCase();
    }

    /**
     * 将整形转换为16进制的1个字节的字符串
     * @param b
     * @return
     */
    public static String intOneHex16(int b) {
        return String.format("%02x", b).toUpperCase();
    }


    /**
     * 将2个字节16进制字符串转换为小端模式
     * @param hex
     * @return
     */
    public static String towHex16ToSmall(String hex){
         int le = hex.length();
         if (le==4) {
             String head = hex.substring(0, 2);
             String end = hex.substring(2, 4);
             hex = end + " " + head;
         }
         return hex;
    }
    /**
     * 将4个字节16进制字符串转换为小端模式
     * @param hex
     * @return
     */
    public static String threeHex16ToSmall(String hex){
        int le = hex.length();
        if (le==8) {
            String head = hex.substring(0, 2);
            String end1 = hex.substring(2, 4);
            String end2 = hex.substring(4, 6);
            String end3 = hex.substring(6, 8);
            hex = end3 +" "+ end2+" "+end1+" "+ head;
        }
        return hex;
    }

    /**
     * 将16进制字符串装换为正常字符串
     * @param hex
     * @return
     */
    public static String hexToString(String hex){
        if (hex == null || hex.equals("")) {
            return null;
        }
        hex = hex.replace(" ", "");
        byte[] baKeyword = new byte[hex.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            hex = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return hex;
    }


    /**
     * 将字符串转换为16进制
     * @param str
     * @return
     */
    public static String strToHexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
                bit = (bs[i] & 0x0f0) >> 4;
                sb.append(chars[bit]);
                bit = bs[i] & 0x0f;
                 sb.append(chars[bit]);
                 sb.append(" ");
        }
        return sb.toString().trim();
    }





    /**
     * 将byte数组转换为16进制字符串
     * @param data
     * @return
     */
    public static String byteToHex(byte [] data){
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : data) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex + " ";
        }
        return result;
    }


    /**
     * 将16进制字符串转换为byte数组
     * @param hex
     * @return
     */
//    public static byte[] hex2Bytes(String hex) {
//        if (hex == null || hex.length() == 0) {
//            return null;
//        }
//        char[] hexChars = hex.toCharArray();
//        byte[] bytes = new byte[hexChars.length / 2];   // 如果 hex 中的字符不是偶数个, 则忽略最后一个
//
//        for (int i = 0; i < bytes.length; i++) {
//            bytes[i] = (byte) Integer.parseInt("" + hexChars[i * 2] + hexChars[i * 2 + 1], 16);
//        }
//        return bytes;
//    }
    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] hex2Bytes(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }








}
