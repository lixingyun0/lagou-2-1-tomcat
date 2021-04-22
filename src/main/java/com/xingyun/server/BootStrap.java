package com.xingyun.server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class BootStrap {

    private static Host host  = new Host();

    public static void main(String[] args) throws Exception {

        initHost();

        ServerSocket serverSocket = new ServerSocket(host.getPort());
        System.out.println("=====>>>Minicat start on port：" + host.getPort());

        int core = 4;
        int max = 20;
        long keepAliveTime =120;
        BlockingQueue blockingQueue = new ArrayBlockingQueue(200);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(core,max,keepAliveTime,TimeUnit.SECONDS,blockingQueue,threadFactory,handler);

        while (true){

            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket,host);
            executor.execute(requestProcessor);
        }


    }



    private static void initHost() throws Exception {

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(new File(BootStrap.class.getResource("/server.xml").getFile()));

        Element rootElement = document.getRootElement();

        Element serviceE = rootElement.element("Service");
        Element connectorE = serviceE.element("Connector");
        host.setPort(Integer.parseInt(connectorE.attributeValue("port")));
        Element hostE = serviceE.element("Engine").element("Host");
        host.setHostname(hostE.attributeValue("name"));
        String appBase = hostE.attributeValue("appBase");
        System.out.println("appBase : " + appBase);


        File file = new File(appBase);

        File[] contextDirs = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {

                return pathname.isDirectory();
            }
        });

        List<Context> contextList = new ArrayList<>();
        for (File contextDir  : contextDirs) {
            Context context = new Context();
            context.setContext(contextDir.getName());
            List<Wrapper> wrapperList = initWrapper(contextDir.getPath());
            context.setWrapperList(wrapperList);
            contextList.add(context);
        }
        host.setContextList(contextList);
        System.out.println(host);

    }

    private static List<Wrapper> initWrapper(String contextDirPath) throws Exception {

        List<Wrapper> wrapperList = new ArrayList<>();
        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(new File(contextDirPath + "/web.xml"));

        Element rootElement = document.getRootElement();

        List servletList = rootElement.selectNodes("//servlet");

        for (Object o : servletList) {

            Wrapper wrapper = new Wrapper();
            Element nameE = ((Element) o).element("servlet-name");
            Element clazzE = ((Element) o).element("servlet-class");

            Element servletMappingE = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + nameE.getStringValue() + "']");
            // /lagou
            String urlPattern = servletMappingE.selectSingleNode("url-pattern").getStringValue();

            //URLClassLoader

            String baseDir = contextDirPath;

            String clazz = clazzE.getStringValue();

            System.out.println("baseDir :" + contextDirPath);

            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new File(baseDir).toURI().toURL()});

            Object servlet = Class.forName(clazz, true, urlClassLoader).newInstance();

            wrapper.setUrl(urlPattern);
            wrapper.setServlet((HttpServlet) servlet);

            wrapperList.add(wrapper);

        }
        return wrapperList;
    }

    private static Map<String,HttpServlet> servletMapping = new HashMap<>();

    private static void initServletMapping() throws DocumentException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(new File(BootStrap.class.getResource("/web.xml").getFile()));

        Element rootElement = document.getRootElement();

        List servletList = rootElement.selectNodes("//servlet");

        for (Object o : servletList) {
            Element nameE = ((Element) o).element("servlet-name");
            Element clazzE = ((Element) o).element("servlet-class");

            Element servletMappingE = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + nameE.getStringValue() + "']");
            // /lagou
            String urlPattern = servletMappingE.selectSingleNode("url-pattern").getStringValue();

            servletMapping.put(urlPattern, (HttpServlet) Class.forName(clazzE.getStringValue()).getConstructor().newInstance());

        }
    }
}
