package ru.aston.hometask.config;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import com.github.javafaker.Number;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.aston.hometask.controller.DispatcherController;
import ru.aston.hometask.controller.UserController;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.repository.UserRepositoryImpl;
import ru.aston.hometask.service.UserServiceImpl;
import ru.aston.hometask.utils.Key;
import ru.aston.hometask.utils.Validator;

import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Getter
public class Configuration implements AutoCloseable {
    private SessionCreator sessionCreator;
    private UserRepositoryImpl userRepository;
    private UserServiceImpl userService;
    private DispatcherController dispatcher;
    private Faker faker;

    public DispatcherController configureApplication() {
        log.info("Configuring application...");
        ApplicationProperties applicationProperties = new ApplicationProperties();
        return configureApplication(applicationProperties);
    }

    public DispatcherController configureApplication(ApplicationProperties applicationProperties) {
        sessionCreator = new SessionCreator(applicationProperties);
        userRepository = new UserRepositoryImpl(sessionCreator);
        userService = new UserServiceImpl(userRepository);

        dispatcher = new DispatcherController();
        dispatcher.registerController(Key.USER, new UserController(userService));

        faker = new Faker();
        addRandomStartData(100);
        return dispatcher;
    }

    public void addRandomStartData(int amount) {
        log.info("Adding start data...");

        Name name = faker.name();
        Internet internet = faker.internet();
        Number number = faker.number();

        long count = Stream.generate(() -> userRepository.create(
                        User.builder()
                                .name(name.firstName())
                                .email(internet.emailAddress())
                                .age(number.numberBetween(Validator.MIN_AGE, Validator.MAX_AGE))
                                .build()))
                .limit(amount)
                .count();
        log.info("{} users added", count);
    }


    @Override
    public void close() {
        log.info("Closing resources");
        if (Objects.nonNull(sessionCreator)) {
            sessionCreator.close();
        }
    }
}
