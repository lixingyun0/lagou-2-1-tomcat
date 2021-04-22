package com.xingyun.server;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MyResponse {

    private OutputStream outputStream;

    public MyResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void outputContent(String content){
        byte[] bytes = (HttpProtocolUtil.header200(content.getBytes(StandardCharsets.UTF_8).length) + content).getBytes(StandardCharsets.UTF_8);
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outHtml(String url){
        String path = MyResponse.class.getResource("/").getPath();

        File file = new File(path+url);

        if (file.exists() && file.isFile()){
            try {
                outputStaticSource(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            HttpProtocolUtil.send404(outputStream);
        }
    }



    private void outputStaticSource(File file) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);

        int available = fileInputStream.available();
        outputStream.write(HttpProtocolUtil.header200(available).getBytes(StandardCharsets.UTF_8));
        byte[] buffer = new byte[1024];

        int len;
        while ((len = fileInputStream.read(buffer)) != -1){
            outputStream.write(buffer,0,len);
        }
        outputStream.flush();

    }

    public static void main(String[] args) {
        String path = MyResponse.class.getResource("/index.html").getFile();
        System.out.println(path);
    }
}
