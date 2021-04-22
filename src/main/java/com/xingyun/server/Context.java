package com.xingyun.server;

import java.util.List;

public class Context {

    private String context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    private List<Wrapper> wrapperList;

    public List<Wrapper> getWrapperList() {
        return wrapperList;
    }

    public void setWrapperList(List<Wrapper> wrapperList) {
        this.wrapperList = wrapperList;
    }
}
