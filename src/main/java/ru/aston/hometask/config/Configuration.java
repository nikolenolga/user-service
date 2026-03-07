package ru.aston.hometask.config;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import com.github.javafaker.Number;
import lombok.extern.slf4j.Slf4j;
import ru.aston.hometask.controller.CreateUserController;
import ru.aston.hometask.controller.DeleteUserController;
import ru.aston.hometask.controller.DispatcherController;
import ru.aston.hometask.controller.HelpController;
import ru.aston.hometask.controller.PrintUsersController;
import ru.aston.hometask.controller.ReadUserController;
import ru.aston.hometask.controller.UpdateUserController;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.repository.UserRepository;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.utils.Command;
import ru.aston.hometask.utils.Validator;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
public class Configuration implements AutoCloseable {
    private SessionCreator sessionCreator;
    private UserRepository userRepository;
    private Faker faker;

    public DispatcherController configureApplication() {
        log.info("Configuring application...");
        ApplicationProperties applicationProperties = new ApplicationProperties();
        sessionCreator = new SessionCreator(applicationProperties);
        userRepository = new UserRepository(sessionCreator);
        UserService userService = new UserService(userRepository);

        DispatcherController dispatcher = new DispatcherController();
        dispatcher.registerController(Command.CREATE, new CreateUserController(userService));
        dispatcher.registerController(Command.READ, new ReadUserController(userService));
        dispatcher.registerController(Command.UPDATE, new UpdateUserController(userService));
        dispatcher.registerController(Command.DELETE, new DeleteUserController(userService));
        dispatcher.registerController(Command.PRINT, new PrintUsersController(userService));
        dispatcher.registerController(Command.HELP, new HelpController());

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
                                .createdAt(LocalDateTime.now())
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
