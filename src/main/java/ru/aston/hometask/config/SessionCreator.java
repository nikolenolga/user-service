package ru.aston.hometask.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.exception.DatabaseException;
import ru.aston.hometask.utils.Message;

import java.io.Closeable;

import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_DRIVER;

@Slf4j
public class SessionCreator implements Closeable {
    private final SessionFactory sessionFactory;

    public SessionCreator(ApplicationProperties applicationProperties) {
        log.info("Connecting to database...");
        String driver = applicationProperties.getProperty(JAKARTA_JDBC_DRIVER);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error("Driver {} not found: {}", driver, e.getMessage());
            throw new DatabaseException(Message.DRIVER_NOT_FOUND);
        }

        Configuration configuration = new Configuration();
        configuration.addProperties(applicationProperties);
        configuration.addAnnotatedClass(User.class);

        try {
            sessionFactory = configuration.buildSessionFactory();
        } catch (HibernateException e) {
            log.error(e.getMessage());
            throw new DatabaseException(Message.THE_DATABASE_SERVICE_IS_NOT_RESPONDING);
        }
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
