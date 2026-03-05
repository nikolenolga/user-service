package ru.aston.hometask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserTo {
    public static final Integer MIN_AGE = 0;
    public static final Integer MAX_AGE = 150;

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDateTime createdAt;
}
