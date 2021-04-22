package com.xingyun.server;

public interface Servlet {

    void init();

    void destory();

    void service(MyRequest request, MyResponse response);

}
