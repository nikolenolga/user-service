package ru.aston.hometask.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserTo {
    public static final Integer MIN_AGE = 0;
    public static final Integer MAX_AGE = 150;

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDateTime createdAt;
}
