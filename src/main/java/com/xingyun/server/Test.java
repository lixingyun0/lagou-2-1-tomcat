package com.xingyun.server;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Test {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        /*String baseDir = "/Users/xingyun/Desktop/webapps/";

        String clazz = "com.xingyun.server.MyServlet";

        String substring = clazz.substring(0, clazz.lastIndexOf("."));
        System.out.println(substring);

        String s = clazz.substring(0, clazz.lastIndexOf(".")).replaceAll("\\.", File.separator);

        String ab = baseDir +s ;
        System.out.println(ab);*/

        String clazz = "com.xingyun.server.MyServlet";

        String test = "file:/Users/xingyun/Desktop/webapps/demo1/";

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL(test)});

        Object o2 = Class.forName(clazz, true, urlClassLoader).newInstance();
        System.out.println(o2);

       // System.out.println(o2);

        Class<?> aClass1 = urlClassLoader.loadClass(clazz);
        Object o1 = aClass1.newInstance();

        System.out.println(o1);

        System.out.println("====");

        MyClassLoader myClassLoader = new MyClassLoader("/Users/xingyun/Desktop/webapps/demo1/");
        Class<?> aClass = myClassLoader.findClass("com.xingyun.server.MyServlet");
        Object o = aClass.newInstance();

        System.out.println(o);
    }
}
