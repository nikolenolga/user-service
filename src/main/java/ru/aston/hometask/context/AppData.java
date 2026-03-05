package ru.aston.hometask.context;

import java.util.HashMap;
import java.util.Map;

public class AppData {
    private final Map<String, Object> attributes = new HashMap<>();

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public <T> T getAttribute(String name, Class<T> type) {
        return (T) attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }
}
