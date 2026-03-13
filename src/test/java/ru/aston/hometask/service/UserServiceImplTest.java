package ru.aston.hometask.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.mapper.Dto;
import ru.aston.hometask.repository.UserRepositoryImpl;
import ru.aston.hometask.utils.Message;
import ru.aston.hometask.utils.Validator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    static Faker faker = new Faker();
    Long id;
    String name;
    String email;
    Integer age;
    User user;
    UserTo userTo;
    @Mock
    UserRepositoryImpl userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        id = 1L;
        name = faker.name().firstName();
        email = faker.internet().emailAddress();
        age = faker.number().numberBetween(Validator.MIN_AGE, Validator.MAX_AGE);
        user = User.builder()
                .id(1L)
                .name(name)
                .email(email)
                .age(age)
                .createdAt(LocalDateTime.now())
                .build();
        userTo = Dto.MAPPER.from(user);
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallRead_verifyUserRepositoryGetIsCalledOnes() {
        when(userRepository.get(id)).thenReturn(Optional.of(user));

        UserTo actual = userService.read(id);

        verify(userRepository, only()).get(id);
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallRead_thenReturnUser() {
        when(userRepository.get(id)).thenReturn(Optional.of(user));

        UserTo actual = userService.read(id);

        assertEquals(userTo, actual);
    }

    @Test
    void givenUserRepositoryReturningEmptyOptional_whenCallRead_thenThrowAppExceptionWithActualMessage() {
        when(userRepository.get(id)).thenReturn(Optional.empty());

        AppException appException = assertThrows(AppException.class, () -> userService.read(id));
        assertEquals(Message.USER_WITH_ID_D_NOT_FOUND.formatted(id), appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallCreateWithUser_verifyUserRepositoryCreateIsCalledOnes() {
        when(userRepository.create(any(User.class))).thenReturn(Optional.of(user));

        UserTo actual = userService.create(userTo);

        verify(userRepository, only()).create(any(User.class));
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallCreateWithFields_verifyUserRepositoryCreateIsCalledOnes() {
        when(userRepository.create(any(User.class))).thenReturn(Optional.of(user));

        UserTo actual = userService.create(name, email, age);

        verify(userRepository, only()).create(any(User.class));
    }


    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallCreateWithFields_thenReturnUser() {
        when(userRepository.create(any(User.class))).thenReturn(Optional.of(user));

        UserTo actual = userService.create(name, email, age);

        assertEquals(userTo, actual);
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallCreateWithUser_thenReturnUser() {
        when(userRepository.create(any(User.class))).thenReturn(Optional.of(user));

        UserTo actual = userService.create(userTo);

        assertEquals(userTo, actual);
    }

    @Test
    void givenUserRepositoryReturningEmptyOptionalUser_whenCallCreateWithFields_thenThrowAppExceptionWithActualMessage() {
        Long id = 1L;

        when(userRepository.create(any(User.class))).thenReturn(Optional.empty());

        AppException appException = assertThrows(AppException.class, () -> userService.create(name, email, age));
        assertEquals(Message.USER_NOT_CREATED, appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningEmptyOptionalUser_whenCallCreateWithUser_thenThrowAppExceptionWithActualMessage() {
        when(userRepository.create(any(User.class))).thenReturn(Optional.empty());

        AppException appException = assertThrows(AppException.class, () -> userService.create(userTo));
        assertEquals(Message.USER_NOT_CREATED, appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallUpdateWithUser_verifyUserRepositoryUpdateIsCalledOnes() {
        when(userRepository.update(any(User.class))).thenReturn(Optional.of(user));

        UserTo actual = userService.update(userTo);

        verify(userRepository, only()).update(any(User.class));
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallUpdateWithFields_thenReturnUser() {
        when(userRepository.get(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.update(any(User.class))).thenReturn(Optional.of(user));

        UserTo actual = userService.update(id, name, email, age);

        assertEquals(userTo, actual);
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallUpdateWithName_thenReturnUser() {
        String updatingName = faker.name().firstName();
        when(userRepository.get(any(Long.class))).thenReturn(Optional.of(user));
        User updated = User.builder()
                .id(user.getId())
                .name(updatingName)
                .email(email)
                .age(age)
                .build();
        when(userRepository.update(any(User.class))).thenReturn(Optional.of(updated));

        UserTo actual = userService.update(id, updatingName, null, null);

        assertEquals(userTo.getId(), actual.getId());
        assertEquals(updatingName, actual.getName());
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallUpdateWithEmail_thenReturnUser() {
        String updatingEmail = faker.internet().emailAddress();
        when(userRepository.get(any(Long.class))).thenReturn(Optional.of(user));
        User updated = User.builder()
                .id(user.getId())
                .name(name)
                .email(updatingEmail)
                .age(age)
                .build();
        when(userRepository.update(any(User.class))).thenReturn(Optional.of(updated));

        UserTo actual = userService.update(id, null, updatingEmail, null);

        assertEquals(userTo.getId(), actual.getId());
        assertEquals(updatingEmail, actual.getEmail());
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallUpdateWithAge_thenReturnUser() {
        Integer updatingAge = faker.number().numberBetween(Validator.MIN_AGE, Validator.MAX_AGE);
        when(userRepository.get(any(Long.class))).thenReturn(Optional.of(user));
        User updated = User.builder()
                .id(user.getId())
                .name(name)
                .email(email)
                .age(updatingAge)
                .build();
        when(userRepository.update(any(User.class))).thenReturn(Optional.of(updated));

        UserTo actual = userService.update(id, null, null, updatingAge);

        assertEquals(userTo.getId(), actual.getId());
        assertEquals(updatingAge, actual.getAge());
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallUpdateWithUser_thenReturnUser() {
        when(userRepository.update(any(User.class))).thenReturn(Optional.of(user));

        UserTo actual = userService.update(userTo);

        assertEquals(userTo, actual);
    }

    @Test
    void givenUserRepositoryReturningEmptyOptionalUserForGet_whenCallUpdateWithFields_thenThrowAppExceptionWithActualMessage() {
        when(userRepository.get(any(Long.class))).thenReturn(Optional.empty());

        AppException appException = assertThrows(AppException.class, () -> userService.update(id, name, email, age));
        assertEquals(Message.USER_WITH_ID_D_NOT_FOUND.formatted(id), appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningEmptyOptionalUserForUpdate_whenCallUpdateWithFields_thenThrowAppExceptionWithActualMessage() {
        when(userRepository.get(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.update(any(User.class))).thenReturn(Optional.empty());

        AppException appException = assertThrows(AppException.class, () -> userService.update(id, name, email, age));
        assertEquals(Message.CAN_T_UPDATE_USER_S.formatted(userTo), appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningEmptyOptionalUserForUpdate_whenCallUpdateWithUser_thenThrowAppExceptionWithActualMessage() {
        when(userRepository.update(any(User.class))).thenReturn(Optional.empty());

        AppException appException = assertThrows(AppException.class, () -> userService.update(userTo));
        assertEquals(Message.CAN_T_UPDATE_USER_S.formatted(userTo), appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningFalse_whenCallDelete_thenThrowAppExceptionWithActualMessage() {
        when(userRepository.delete(any(Long.class))).thenReturn(false);

        AppException appException = assertThrows(AppException.class, () -> userService.delete(id));
        assertEquals(Message.USER_WITH_ID_D_NOT_FOUND.formatted(id), appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningFalse_whenCallDelete_thenVerifyDeleteWasCalledOnce() {
        when(userRepository.delete(any(Long.class))).thenReturn(true);

        userService.delete(id);
        verify(userRepository, only()).delete(any(Long.class));
    }

    @Test
    void givenUserRepository_whenCallGetAll_thenVerifyGetAllWasCalledOnce() {
        when(userRepository.getAll()).thenReturn(Collections.emptyList());

        Collection<UserTo> actual = userService.getAll();

        verify(userRepository, only()).getAll();
    }

    @Test
    void givenUserRepositoryReturningEmptyUsersList_whenCallGetAll_thenReturnEmptyList() {
        when(userRepository.getAll()).thenReturn(Collections.emptyList());

        Collection<UserTo> actual = userService.getAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void givenUserRepositoryReturningUsersList_whenCallGetAll_thenReturnSameSizeListContainingUsers() {
        when(userRepository.getAll()).thenReturn(List.of(user, user, user));

        Collection<UserTo> actual = userService.getAll();
        assertEquals(3, actual.size());
        assertTrue(actual.contains(userTo));
    }
}