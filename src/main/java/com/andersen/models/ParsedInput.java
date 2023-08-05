package com.andersen.models;

import com.andersen.enums.AppCommand;
import com.andersen.enums.actions.CommandAction;

import java.util.Arrays;
import java.util.Objects;

public final class ParsedInput {

    private final AppCommand command;
    private final CommandAction action;
    private final String[] args;

    public ParsedInput(AppCommand command) {
        this(command, null, null);
    }

    public ParsedInput(AppCommand command, CommandAction action) {
        this(command, action, null);
    }

    public ParsedInput(AppCommand command, CommandAction action, String[] args) {
        this.command = command;
        this.action = action;
        this.args = args;
    }

    public AppCommand getCommand() {
        return command;
    }

    public CommandAction getAction() {
        return action;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "ParsedInput [command=" + command + ", action=" + action + ", args=" + Arrays.toString(args) + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsedInput that = (ParsedInput) o;
        return command == that.command && action == that.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, action);
    }
}
