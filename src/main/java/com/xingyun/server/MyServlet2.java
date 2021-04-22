package com.xingyun.server;

public class MyServlet2 extends HttpServlet {


    @Override
    public void doGet(MyRequest request, MyResponse response){
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("GET -> " + request.getUrl() + " " + request.getMethod() );
        response.outputContent("GET -> " + request.getUrl() + " " + request.getMethod() );
    }

    @Override
    public void doPost(MyRequest request, MyResponse response){
        System.out.println("POST -> " + request.getUrl() + " " + request.getMethod() );
        response.outputContent("POST -> " + request.getUrl() + " " + request.getMethod());
    }

    @Override
    public void init() {

    }

    @Override
    public void destory() {

    }
}
