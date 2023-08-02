package com.andersen.utils;

import static java.lang.String.format;

import java.util.Arrays;

import com.andersen.enums.AppCommand;
import com.andersen.enums.actions.CommandAction;
import com.andersen.models.ParsedInput;

public class InputParser {

    private static final InputParser instance = new InputParser();

    public static InputParser getInstance() {
        return instance;
    }

    private InputParser() {}

    public ParsedInput parseInput(String input) {
        String[] inputElements = input.split(" +");

        if (inputElements.length == 0) throw new IllegalArgumentException("Too few input elements, expected at least 1");

        String command = inputElements[0];
        AppCommand parsedCommand = findCommand(command);

        if (!parsedCommand.isActionable()) return new ParsedInput(parsedCommand);

        if (inputElements.length < 2) throw new IllegalArgumentException(format("Expected action for command '%s'", inputElements[0]));
        
        String action = inputElements[1];
        CommandAction parsedAction = findCommandAction(parsedCommand, action);

        String[] parsedArgs = Arrays.copyOfRange(inputElements, 2, inputElements.length);

        checkArgsCount(parsedCommand, parsedAction, parsedArgs);

        return new ParsedInput(parsedCommand, parsedAction, parsedArgs);
    }

    private void checkArgsCount(AppCommand parsedCommand, CommandAction parsedAction, String[] args) {
        if (args.length < parsedAction.getMinArgsAmount() || args.length > parsedAction.getMaxArgsAmount())
                throw new IllegalArgumentException(format("Invalid args amount '%s' for for command '%s', action '%s'",
                        args.length,
                        parsedCommand,
                        parsedAction));
    }

    private AppCommand findCommand(String command) {
        return Arrays.stream(AppCommand.values())
                .filter(item -> item.toString().equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(format("Unknown command '%s'", command)));
    }

    private CommandAction findCommandAction(AppCommand command, String action) {
        return command.getActions().stream()
                .filter(item -> item.toString().equals(action))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(format("Unknown action '%s' for command '%s'", action, command)));
    }
}
