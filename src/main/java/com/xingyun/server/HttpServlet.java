package com.xingyun.server;

public abstract class HttpServlet implements Servlet{

    public abstract void doGet(MyRequest request, MyResponse response);

    public abstract void doPost(MyRequest request, MyResponse response);


    @Override
    public void service(MyRequest request, MyResponse response) {
        if ("GET".equals(request.getMethod())){
            doGet(request,response);
        }else {
            doPost(request,response);
        }
    }
}
