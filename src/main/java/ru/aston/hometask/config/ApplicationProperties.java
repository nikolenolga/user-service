package ru.aston.hometask.config;

import ru.aston.hometask.exception.AppException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class ApplicationProperties extends Properties {
    private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    private final static Path CLASSES_ROOT = Paths.get(URI.create(
            Objects.requireNonNull(
                    ApplicationProperties.class.getResource("/")
            ).toString()));

    public ApplicationProperties() {
        String propertiesPath = CLASSES_ROOT + File.separator + APPLICATION_PROPERTIES_FILE;
        System.out.println("Loading properties from " + propertiesPath);
        try(FileReader fileReader = new FileReader(propertiesPath)) {
            this.load(fileReader);
        } catch (IOException e) {
            throw new AppException("Can't read properties file: " + propertiesPath, e);
        }
    }
}
