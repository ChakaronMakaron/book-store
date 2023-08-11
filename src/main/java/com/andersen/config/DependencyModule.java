package com.andersen.config;

import com.andersen.config.model.ConfigModel;
import com.andersen.controllers.BookController;
import com.andersen.controllers.impl.BookControllerCommandLine;
import com.andersen.controllers.router.RouterServlet;
import com.andersen.repositories.BookRepository;
import com.andersen.repositories.local.LocalBookRepository;
import com.andersen.services.BookService;
import com.andersen.services.impl.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import jakarta.inject.Singleton;

public class DependencyModule extends AbstractModule {
    private final ConfigModel config;
    public DependencyModule(ConfigModel config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        super.configure();
    }

    @Provides
    @Singleton
    public RouterServlet routerServlet() {
        return new RouterServlet(bookController());
    }

    @Provides
    @Singleton
    public BookController bookController() {
        return new BookControllerCommandLine(bookService());
    }

    @Provides
    @Singleton
    public BookService bookService() {
        return new BookServiceImpl(bookRepository());
    }

    @Provides
    @Singleton
    public BookRepository bookRepository() {
        return new LocalBookRepository(objectMapper(), config.getSavePath() + config.getBooksFile());
    }

    @Provides
    @Singleton
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
