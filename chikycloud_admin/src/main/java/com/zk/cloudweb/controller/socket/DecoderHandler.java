package com.zk.cloudweb.controller.socket;

import com.zk.cloudweb.controller.socket.util.Hex_to_Decimal;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义解码器
 */
public class DecoderHandler extends ByteToMessageDecoder {
 
    private static Logger logger = LoggerFactory.getLogger(ServerHandler.class);
 
    private static Map<ChannelHandlerContext, String> msgBufMap = new ConcurrentHashMap<>();
 
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println(in);
        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data);
        //5A 4B 43 54 00 00 00 0C 00 08 69 46 70 40 60 46 71 00 00 00 00 C4 D2 0D 0A
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        String msg = new String(data);
        // 处理粘包拆包问题
        if (msg.startsWith("#")) {
            if (msg.endsWith("#")) {
                out.add(msg);
            } else {
                msgBufMap.put(ctx, msg);
            }
        } else if (msg.endsWith("#") && msgBufMap.containsKey(ctx)) {
            msg = msgBufMap.get(ctx) + msg.split("#")[0];
            out.add(msg);
            msgBufMap.remove(ctx);
        } else {

            //将socket接收的数据转为16进制的字符串
            String result = Hex_to_Decimal.byteToHex(data);

//            String strGBK = URLEncoder.encode(msg, "GBK");
//            System.out.println(strGBK);
//            String strUTF8 = URLDecoder.decode(msg, "UTF-8");
//            System.out.println("接收的数据："+msg);
//            String [] msgArrt = msg.split(" ");
            out.add(result);
        }
    }






}
