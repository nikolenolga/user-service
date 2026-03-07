package ru.aston.hometask.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.mapper.Dto;
import ru.aston.hometask.repository.UserRepository;
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
class UserServiceTest {
    static Faker faker = new Faker();
    static Long id;
    static String name;
    static String email;
    static Integer age;
    static User user;
    static UserTo userTo;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @BeforeAll
    public static void beforeAll() {
        id = 1L;
        name = faker.name().fullName();
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
        /* given */
        when(userRepository.get(id)).thenReturn(Optional.of(user));
        /* when */
        UserTo actual = userService.read(id);
        /* then */
        verify(userRepository, only()).get(id);
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallRead_thenReturnUser() {
        /* given */
        when(userRepository.get(id)).thenReturn(Optional.of(user));
        /* when */
        UserTo actual = userService.read(id);
        /* then */
        assertEquals(userTo, actual);
    }

    @Test
    void givenUserRepositoryReturningEmptyOptional_whenCallRead_thenThrowAppExceptionWithActualMessage() {
        /* given */
        when(userRepository.get(id)).thenReturn(Optional.empty());
        /* when then */
        AppException appException = assertThrows(AppException.class, () -> userService.read(id));
        assertEquals(Message.USER_WITH_ID_D_NOT_FOUND.formatted(id), appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallCreateWithUser_verifyUserRepositoryCreateIsCalledOnes() {
        /* given */
        when(userRepository.create(any(User.class))).thenReturn(Optional.of(user));
        /* when */
        UserTo actual = userService.create(userTo);
        /* then */
        verify(userRepository, only()).create(any(User.class));
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallCreateWithFields_verifyUserRepositoryCreateIsCalledOnes() {
        /* given */
        when(userRepository.create(any(User.class))).thenReturn(Optional.of(user));
        /* when */
        UserTo actual = userService.create(name, email, age);
        /* then */
        verify(userRepository, only()).create(any(User.class));
    }


    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallCreateWithFields_thenReturnUser() {
        /* given */
        when(userRepository.create(any(User.class))).thenReturn(Optional.of(user));
        /* when */
        UserTo actual = userService.create(name, email, age);
        /* then */
        assertEquals(userTo, actual);
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallCreateWithUser_thenReturnUser() {
        /* given */
        when(userRepository.create(any(User.class))).thenReturn(Optional.of(user));
        /* when */
        UserTo actual = userService.create(userTo);
        /* then */
        assertEquals(userTo, actual);
    }

    @Test
    void givenUserRepositoryReturningEmptyOptionalUser_whenCallCreateWithFields_thenThrowAppExceptionWithActualMessage() {
        /* given */
        Long id = 1L;
        when(userRepository.create(any(User.class))).thenReturn(Optional.empty());
        /* when then */
        AppException appException = assertThrows(AppException.class, () -> userService.create(name, email, age));
        assertEquals(Message.USER_NOT_CREATED, appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningEmptyOptionalUser_whenCallCreateWithUser_thenThrowAppExceptionWithActualMessage() {
        /* given */
        when(userRepository.create(any(User.class))).thenReturn(Optional.empty());
        /* when then */
        AppException appException = assertThrows(AppException.class, () -> userService.create(userTo));
        assertEquals(Message.USER_NOT_CREATED, appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallUpdateWithUser_verifyUserRepositoryUpdateIsCalledOnes() {
        /* given */
        when(userRepository.update(any(User.class))).thenReturn(Optional.of(user));
        /* when */
        UserTo actual = userService.update(userTo);
        /* then */
        verify(userRepository, only()).update(any(User.class));
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallUpdateWithFields_thenReturnUser() {
        /* given */
        when(userRepository.get(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.update(any(User.class))).thenReturn(Optional.of(user));
        /* when */
        UserTo actual = userService.update(id, name, email, age);
        /* then */
        assertEquals(userTo, actual);
    }

    @Test
    void givenUserRepositoryReturningValidOptionalUser_whenCallUpdateWithUser_thenReturnUser() {
        /* given */
        when(userRepository.update(any(User.class))).thenReturn(Optional.of(user));
        /* when */
        UserTo actual = userService.update(userTo);
        /* then */
        assertEquals(userTo, actual);
    }

    @Test
    void givenUserRepositoryReturningEmptyOptionalUserForGet_whenCallUpdateWithFields_thenThrowAppExceptionWithActualMessage() {
        /* given */
        when(userRepository.get(any(Long.class))).thenReturn(Optional.empty());
        /* when then */
        AppException appException = assertThrows(AppException.class, () -> userService.update(id, name, email, age));
        assertEquals(Message.USER_WITH_ID_D_NOT_FOUND.formatted(id), appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningEmptyOptionalUserForUpdate_whenCallUpdateWithFields_thenThrowAppExceptionWithActualMessage() {
        /* given */
        when(userRepository.get(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.update(any(User.class))).thenReturn(Optional.empty());
        /* when then */
        AppException appException = assertThrows(AppException.class, () -> userService.update(id, name, email, age));
        assertEquals(Message.CAN_T_UPDATE_USER_S.formatted(userTo), appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningEmptyOptionalUserForUpdate_whenCallUpdateWithUser_thenThrowAppExceptionWithActualMessage() {
        /* given */
        when(userRepository.update(any(User.class))).thenReturn(Optional.empty());
        /* when then */
        AppException appException = assertThrows(AppException.class, () -> userService.update(userTo));
        assertEquals(Message.CAN_T_UPDATE_USER_S.formatted(userTo), appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningFalse_whenCallDelete_thenThrowAppExceptionWithActualMessage() {
        /* given */
        when(userRepository.delete(any(Long.class))).thenReturn(false);
        /* when then */
        AppException appException = assertThrows(AppException.class, () -> userService.delete(id));
        assertEquals(Message.USER_WITH_ID_D_NOT_FOUND.formatted(id), appException.getMessage());
    }

    @Test
    void givenUserRepositoryReturningFalse_whenCallDelete_thenVerifyDeleteWasCalledOnce() {
        /* given */
        when(userRepository.delete(any(Long.class))).thenReturn(true);
        /* when then */
        userService.delete(id);
        verify(userRepository, only()).delete(any(Long.class));
    }

    @Test
    void givenUserRepository_whenCallGetAll_thenVerifyGetAllWasCalledOnce() {
        /* given */
        when(userRepository.getAll()).thenReturn(Collections.emptyList());
        /* when */
        Collection<UserTo> actual = userService.getAll();
        /* then */
        verify(userRepository, only()).getAll();
    }


    @Test
    void givenUserRepositoryReturningEmptyUsersList_whenCallGetAll_thenReturnEmptyList() {
        /* given */
        when(userRepository.getAll()).thenReturn(Collections.emptyList());
        /* when then */
        Collection<UserTo> actual = userService.getAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void givenUserRepositoryReturningUsersList_whenCallGetAll_thenReturnSameSizeListContainingUsers() {
        /* given */
        when(userRepository.getAll()).thenReturn(List.of(user, user, user));
        /* when then */
        Collection<UserTo> actual = userService.getAll();
        assertEquals(3, actual.size());
        assertTrue(actual.contains(userTo));
    }
}