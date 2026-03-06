package ru.aston.hometask.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.aston.hometask.config.SessionCreator;
import ru.aston.hometask.entity.User;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository implements Repository<User> {
    private final SessionCreator sessionCreator;

    @Override
    public Optional<User> create(User user) {
        try (Session session = sessionCreator.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(user);
                transaction.commit();
                System.out.println("from repo: " + user);
                return Optional.of(user);
            } catch (Exception e) {
                transaction.rollback();
                return Optional.empty();
            }
        }
    }

//    @Override
//    public Optional<User> create(User user) {
//        if (user != null) {
//            Long id = nextId();
//            user.setId(id);
//            user.setCreatedAt(LocalDateTime.now());
//            storage.put(id, user);
//        }
//
//        return Optional.ofNullable(user);
//    }

    @Override
    public Optional<User> get(long id) {
        try (Session session = sessionCreator.getSession()) {
            User user = session.find(User.class, id);
            return Optional.ofNullable(user);
        }
    }

//    @Override
//    public Optional<User> get(long id) {
//        if (storage.containsKey(id)) {
//            return Optional.ofNullable(storage.get(id));
//        }
//        return Optional.empty();
//    }

    @Override
    public Optional<User> update(User user) {
        try (Session session = sessionCreator.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User merged = session.merge(user);
                transaction.commit();
                return Optional.of(merged);
            } catch (Exception e) {
                transaction.rollback();
                return Optional.empty();
            }
        }
    }

//    @Override
//    public Optional<User> update(User user) {
//        Long id = user.getId();
//        if (!storage.containsKey(id)) {
//            throw new AppException("User with id " + id + " does not exist");
//        }
//        return Optional.ofNullable(storage.put(id, user));
//    }

    @Override
    public boolean delete(User user) {
        try (Session session = sessionCreator.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.remove(user);
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
                return false;
            }
        }
    }

//    @Override
//    public void delete(User user) {
//        Long id = user.getId();
//        if (!storage.containsKey(id)) {
//            throw new AppException("User with id " + id + " does not exist");
//        }
//
//        storage.remove(id);
//    }

    @Override
    public boolean delete(long id) {
        try (Session session = sessionCreator.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.find(User.class, id);
                session.remove(user);
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
                return false;
            }
        }
    }

//    @Override
//    public boolean delete(long id) {
//        if (!storage.containsKey(id)) {
//            return false;
//        }
//
//        storage.remove(id);
//        return true;
//    }

    @Override
    public Collection<User> getAll() {
        try (Session session = sessionCreator.getSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        }
    }

//    @Override
//    public Collection<User> getAll() {
//        return storage.values();
//    }

}
