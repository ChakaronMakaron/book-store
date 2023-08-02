package com.andersen.enums;

import java.util.Collections;
import java.util.List;

import com.andersen.enums.actions.BookAction;
import com.andersen.enums.actions.CommandAction;
import com.andersen.enums.actions.OrderAction;
import com.andersen.enums.actions.RequestAction;

public enum AppCommand {

    EXIT(false, Collections.emptyList()),
    
    BOOK(true, List.of(BookAction.LIST, BookAction.ADD)),
    REQUEST(true, List.of(RequestAction.LIST)),
    ORDER(true, List.of(OrderAction.LIST, OrderAction.CREATE, OrderAction.COMPLETE, OrderAction.CANCEL)),
    CLIENT(true, Collections.emptyList());

    private boolean isActionable;
    private List<CommandAction> actions;

    AppCommand(boolean isActionable, List<CommandAction> actions) {
        this.isActionable = isActionable;
        this.actions = actions;
    }

    public List<CommandAction> getActions() {
        return actions;
    }

    public boolean isActionable() {
        return isActionable;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
