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

    private UserTo(Builder builder) {
        id = builder.id;
        name = builder.name;
        email = builder.email;
        age = builder.age;
        createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private Integer age;
        private LocalDateTime createdAt;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserTo build() {
            Validator.validate(name, email, age);
            return new UserTo(this);
        }
    }
}
