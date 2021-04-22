package com.xingyun.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class RequestProcessor extends Thread{

    private Socket socket;

    private Host host;

    public RequestProcessor(Socket socket, Host host) {
        this.socket = socket;
        this.host = host;
    }

    @Override
    public synchronized void run() {
        try {

            InputStream inputStream = socket.getInputStream();

            MyRequest myRequest = new MyRequest(inputStream);

            System.out.println(myRequest.getUrl());
            System.out.println(myRequest.getHost());
            System.out.println(myRequest.getContext());
            System.out.println(myRequest.getMethod());


            if (!myRequest.getHost().equals(host.getHostname())){
                socket.close();
                return;
            }
            List<Context> contextList = host.getContextList();
            for (Context context : contextList) {
                if (context.getContext().equals(myRequest.getContext())){
                    List<Wrapper> wrapperList = context.getWrapperList();
                    for (Wrapper wrapper : wrapperList) {
                        if (wrapper.getUrl().equals(myRequest.getUrl())){
                            HttpServlet httpServlet = wrapper.getServlet();
                            OutputStream outputStream = socket.getOutputStream();
                            MyResponse myResponse = new MyResponse(outputStream);
                            if (httpServlet == null){
                                myResponse.outHtml(myRequest.getUrl());
                            }else {
                                httpServlet.service(myRequest,myResponse);
                            }
                        }
                    }
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
