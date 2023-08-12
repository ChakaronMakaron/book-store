package com.andersen.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.andersen.EntryPoint;
import com.andersen.config.model.ConfigModel;
import com.andersen.controllers.router.RouterServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import jakarta.inject.Singleton;
import jakarta.servlet.http.HttpServlet;

public class DependencyModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        bind(HttpServlet.class).to(RouterServlet.class);
    }

    @Provides
    @Singleton
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Provides
    @Singleton
    public ConfigModel config(ObjectMapper objectMapper) {
        return loadConfig(objectMapper);
    }

    private ConfigModel loadConfig(ObjectMapper objectMapper) {
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
