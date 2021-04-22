package com.xingyun.server;

import java.io.IOException;
import java.io.InputStream;

public class MyRequest {

    private InputStream inputStream;

    private String method;

    private String url;

    private String origin;

    private String requestBody;

    private String host;

    private String context;

    public MyRequest(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }

        byte[] bytes = new byte[count];
        inputStream.read(bytes);

        String inputStr = new String(bytes);



        this.origin = inputStr;
        String[] split = origin.split("\r\n\r\n");
        if (split.length > 1){
            this.requestBody = split[1];
        }

        String first = split[0].split("\n")[0];
        this.method = first.split(" ")[0];
        String uri = first.split(" ")[1];
        this.url = uri.substring(uri.indexOf("/",1));

        this.context = uri.substring(1,uri.indexOf("/",1));

        for (String s : split[0].split("\n")) {
           if (s.contains("Host")){
               String hostHeader = s;
               this.host = hostHeader.split(": ")[1].split(":")[0];
           }
        }

        //System.out.println(origin);

    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
