package ru.aston.hometask.service;

import java.util.Collection;

public interface Service<T> {

    T create(T dto);

    T read(Long id);

    T update(T dto);

    void delete(Long id);

    Collection<T> getAll();
}
