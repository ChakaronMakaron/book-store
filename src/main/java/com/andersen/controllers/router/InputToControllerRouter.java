package com.andersen.controllers.router;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.andersen.controllers.BookController;
import com.andersen.controllers.OrderController;
import com.andersen.controllers.RequestController;
import com.andersen.enums.AppCommand;
import com.andersen.enums.actions.BookAction;
import com.andersen.enums.actions.OrderAction;
import com.andersen.enums.actions.RequestAction;
import com.andersen.models.ParsedInput;

public class InputToControllerRouter {

    private final Map<ParsedInput, Consumer<ParsedInput>> router;
    private final BookController bookController;
    private final OrderController orderController;
    private final RequestController requestController;

    public InputToControllerRouter(BookController bookController,
            OrderController orderController, RequestController requestController) {
        this.router = new HashMap<>();
        this.bookController = bookController;
        this.orderController = orderController;
        this.requestController = requestController;
        addMappings();
    }

    public void sendToController(ParsedInput input) {
        router.get(input).accept(input);
    }

    private void addMappings() {
        // Book add
        router.put(new ParsedInput(AppCommand.BOOK, BookAction.ADD), input -> {
            String bookName = input.getArgs()[0];
            int amount = Integer.parseInt(input.getArgs()[1]);
            bookController.add(bookName, amount);
        });

        // Book list
        router.put(new ParsedInput(AppCommand.BOOK, BookAction.LIST), input -> {
            String sortKey = input.getArgs()[0];
            bookController.list(sortKey);
        });

        // Order list
        router.put(new ParsedInput(AppCommand.ORDER, OrderAction.LIST), input -> {
            String sortKey = input.getArgs()[0];
            orderController.list(sortKey);
        });

        // Order complete
        router.put(new ParsedInput(AppCommand.ORDER, OrderAction.COMPLETE), input -> {
            Long orderId = Long.parseLong(input.getArgs()[0]);
            orderController.complete(orderId);
        });

        // Order create
        router.put(new ParsedInput(AppCommand.ORDER, OrderAction.CREATE), input -> {
            orderController.create();
        });

        // Order cancel
        router.put(new ParsedInput(AppCommand.ORDER, OrderAction.CANCEL), input -> {
            Long orderId = Long.parseLong(input.getArgs()[0]);
            orderController.cancel(orderId);
        });

        // Request list
        router.put(new ParsedInput(AppCommand.REQUEST, RequestAction.LIST), input -> {
            String sortKey = input.getArgs()[0];
            requestController.list(sortKey);
        });
    }
}
