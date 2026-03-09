package ru.aston.hometask.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.aston.hometask.utils.Validator;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@ToString
public class UserTo {
    private final Long id;
    private final String name;
    private final String email;
    private final Integer age;
    private final LocalDateTime createdAt;

    public UserTo(Long id, String name, String email, Integer age, LocalDateTime createdAt) {
        Validator.validate(name, email, age);
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
    }

}
