package com.andersen.controllers.mapper;

import static com.andersen.enums.AppCommand.BOOK;
import static com.andersen.enums.AppCommand.ORDER;
import static com.andersen.enums.AppCommand.REQUEST;
import static java.util.Objects.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.andersen.controllers.BookController;
import com.andersen.controllers.OrderController;
import com.andersen.controllers.RequestController;
import com.andersen.enums.actions.BookAction;
import com.andersen.enums.actions.OrderAction;
import com.andersen.enums.actions.RequestAction;
import com.andersen.models.BookOrder;
import com.andersen.models.ParsedInput;

public class InputToControllerMapper {

    private final Map<Integer, Consumer<ParsedInput>> map;
    private final BookController bookController;
    private final OrderController orderController;
    private final RequestController requestController;

    public InputToControllerMapper(BookController bookController,
            OrderController orderController, RequestController requestController) {
        this.map = new HashMap<>();
        this.bookController = bookController;
        this.orderController = orderController;
        this.requestController = requestController;
        addMappings();
    }

    public void sendToController(ParsedInput input) {
        map.get(hash(input.getCommand().getStrValue(), input.getAction().getStrValue())).accept(input);
    }

    private void addMappings() {
        // Book add
        map.put(hash(BOOK.getStrValue(), BookAction.ADD.getStrValue()), input -> {
            String bookName = input.getArgs()[0];
            int amount = Integer.parseInt(input.getArgs()[1]);
            bookController.add(bookName, amount);
        });

        // Book list
        map.put(hash(BOOK.getStrValue(), BookAction.LIST.getStrValue()), input -> {
            String sortKey = input.getArgs()[0];
            bookController.list(sortKey);
        });

        // Order list
        map.put(hash(ORDER.getStrValue(), OrderAction.LIST.getStrValue()), input -> {
            String sortKey = input.getArgs()[0];
            orderController.list(sortKey);
        });

        // Order complete
        map.put(hash(ORDER.getStrValue(), OrderAction.COMPLETE.getStrValue()), input -> {
            Long orderId = Long.parseLong(input.getArgs()[0]);
            orderController.complete(orderId);
        });

        // Order create
        map.put(hash(ORDER.getStrValue(), OrderAction.CREATE.getStrValue()), input -> {
            orderController.create(parseArgsToBookOrders(input.getArgs()));
        });

        // Order cancel
        map.put(hash(ORDER.getStrValue(), OrderAction.CANCEL.getStrValue()), input -> {
            Long orderId = Long.parseLong(input.getArgs()[0]);
            orderController.cancel(orderId);
        });

        // Request list
        map.put(hash(REQUEST.getStrValue(), RequestAction.LIST.getStrValue()), input -> {
            String sortKey = input.getArgs()[0];
            requestController.list(sortKey);
        });
    }

    private List<BookOrder> parseArgsToBookOrders(String[] args) {
        List<BookOrder> res = new ArrayList<>();
        for (int i = 0; i < args.length; i += 2) res.add(new BookOrder(args[i], Integer.parseInt(args[i + 1])));
        return res;
    }
}
