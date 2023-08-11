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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EntryPoint {

    public static void main(String[] args) throws Exception {
        ConfigModel config = readConfig();

        Injector injector = Guice.createInjector(new DependencyModule(config));

        HttpServlet routerServlet = injector.getInstance(RouterServlet.class);

        String tempDir = System.getProperty("java.io.tmpdir");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(tempDir);

        Connector httpConnector = new Connector();
        httpConnector.setPort(config.getPort());
        tomcat.getService().addConnector(httpConnector);

        Context servletContext = tomcat.addContext(config.getContextPath(), tempDir);

        tomcat.addServlet(config.getContextPath(), "RouterServlet", routerServlet);
        servletContext.addServletMappingDecoded("/*", "RouterServlet");

        tomcat.start();

        tomcat.getServer().await();
    }

    private static ConfigModel readConfig() {
        try (InputStream input = EntryPoint.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties properties = new Properties();

            properties.load(input);

            return new ConfigModel(properties);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
