package com.zk.cloudweb;

import com.zk.cloudweb.controller.socket.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.zk.cloudweb.dao")

public class ChikycloudAdminApplication implements CommandLineRunner {

    @Autowired
    NettyServer nettyServer;
    public static void main(String[] args) {
        SpringApplication.run(ChikycloudAdminApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        nettyServer.start();
    }
}
