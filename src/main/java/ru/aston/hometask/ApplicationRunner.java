package ru.aston.hometask;

import lombok.extern.slf4j.Slf4j;
import ru.aston.hometask.config.Configuration;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.controller.DispatcherController;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.exception.DatabaseException;
import ru.aston.hometask.utils.Command;
import ru.aston.hometask.utils.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
public class ApplicationRunner {

    public static void main(String[] args) {
        log.info("Application starting...");
        try (Configuration configuration = new Configuration();
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            DispatcherController dispatcherController = configuration.configureApplication();
            log.info("Application configuration finished.");
            System.out.println(Message.GREETINGS);

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
                    log.info(e.getMessage());
                    System.out.println(e.getMessage());
                }
            }

            log.info("Application finished successfully.");
        } catch (DatabaseException e) {
            log.error("Application terminated with database exception: {}", e.getMessage());
            System.out.printf(e.getMessage());
        } catch (Exception e) {
            log.error("Application terminated unexpectedly: {}", e.getMessage());
            System.out.printf(Message.APPLICATION_TERMINATED_WITH_ERROR_X_N.formatted(e.getMessage()));
        }
    }
}
