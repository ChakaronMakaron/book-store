package com.andersen.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.andersen.App;
import com.andersen.config.model.ConfigModel;
import com.andersen.controllers.BookController;
import com.andersen.controllers.OrderController;
import com.andersen.controllers.RequestController;
import com.andersen.controllers.impl.ServletBookController;
import com.andersen.controllers.impl.ServletOrderController;
import com.andersen.controllers.impl.ServletRequestController;
import com.andersen.repositories.BookRepository;
import com.andersen.repositories.OrderRepository;
import com.andersen.repositories.RequestRepository;
import com.andersen.repositories.impl.BookRepositoryDummy;
import com.andersen.repositories.impl.OrderRepositoryDummy;
import com.andersen.repositories.impl.RequestRepositoryDummy;
import com.andersen.router.RouterServlet;
import com.andersen.services.BookService;
import com.andersen.services.OrderService;
import com.andersen.services.RequestService;
import com.andersen.services.impl.BookServiceImpl;
import com.andersen.services.impl.OrderServiceImpl;
import com.andersen.services.impl.RequestServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import jakarta.inject.Singleton;
import jakarta.servlet.http.HttpServlet;

public class DependencyModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HttpServlet.class).to(RouterServlet.class);
        // Controllers
        bind(BookController.class).to(ServletBookController.class);
        bind(OrderController.class).to(ServletOrderController.class);
        bind(RequestController.class).to(ServletRequestController.class);
        // Services
        bind(BookService.class).to(BookServiceImpl.class);
        bind(OrderService.class).to(OrderServiceImpl.class);
        bind(RequestService.class).to(RequestServiceImpl.class);
        // Repositories
        bind(BookRepository.class).to(BookRepositoryDummy.class);
        bind(OrderRepository.class).to(OrderRepositoryDummy.class);
        bind(RequestRepository.class).to(RequestRepositoryDummy.class);
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

            String rawConfig = IOUtils.toString(App.class.getClassLoader().getResourceAsStream("config.json"),
                    StandardCharsets.UTF_8);
            System.out.printf("Config:\n%s", rawConfig);

            return objectMapper.readValue(rawConfig, ConfigModel.class);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
