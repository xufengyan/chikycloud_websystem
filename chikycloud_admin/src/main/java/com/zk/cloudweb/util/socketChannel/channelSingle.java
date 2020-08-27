package com.zk.cloudweb.util.socketChannel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xf
 * @version 1.0
 * @date 2020/8/19 15:59
 */
public class channelSingle {
    private Map<String, ChannelHandlerContext> channelMap = new HashMap<>();
    private static channelSingle channelSingle = null;
    public static channelSingle getChannelUtil(){
        if (null == channelSingle){
            channelSingle = new channelSingle();
        }
        return channelSingle;
    }

    public Map<String, ChannelHandlerContext> getChannelMap() {
        return channelMap;
    }

    public void setChannelMap(Map<String, ChannelHandlerContext> channelMap) {
        this.channelMap = channelMap;
    }
}
