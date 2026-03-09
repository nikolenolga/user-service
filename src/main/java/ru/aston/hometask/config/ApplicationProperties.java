package ru.aston.hometask.config;

import lombok.extern.slf4j.Slf4j;
import ru.aston.hometask.exception.DatabaseException;
import ru.aston.hometask.utils.Message;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class ApplicationProperties extends Properties {
    private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    private final static Path CLASSES_ROOT = Paths.get(URI.create(
            Objects.requireNonNull(
                    ApplicationProperties.class.getResource("/")
            ).toString()));

    public ApplicationProperties() {
        String propertiesPath = CLASSES_ROOT + File.separator + APPLICATION_PROPERTIES_FILE;
        log.info("Loading properties from {}", propertiesPath);
        try (FileReader fileReader = new FileReader(propertiesPath)) {
            this.load(fileReader);
        } catch (IOException e) {
            log.error("Error loading properties from {}", propertiesPath, e);
            throw new DatabaseException(Message.THE_DATABASE_CONNECTION_PROPERTIES_NOT_FOUND);
        }
    }
}
