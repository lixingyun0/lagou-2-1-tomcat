package com.xingyun.server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpProtocolUtil {



    public static String header200(int length){

        String header = "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + length + " \n" +
                "\r\n";

        return header;

    }
    public static void send200(String data, OutputStream outputStream){

        String header = "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + data.getBytes(StandardCharsets.UTF_8).length + " \n" +
                "\r\n";

        try {
            outputStream.write((header+data).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send404(OutputStream outputStream){
        String str404 = "<h1>404 not found</h1>";
        String content =  "HTTP/1.1 404 NOT Found \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + str404.getBytes().length + " \n" +
                "\r\n" + str404;

        try {
            outputStream.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String content404(){
        String str404 = "<h1>404 not found</h1>";
        String content =  "HTTP/1.1 404 NOT Found \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + str404.getBytes().length + " \n" +
                "\r\n" + str404;

        return content;
    }
}
