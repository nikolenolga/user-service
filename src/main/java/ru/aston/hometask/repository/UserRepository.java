package ru.aston.hometask.repository;

import ru.aston.hometask.entity.User;
import ru.aston.hometask.exception.AppException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository implements Repository<User> {
    private final Map<Long, User> storage = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public Optional<User> create(User user) {
        if (user != null) {
            Long id = nextId();
            user.setId(id);
            user.setCreatedAt(LocalDateTime.now());
            storage.put(id, user);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> get(long id) {
        if (storage.containsKey(id)) {
            return Optional.ofNullable(storage.get(id));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User user) {
        Long id = user.getId();
        if (!storage.containsKey(id)) {
            throw new AppException("User with id " + id + " does not exist");
        }
        return Optional.ofNullable(storage.put(id, user));
    }

    @Override
    public void delete(User user) {
        Long id = user.getId();
        if (!storage.containsKey(id)) {
            throw new AppException("User with id " + id + " does not exist");
        }

        storage.remove(id);
    }

    @Override
    public boolean delete(long id) {
        if (!storage.containsKey(id)) {
            return false;
        }

        storage.remove(id);
        return true;
    }

    @Override
    public Collection<User> getAll() {
        return storage.values();
    }

    private Long nextId() {
        return counter.incrementAndGet();
    }
}
