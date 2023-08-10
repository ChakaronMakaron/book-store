package com.andersen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.io.IOUtils;

import com.andersen.config.DependencyModule;
import com.andersen.config.model.ConfigModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;

import jakarta.servlet.http.HttpServlet;

public class EntryPoint {

    public static void main(String[] args) throws Exception {

        Injector injector = Guice.createInjector(new DependencyModule());

        ConfigModel config = readConfig(injector.getInstance(ObjectMapper.class));
        HttpServlet routerServlet = injector.getInstance(HttpServlet.class);

        String tempDir = System.getProperty("java.io.tmpdir");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(tempDir);
        tomcat.setPort(config.port());
        tomcat.getConnector();

        Context servletContext = tomcat.addContext(config.contextPath(), tempDir);

        tomcat.addServlet(config.contextPath(), "RouterServlet", routerServlet);
        servletContext.addServletMappingDecoded("/*", "RouterServlet");

        tomcat.start();
        tomcat.getServer().await();
    }

    private static ConfigModel readConfig(ObjectMapper objectMapper) {
        try {
            String rawConfig = IOUtils.toString(EntryPoint.class.getClassLoader().getResourceAsStream("config.json"),
                    StandardCharsets.UTF_8);
            System.out.printf("Config:\n%s", rawConfig);
            return objectMapper.readValue(rawConfig, ConfigModel.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
