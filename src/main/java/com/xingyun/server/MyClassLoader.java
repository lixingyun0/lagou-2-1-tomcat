package com.xingyun.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader  extends ClassLoader{

    private String path;

    public MyClassLoader(String path) {
        this.path = path;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> cls = findLoadedClass(name);
        if (cls != null) {
            return cls;
        }

        try {
            InputStream is = new FileInputStream(path + name.replace(".", "/") + ".class");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.loadClass(name);
    }

}
