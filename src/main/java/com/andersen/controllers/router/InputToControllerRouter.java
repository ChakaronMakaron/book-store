package com.andersen.controllers.router;


import com.andersen.App;
import com.andersen.controllers.BookController;
import com.andersen.controllers.OrderController;
import com.andersen.controllers.RequestController;
import com.andersen.enums.AppCommand;
import com.andersen.enums.actions.BookAction;
import com.andersen.enums.actions.OrderAction;
import com.andersen.enums.actions.RequestAction;
import com.andersen.models.ParsedInput;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


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
            Long bookId = Long.parseLong(input.getArgs()[0]);
            int amount = Integer.parseInt(input.getArgs()[1]);
            bookController.add(bookId, amount);
        });

        // Book list
        router.put(new ParsedInput(AppCommand.BOOK, BookAction.LIST), input -> {
            if (input.getArgs().length == 0) {
                bookController.list("natural");
            } else {
                String sortKey = input.getArgs()[0];
                bookController.list(sortKey);
            }
        });

        // Order list
        router.put(new ParsedInput(AppCommand.ORDER, OrderAction.LIST), input -> {
            if (input.getArgs().length == 0) {
                orderController.list("natural");
            } else {
                String sortKey = input.getArgs()[0];
                orderController.list(sortKey);
            }
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
            if (input.getArgs().length == 0) {
                requestController.list("natural");
            } else {
                String sortKey = input.getArgs()[0];
                requestController.list(sortKey);
            }
        });


        //Get total income from orders for a certain period
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd['T'HH:mm]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .toFormatter();

        router.put(new ParsedInput(AppCommand.ORDER, OrderAction.TOTAL_INCOME), input -> {
            LocalDateTime startIncomeDate = LocalDateTime.parse(input.getArgs()[0], formatter);
            LocalDateTime endIncomeDate = LocalDateTime.parse(input.getArgs()[1], formatter);
            System.out.println("PERIOD FROM " + startIncomeDate);
            System.out.println("TO " + endIncomeDate);
            orderController.countIncome(startIncomeDate, endIncomeDate);
        });


        // Exit
        router.put(new ParsedInput(AppCommand.EXIT), input -> {
            System.exit(0);
        });

        // Help
        router.put(new ParsedInput(AppCommand.HELP), input -> {
            App.getInstance().help();
        });

    }
}
