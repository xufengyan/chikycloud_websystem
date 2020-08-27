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

	/**
	 * 连接数据
	 */
	private SocketPackage socketPackage;

	/**
	 * 登录数据
	 */
	private Socketlogin socketlogin;

	/**
	 * 用户设置机器Id
	 */
	private String machineSetId;

	public DeviceSession(Channel channel) {
		this.channel = channel;
	}

	//记录心跳次数
	private Integer heartbeatNum;
}