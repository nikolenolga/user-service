package ru.aston.hometask.service;

import lombok.AllArgsConstructor;
import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.mapper.Dto;
import ru.aston.hometask.repository.UserRepository;
import ru.aston.hometask.utils.Message;

import java.util.Collection;
import java.util.Objects;

@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserTo create(String name, String email, Integer age) {
        UserTo userTo = new UserTo(null, name, email, age, null);

        return userRepository.create(Dto.MAPPER.from(userTo))
                .map(Dto.MAPPER::from)
                .orElseThrow(() -> new AppException(Message.USER_NOT_CREATED));
    }

    @Override
    public UserTo create(UserTo userTo) {

        return userRepository.create(Dto.MAPPER.from(userTo))
                .map(Dto.MAPPER::from)
                .orElseThrow(() -> new AppException(Message.USER_NOT_CREATED));
    }

    @Override
    public UserTo read(Long id) {
        return userRepository.get(id)
                .map(Dto.MAPPER::from)
                .orElseThrow(() -> new AppException(Message.USER_WITH_ID_D_NOT_FOUND.formatted(id)));
    }

    @Override
    public UserTo update(Long id, String name, String email, Integer age) {
        UserTo read = read(id);
        UserTo updating = new UserTo(
                id,
                Objects.isNull(name) ? read.getName() : name,
                Objects.isNull(email) ? read.getEmail() : email,
                Objects.isNull(age) ? read.getAge() : age,
                read.getCreatedAt());

        return update(updating);
    }

    @Override
    public UserTo update(UserTo userTo) {

        return userRepository.update(Dto.MAPPER.from(userTo))
                .map(Dto.MAPPER::from)
                .orElseThrow(() -> new AppException(Message.CAN_T_UPDATE_USER_S.formatted(userTo)));
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.delete(id)) {
            throw new AppException(Message.USER_WITH_ID_D_NOT_FOUND.formatted(id));
        }
    }

    @Override
    public Collection<UserTo> getAll() {
        return userRepository.getAll()
                .stream()
                .map(Dto.MAPPER::from)
                .toList();
    }
}
