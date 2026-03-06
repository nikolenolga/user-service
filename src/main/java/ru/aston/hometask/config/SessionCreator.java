package ru.aston.hometask.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.exception.AppException;

import java.io.Closeable;

import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_DRIVER;

public class SessionCreator implements Closeable {
    private final SessionFactory sessionFactory;

    public SessionCreator(ApplicationProperties applicationProperties) {
        String driver = applicationProperties.getProperty(JAKARTA_JDBC_DRIVER);
        System.out.println("Jakarta JDBC driver: " + driver);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new AppException("Driver class not found: " + driver, e);
        }

        Configuration configuration = new Configuration();
        configuration.addProperties(applicationProperties);

        configuration.addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
