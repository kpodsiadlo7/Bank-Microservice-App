package com.mainapp.containers;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainers extends PostgreSQLContainer<PostgresTestContainers>{
    public static final String IMAGE_VERSION = "postgres:15.0";
    public static final String DATABASE_NAME = "postgres";
    public static PostgreSQLContainer container;

    public PostgresTestContainers() {
        super(IMAGE_VERSION);
    }

    public static PostgreSQLContainer getInstance() {
        if (container == null) {
            container = new PostgresTestContainers().withDatabaseName(DATABASE_NAME);
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
    }
}
