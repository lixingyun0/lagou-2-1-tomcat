package com.xingyun.server;

import java.util.List;

public class Host {

    public Host(List<Context> contextList) {
        this.contextList = contextList;
    }

    public Host() {
    }

    private int port;

    private String hostname;

    private List<Context> contextList;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<Context> getContextList() {
        return contextList;
    }

    public void setContextList(List<Context> contextList) {
        this.contextList = contextList;
    }
}
