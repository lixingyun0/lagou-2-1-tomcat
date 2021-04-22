package com.xingyun.server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFileTest {

    public static void main(String[] args) throws IOException {
        read1_1();
    }

    private static void read1() throws IOException {
        BufferedReader reader2 =  new BufferedReader(new FileReader("/Users/xingyun/Desktop/post.txt"));


        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = reader2.readLine()) != null){

            line = reader2.readLine();
            stringBuilder.append(line+"\n");

        }
        System.out.println(stringBuilder);
    }

    private static void read1_1() throws IOException {
        BufferedReader reader2 =  new BufferedReader(new FileReader("/Users/xingyun/Desktop/post.txt"));

        char[] buffer = new char[10];
        StringBuilder stringBuilder = new StringBuilder();
        int len;
        while ((len = reader2.read(buffer)) != -1){



            stringBuilder.append(buffer,0,len);

        }
        System.out.println(stringBuilder);
    }

    private static void read2() throws FileNotFoundException {
        byte[] buffer = new byte[10];
        StringBuilder stringBuilder = new StringBuilder();

        InputStream inputStream = new FileInputStream("/Users/xingyun/Desktop/post.txt");

        try {


            int len = -1;
            while ((len =inputStream.available())>0) {


                if (len<buffer.length){
                    buffer = new byte[len];
                    inputStream.read(buffer,0,len);

                }else {
                    inputStream.read(buffer);
                }



                stringBuilder.append(new String(buffer));
            }
            //stringBuilder.append(new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //inputStream.close();
        }

        System.out.println(stringBuilder);
    }

    private static void read3() throws FileNotFoundException {
        byte[] buffer = new byte[10];
        StringBuilder stringBuilder = new StringBuilder();

        InputStream inputStream = new FileInputStream("/Users/xingyun/Desktop/post.txt");

        try {


            int len = -1;
            while ((len =inputStream.read(buffer)) != -1) {


                stringBuilder.append(new String(buffer,0,len));
            }
            //stringBuilder.append(new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //inputStream.close();
        }

        System.out.println(stringBuilder);
    }

    private static void read4() throws FileNotFoundException {

        List<Byte> bytes = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        InputStream inputStream = new FileInputStream("/Users/xingyun/Desktop/post.txt");

        try {


            int b;
            while ((b =inputStream.read()) != -1) {

                bytes.add((byte) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] byteArray = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            byteArray[i] = bytes.get(i);
        }
        System.out.println(new String(byteArray));
    }
}
