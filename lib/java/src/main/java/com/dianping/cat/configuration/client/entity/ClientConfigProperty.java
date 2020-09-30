package com.dianping.cat.configuration.client.entity;

import java.io.Serializable;

/**
 * 用来简化CAT配置；
 */
public class ClientConfigProperty implements Serializable {

    private int port = 2280; // CAT 的服务端口；默认为2280

    private int httpPort = 8080;// CAT 的Admin端口；默认为8080

    private String[] servers; // CAT的服务端集群IP列表；

    private String appId; //在一个spring boot应用中，这个值 = spring.application.name;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public String[] getServers() {
        return servers;
    }

    public void setServers(String[] servers) {
        this.servers = servers;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return String.format("CatClientProperty(servers:%s, port:%d, httpPort:%d, appId:%s)", servers, port, httpPort, appId);
    }
}
