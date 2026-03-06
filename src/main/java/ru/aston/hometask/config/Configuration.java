package ru.aston.hometask.config;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import com.github.javafaker.Number;
import ru.aston.hometask.context.AppData;
import ru.aston.hometask.controller.CreateUserController;
import ru.aston.hometask.controller.DeleteUserController;
import ru.aston.hometask.controller.DispatcherController;
import ru.aston.hometask.controller.PrintUserController;
import ru.aston.hometask.controller.ReadUserController;
import ru.aston.hometask.controller.UpdateUserController;
import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.repository.UserRepository;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.utils.Command;

public class Configuration {

    public static DispatcherController createAndConfigureDispatcherController() {
        System.out.println("Configuring application...");
        ApplicationProperties applicationProperties = new ApplicationProperties();
        SessionCreator sessionCreator = new SessionCreator(applicationProperties);
        UserRepository userRepository = new UserRepository(sessionCreator);
        UserService userService = new UserService(userRepository);

        AppData appData = new AppData();
        DispatcherController dispatcher = new DispatcherController(appData);
        dispatcher.registerController(Command.CREATE, new CreateUserController(userService));
        dispatcher.registerController(Command.READ, new ReadUserController(userService));
        dispatcher.registerController(Command.UPDATE, new UpdateUserController(userService));
        dispatcher.registerController(Command.DELETE, new DeleteUserController(userService));
        dispatcher.registerController(Command.PRINT, new PrintUserController(userService));

        addRandomStartData(userService, 100);
        return dispatcher;
    }

    public static void addRandomStartData(UserService userService, int amount) {
        System.out.println("Adding start data...");
        Faker faker = new Faker();
        Name name = faker.name();
        Internet internet = faker.internet();
        Number number = faker.number();
        for (int i = 0; i < amount; i++) {
            userService.create(name.firstName(),
                    internet.emailAddress(),
                    number.numberBetween(UserTo.MIN_AGE, UserTo.MAX_AGE));
        }
    }
}
