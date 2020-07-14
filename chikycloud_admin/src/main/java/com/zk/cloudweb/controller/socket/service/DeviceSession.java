package com.zk.cloudweb.controller.socket.service;

import com.zk.cloudweb.entity.socketLink.SocketPackage;
import com.zk.cloudweb.entity.socketLink.Socketlogin;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class DeviceSession {
	private String clientId;
	
	private Channel channel;
	
	private String returnValue;

	private SocketPackage socketPackage;

	private Socketlogin socketlogin;

	public DeviceSession(Channel channel) {
		this.channel = channel;
	}

	//记录心跳次数
	private Integer heartbeatNum;
}