package ru.aston.hometask.service;

import lombok.AllArgsConstructor;
import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.mapper.Dto;
import ru.aston.hometask.repository.Repository;
import ru.aston.hometask.utils.Message;

import java.util.Collection;
import java.util.Objects;

@AllArgsConstructor
public class UserService {
    private final Repository<User> repository;

    public UserTo create(String name, String email, Integer age) {
        UserTo userTo = UserTo.builder().name(name).email(email).age(age).build();

        return repository.create(Dto.MAPPER.from(userTo))
                .map(Dto.MAPPER::from)
                .orElseThrow(() -> new AppException(Message.USER_NOT_CREATED));
    }

    public UserTo create(UserTo userTo) {

        return repository.create(Dto.MAPPER.from(userTo))
                .map(Dto.MAPPER::from)
                .orElseThrow(() -> new AppException(Message.USER_NOT_CREATED));
    }

    public UserTo read(Long id) {
        return repository.get(id)
                .map(Dto.MAPPER::from)
                .orElseThrow(() -> new AppException(Message.USER_WITH_ID_D_NOT_FOUND.formatted(id)));
    }

    public UserTo update(Long id, String name, String email, Integer age) {
        UserTo read = read(id);
        UserTo updating = UserTo.builder()
                .id(id)
                .name(Objects.isNull(name) ? read.getName() : name)
                .email(Objects.isNull(email) ? read.getEmail() : email)
                .age(Objects.isNull(age) ? read.getAge() : age)
                .createdAt(read.getCreatedAt())
                .build();

        return update(updating);
    }

    public UserTo update(UserTo userTo) {

        return repository.update(Dto.MAPPER.from(userTo))
                .map(Dto.MAPPER::from)
                .orElseThrow(() -> new AppException(Message.CAN_T_UPDATE_USER_S.formatted(userTo)));
    }

    public void delete(Long id) {
        if (!repository.delete(id)) {
            throw new AppException(Message.USER_WITH_ID_D_NOT_FOUND.formatted(id));
        }
    }

    public Collection<UserTo> getAll() {
        return repository.getAll()
                .stream()
                .map(Dto.MAPPER::from)
                .toList();
    }
}
