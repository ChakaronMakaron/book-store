package com.andersen.config.model;

import java.util.Properties;

public class ConfigModel {
    private final Properties properties;

    public ConfigModel(Properties properties) {
        this.properties = properties;
    }

    public String getContextPath() {
        return properties.getProperty("context.path");
    }

    public Integer getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }

    public String getSavePath() {
        return properties.getProperty("save.path");
    }

    public String getBooksFile() {
        return properties.getProperty("books.file");
    }

    public String getOrdersFile() {
        return properties.getProperty("orders.file");
    }

    public String getRequestsFile() {
        return properties.getProperty("requests.file");
    }
}