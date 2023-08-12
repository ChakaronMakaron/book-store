package com.andersen;

import com.andersen.config.DependencyModule;
import com.andersen.config.model.ConfigModel;
import com.andersen.controllers.router.RouterServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import jakarta.servlet.http.HttpServlet;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class EntryPoint {

    public static void main(String[] args) throws Exception {

        Injector injector = Guice.createInjector(new DependencyModule());

        ConfigModel config = injector.getInstance(ConfigModel.class);
        HttpServlet routerServlet = injector.getInstance(RouterServlet.class);

        String tempDir = System.getProperty("java.io.tmpdir");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(tempDir);

        Connector httpConnector = new Connector();
        httpConnector.setPort(config.port());
        tomcat.getService().addConnector(httpConnector);

        Context servletContext = tomcat.addContext(config.contextPath(), tempDir);

        tomcat.addServlet(config.contextPath(), "RouterServlet", routerServlet);
        servletContext.addServletMappingDecoded("/*", "RouterServlet");

        tomcat.start();

        tomcat.getServer().await();
    }
}
