package com.koneksys.interview.configuration;

import org.h2.tools.Server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListenerConfig implements ServletContextListener {


    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Booting up");
        try {
            Server.createWebServer("-web","-webAllowOthers","-webPort","8082").start();
            Server.createTcpServer("-tcp","-tcpAllowOthers","-tcpPort","9092").start();

        } catch (Exception e){
            System.out.println("Could not create web server for console");
            e.printStackTrace();
        }
    }

}
