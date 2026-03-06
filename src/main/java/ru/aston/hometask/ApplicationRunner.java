package ru.aston.hometask;

import ru.aston.hometask.context.AppData;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.config.Configuration;
import ru.aston.hometask.controller.DispatcherController;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.utils.Command;
import ru.aston.hometask.utils.Key;
import ru.aston.hometask.utils.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApplicationRunner {

    public static void main(String[] args) {
        DispatcherController dispatcherController = Configuration.createAndConfigureDispatcherController();
        AppData appData = dispatcherController.getAppData();
        printStartGreetings();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {
                    AppRequest request = AppRequest.createRequest(reader.readLine());
                    if (request.isExitRequest()) {
                        break;
                    }

                    if (request.isHelpRequest()) {
                        System.out.println(Command.HELP_INFO);
                        continue;
                    }

                    dispatcherController.send(request);
                } catch (AppException e) {
                    System.out.println(e.getMessage());
                } finally {
                    printPickedCommandAndUser(appData);
                }
            }
        } catch (IOException e) {
            System.out.println(Message.APPLICATION_TERMINATED_WITH_ERROR);
        }
    }

    private static void printPickedCommandAndUser(AppData appData) {
        String savedCommand = appData.getAttribute(Key.COMMAND, String.class);
        Object savedUser = appData.getAttribute(Key.USER);
        System.out.printf(Message.N_CURRENT_COMMAND_X_CURRENT_USER_X_N,
                savedCommand == null ? Message.NONE : savedCommand,
                savedUser == null ? Message.NONE : savedUser);
    }

    private static void printStartGreetings() {
        System.out.println(Message.GREETINGS);
    }
}
