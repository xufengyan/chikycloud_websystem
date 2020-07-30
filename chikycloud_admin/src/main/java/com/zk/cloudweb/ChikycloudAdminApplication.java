package com.zk.cloudweb;

import com.zk.cloudweb.controller.socket.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.PreDestroy;

@SpringBootApplication
@MapperScan(basePackages = "com.zk.cloudweb.dao")

public class ChikycloudAdminApplication implements CommandLineRunner {

    @Autowired
    NettyServer nettyServer;
    public static void main(String[] args) {
        SpringApplication.run(com.zk.cloudweb.ChikycloudAdminApplication.class, args);
    }

    /**
     * 项目启动的时候执行的方法
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        nettyServer.start();
    }

    /**
     * 项目关闭的时候执行的方法
     * @throws Exception
     */
    @PreDestroy
    public void destory() throws Exception{
        System.out.println("项目关闭的时候执行");
    }
}
