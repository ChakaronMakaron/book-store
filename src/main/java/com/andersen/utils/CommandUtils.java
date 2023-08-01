package com.andersen.utils;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.andersen.enums.AppCommand;
import com.andersen.enums.actions.CommandAction;
import com.andersen.models.ParsedInput;

public class CommandUtils {

    public static ParsedInput parseInput(String input) {
        String[] inputElements = input.split(" ");

        if (inputElements.length < 1) throw new IllegalArgumentException("Too few input elements, expected at least 1");

        IntStream.range(0, inputElements.length).forEach(i -> inputElements[i] = inputElements[i].trim());

        String command = inputElements[0];
        AppCommand parsedCommand = findCommand(command);

        if (!parsedCommand.isActionable()) return new ParsedInput(parsedCommand);

        if (inputElements.length < 2) throw new IllegalArgumentException(format("Expected action for command '%s'", command));

        String action = inputElements[1];
        CommandAction parsedAction = findCommandAction(parsedCommand, action);

        String[] parsedArgs = Arrays.copyOfRange(inputElements, 2, inputElements.length);

        if (parsedArgs.length < parsedAction.getMinArgsAmount() || parsedArgs.length > parsedAction.getMaxArgsAmount())
                throw new IllegalArgumentException(format("Invalid args amount '%s' for for command '%s', action '%s'",
                        parsedArgs.length,
                        command,
                        action));

        return new ParsedInput(parsedCommand, parsedAction, parsedArgs);
    }

    private static AppCommand findCommand(String command) {
        return Arrays.stream(AppCommand.values())
                .filter(item -> item.getStrValue().equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(format("Unknown command '%s'", command)));
    }

    private static CommandAction findCommandAction(AppCommand command, String action) {
        return command.getActions().stream()
                .filter(item -> item.getStrValue().equals(action))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(format("Unknown action '%s' for command '%s'", action, command)));
    }
}
