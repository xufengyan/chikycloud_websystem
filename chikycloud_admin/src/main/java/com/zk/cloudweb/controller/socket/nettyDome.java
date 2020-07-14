package com.zk.cloudweb.controller.socket;

import com.zk.cloudweb.controller.socket.util.Hex_to_Decimal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/1 13:48
 */
public class nettyDome {

    public static void main(String[] args) throws IOException {

//       Socket s= new Socket("192.168.0.131",8899);
//        OutputStream out = s.getOutputStream();
//        out.write(("数据#SADASDasd#asdas").getBytes());
//        s.close();

//        System.out.println(Integer.valueOf("000C", 16));
//        System.out.println(Integer.valueOf("006E", 16));
//
//        String hexString = "00005EDA";
//        Long l= nettyDome.parseLong(hexString, 16);
//        Float f = Float.intBitsToFloat(l.intValue());
//
//        System.out.println("16进制转float："+f);
////        System.out.println(Float.intBitsToFloat(Integer.parseInt("00005EDA", 16)));
//        System.out.println(Float.intBitsToFloat(Integer.valueOf("00005EDA", 16)));

        System.out.println(Hex_to_Decimal.strToHexStr("ZKCT"));

        System.out.println(Hex_to_Decimal.intToHex16(12));


    }


    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }



    /**
     * 代码来自：java.lang.Long
     * 因为要跟踪看变量的值，所以要copy出来，或者是去附加源码，否则 eclipse 调试时查看变量的值会提示 xxx cannot be resolved to a variable
     * @author 微wx笑
     * @date   2017年12月6日下午5:19:40
     * @param s
     * @param radix
     * @return
     * @throws NumberFormatException
     */
    public static long parseLong(String s, int radix) throws NumberFormatException {
        if (s == null) {
            throw new NumberFormatException("null");
        }

        if (radix < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix + " less than Character.MIN_RADIX");
        }
        if (radix > Character.MAX_RADIX) {
            throw new NumberFormatException("radix " + radix + " greater than Character.MAX_RADIX");
        }

        long result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        long limit = -Long.MAX_VALUE;
        long multmin;
        int digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                } else if (firstChar != '+')
                    throw NumberFormatException.forInputString(s);

                if (len == 1) // Cannot have lone "+" or "-"
                    throw NumberFormatException.forInputString(s);
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(s.charAt(i++), radix);
                if (digit < 0) {
                    throw NumberFormatException.forInputString(s);
                }
                if (result < multmin) {
                    throw NumberFormatException.forInputString(s);
                }
                result *= radix;
                if (result < limit + digit) {
                    throw NumberFormatException.forInputString(s);
                }
                result -= digit;
            }
        } else {
            throw NumberFormatException.forInputString(s);
        }
        return negative ? result : -result;
    }
}

/**
 * 代码来自：java.lang.NumberFormatException
 * NumberFormatException
 * @author 微wx笑
 * @date   2017年12月6日下午5:20:36
 */
class NumberFormatException extends IllegalArgumentException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NumberFormatException(String s) {
        super(s);
    }

    static NumberFormatException forInputString(String s) {
        return new NumberFormatException("For input string: \"" + s + "\"");
    }


}
