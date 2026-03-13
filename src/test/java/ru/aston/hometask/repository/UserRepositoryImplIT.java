package ru.aston.hometask.repository;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import com.github.javafaker.Number;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.aston.hometask.ContainerIT;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.utils.Validator;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepositoryImplIT extends ContainerIT {
    static UserRepositoryImpl userRepository;
    static Name name;
    static Internet internet;
    static Number number;
    User testUser;

    @BeforeAll
    static void beforeAll() {
        userRepository = configuration.getUserRepository();
        Faker faker = configuration.getFaker();
        name = faker.name();
        internet = faker.internet();
        number = faker.number();
    }

    private static User getRandomUser() {
        return User.builder()
                .name(getRandomName())
                .email(getRandomEmail())
                .age(getRandomAge())
                .build();
    }

    private static String getRandomName() {
        return name.firstName();
    }

    static Stream<String> nameProvider() {
        return Stream.generate(UserRepositoryImplIT::getRandomName).limit(10);
    }

    private static String getRandomEmail() {
        return internet.emailAddress();
    }

    static Stream<String> emailProvider() {
        return Stream.generate(UserRepositoryImplIT::getRandomEmail).limit(10);
    }

    private static int getRandomAge() {
        return number.numberBetween(Validator.MIN_AGE, Validator.MAX_AGE);
    }

    static Stream<Integer> ageProvider() {
        return Stream.generate(UserRepositoryImplIT::getRandomAge).limit(10);
    }

    @BeforeEach
    void setUp() {
        testUser = getRandomUser();
        userRepository.create(testUser);
    }

    @Test
    void givenCreatedTestUser_whenGetId_thenIdNotNull() {
        assertNotNull(testUser);
        assertNotNull(testUser.getId());
        assertTrue(testUser.getId() > 0L);
    }

    @Test
    void givenValidNewUser_whenCallCreate_thenReturnPresentOptionalUserWithId() {
        User newUser = getRandomUser();

        Optional<User> optionalUser = userRepository.create(newUser);

        assertTrue(optionalUser.isPresent());
        assertNotNull(optionalUser.get().getId());
    }

    @Test
    void givenValidNewUser_whenCallCreate_thenReturnPresentOptionalWithNotNullCreatedAtField() {
        User newUser = getRandomUser();

        Optional<User> optionalUser = userRepository.create(newUser);

        assertTrue(optionalUser.isPresent());
        assertNotNull(optionalUser.get().getCreatedAt());
    }

    @Test
    void givenNullUser_whenCallCreate_thenReturnEmptyOptional() {
        User newUser = null;

        Optional<User> optionalUser = userRepository.create(newUser);

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void givenUserWithNullName_whenCallCreate_thenReturnEmptyOptional() {
        User newUser = User.builder()
                .name(null)
                .email(getRandomEmail())
                .age(getRandomAge())
                .build();

        Optional<User> optionalUser = userRepository.create(newUser);

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void givenUserWithNullEmail_whenCallCreate_thenReturnEmptyOptional() {
        User newUser = User.builder()
                .name(getRandomName())
                .email(null)
                .age(getRandomAge())
                .build();

        Optional<User> optionalUser = userRepository.create(newUser);

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void givenUserWithNullAge_whenCallCreate_thenReturnEmptyOptional() {
        User newUser = User.builder()
                .name(getRandomName())
                .email(getRandomEmail())
                .age(null)
                .build();

        Optional<User> optionalUser = userRepository.create(newUser);

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void givenCreatedUser_whenCallCreate_thenReturnEmptyOptional() {
        User createdUser = testUser;

        Optional<User> optionalUser = userRepository.create(createdUser);

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void givenUserWithId_whenCallCreate_thenReturnEmptyOptional() {
        User newUser = User.builder()
                .id(1000L)
                .name(getRandomName())
                .email(getRandomEmail())
                .age(getRandomAge())
                .build();

        Optional<User> optionalUser = userRepository.create(newUser);

        assertTrue(optionalUser.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3, 6, 12, 25, 35, 42, 4, 7})
    void givenCreatedUsersId_whenCallGet_thenReturnPresentOptionalUser(Long id) {
        Optional<User> optionalUser = userRepository.get(id);

        assertTrue(optionalUser.isPresent());
    }

    @Test
    void givenCreatedUser_whenCallGet_thenReturnPresentOptionalWithEqualUser() {
        Long id = testUser.getId();

        Optional<User> optionalUser = userRepository.get(id);

        assertTrue(optionalUser.isPresent());
        assertEquals(testUser, optionalUser.get());
    }

    @Test
    void givenNullUserId_whenCallGet_thenReturnEmptyOptionalUser() {
        Long id = null;

        assertThrows(NullPointerException.class, () -> userRepository.get(id));
    }

    @ParameterizedTest
    @MethodSource("nameProvider")
    void givenExistingUserWithNewName_whenCallUpdate_thenReturnPresentOptionalWithUpdatedName(String name) {
        testUser.setName(name);

        Optional<User> optionalUser = userRepository.update(testUser);

        assertTrue(optionalUser.isPresent());
        assertEquals(name, optionalUser.get().getName());
    }

    @ParameterizedTest
    @MethodSource("emailProvider")
    void givenExistingUserWithNewEmail_whenCallUpdate_thenReturnPresentOptionalWithUpdatedName(String email) {
        testUser.setEmail(email);

        Optional<User> optionalUser = userRepository.update(testUser);

        assertTrue(optionalUser.isPresent());
        assertEquals(email, optionalUser.get().getEmail());
    }

    @ParameterizedTest
    @MethodSource("ageProvider")
    void givenExistingUserWithNewAge_whenCallUpdate_thenReturnPresentOptionalWithUpdatedName(Integer age) {
        testUser.setAge(age);

        Optional<User> optionalUser = userRepository.update(testUser);

        assertTrue(optionalUser.isPresent());
        assertEquals(age, optionalUser.get().getAge());
    }

    @Test
    void givenNotExistingUserWithId_whenCallUpdate_thenReturnEmptyOptional() {
        User newUser = User.builder()
                .id(2000L)
                .name(getRandomName())
                .email(getRandomEmail())
                .age(getRandomAge())
                .build();

        Optional<User> optionalUser = userRepository.update(newUser);

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void givenNotExistingUserWithNullId_whenCallUpdate_thenUserObjectNotChanged() {
        User newUser = getRandomUser();

        Optional<User> optionalUser = userRepository.update(newUser);

        assertNull(newUser.getId());
    }

    @Test
    void givenCreatedTestUser_whenCallDeleteUser_thenReturnTrue() {
        boolean isDeleted = userRepository.delete(testUser);

        assertTrue(isDeleted);
    }

    @Test
    void givenNotExistingUser_whenCallDeleteUser_thenReturnFalse() {
        User newUser = User.builder()
                .id(675L)
                .name(getRandomName())
                .email(getRandomEmail())
                .age(getRandomAge())
                .build();

        boolean isDeleted = userRepository.delete(newUser);

        assertFalse(isDeleted);
    }

    @Test
    void givenCreatedTestUserId_whenCallDeleteUser_thenReturnTrue() {
        Long id = testUser.getId();

        boolean isDeleted = userRepository.delete(id);

        assertTrue(isDeleted);
    }

    @Test
    void givenNotExistingUserId_whenCallDeleteUser_thenReturnFalse() {
        Long id = 999L;

        boolean isDeleted = userRepository.delete(id);

        assertFalse(isDeleted);
    }

    @Test
    void givenUsersAlreadyCreated_whenCallGetAll_thenGetNotEmptyCollection() {
        Collection<User> users = userRepository.getAll();

        assertFalse(users.isEmpty());
    }

    @Test
    void givenTestUserCreated_whenCallGetAll_thenCollectionContainsTestUser() {
        Collection<User> users = userRepository.getAll();

        assertTrue(users.contains(testUser));
    }

    @Test
    void givenUsersAmount_whenCreateTenNewUsersAndCallGetAll_thenReturnCollectionWithTenMoreUsers() {
        int expected = 10;
        int countBefore = userRepository.getAll().size();

        Stream.generate(UserRepositoryImplIT::getRandomUser)
                .limit(expected)
                .forEach(user -> userRepository.create(user));
        int countAfter = userRepository.getAll().size();

        int actual = countAfter - countBefore;
        assertEquals(expected, actual);
    }
}