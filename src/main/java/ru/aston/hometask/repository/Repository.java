package ru.aston.hometask.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> create(T t);

    Optional<T> get(long id);

    Optional<T> update(T t);

    void delete(T t);

    boolean delete(long id);

    Collection<T> getAll();
}
