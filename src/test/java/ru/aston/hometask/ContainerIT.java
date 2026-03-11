package ru.aston.hometask;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.JdbcSettings;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.aston.hometask.config.ApplicationProperties;
import ru.aston.hometask.config.Configuration;
import ru.aston.hometask.config.SessionCreator;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class ContainerIT {
    public static final String DOCKER_IMAGE_NAME = "postgres:16.3";
    private static final JdbcDatabaseContainer<?> CONTAINER;
    public static Configuration configuration;
    public static SessionCreator sessionCreator;

    static {
        log.info("Starting PostgreSQL test container");
        CONTAINER = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME);
        CONTAINER.start();

        ApplicationProperties properties = new ApplicationProperties();
        properties.setProperty(JdbcSettings.JAKARTA_JDBC_URL, CONTAINER.getJdbcUrl());
        properties.setProperty(JdbcSettings.JAKARTA_JDBC_USER, CONTAINER.getUsername());
        properties.setProperty(JdbcSettings.JAKARTA_JDBC_PASSWORD, CONTAINER.getPassword());

        configuration = new Configuration();
        configuration.configureApplication(properties);
        sessionCreator = configuration.getSessionCreator();
        log.info("Test container ContainerIt initialized");
    }

    public ContainerIT() {
        init();
    }

    public static void init() {
        log.info("test container ContainerIt initialized");
    }

    @Test
    void testSessionCreator() {
        assertNotNull(sessionCreator);
    }
}
