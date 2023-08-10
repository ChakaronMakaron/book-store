package com.andersen.config;

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
}
