package com.andersen.services.impl;

import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.models.Request;
import com.andersen.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private RequestServiceImpl requestService;
    @Mock
    private BookServiceImpl bookService;

    private List<Order> orders;
    private Order order1;
    private Order order2;
    private Order order3;

    @BeforeEach
    void setUp() {
        Book book1 = new Book(2L, "Lolita", 25, 3);
        Book book2 = new Book(4L, "Don Quixote", 42, 9);

        List<Request> requests = List.of(
                new Request(1L, book1, 4, Request.RequestStatus.IN_PROCESS),
                new Request(2L, book2, 11, Request.RequestStatus.IN_PROCESS));

        order1 = new Order(1L, 1L, LocalDateTime.now().plusHours(5), Order.OrderStatus.COMPLETED, Collections.emptyList(), 67);
        order2 = new Order(2L, 2L, LocalDateTime.now().plusHours(4), Order.OrderStatus.COMPLETED, requests, 50);
        order3 = new Order(3L, 1L, null, Order.OrderStatus.IN_PROCESS, Collections.emptyList(), 65);
        orders = new ArrayList<>(List.of(order1, order2, order3));
    }



    @Test
    void givenListOfOrders_whenAddNewOrder_thenItShouldBeListed() {
        Order order = new Order(4L, 1L, LocalDateTime.now(), Order.OrderStatus.IN_PROCESS, Collections.emptyList(), 85);

        orderService.add(order);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(orderRepository, Mockito.times(1)).add(orderCaptor.capture());

        Order capturedOrder = orderCaptor.getValue();

        assertEquals(order.getId(), capturedOrder.getId());
        assertEquals(order.getClientId(), capturedOrder.getClientId());
        assertEquals(order.getStatus(), capturedOrder.getStatus());
        assertEquals(order.getCompletionDate(), capturedOrder.getCompletionDate());
        assertEquals(order.getPrice(), capturedOrder.getPrice());
    }

    @Test
    void givenOrder_whenChangeThisOrder_thenItShouldBeUpdated() {
        Order updatedOrder = new Order(3L, 1L, LocalDateTime.now().plusHours(6), Order.OrderStatus.COMPLETED, Collections.emptyList(), 67);

        Mockito.when(orderRepository.findByOrderId(updatedOrder.getId())).thenReturn(Optional.of(order3));

        orderService.save(updatedOrder);

        assertEquals(Order.OrderStatus.COMPLETED, order3.getStatus());
        assertEquals(updatedOrder.getCompletionDate(), order3.getCompletionDate());

        Mockito.verify(orderRepository, Mockito.times(1)).findByOrderId(updatedOrder.getId());
    }

    @Test
    void givenClientIdAndListOfOrders_whenGetAllClientOrders_thenItShouldReturnAllClientOrders() {
        List<Order> expectedClientOrder = List.of(order1, order3);

        Mockito.when(orderRepository.findOrdersByClientId(1L)).thenReturn(expectedClientOrder);

        List<Order> actualClientOrders = orderService.getAllClientOrders(1L, "date");

        assertEquals(2, actualClientOrders.size());
        assertEquals(expectedClientOrder, actualClientOrders);

        Mockito.verify(orderRepository, Mockito.times(1)).findOrdersByClientId(1L);
    }

    @Test
    void givenOrder_whenChangeStatusOnCompleted_thenItShouldChangeTheOrderStatus() {
        Mockito.when(orderRepository.findByOrderId(2L)).thenReturn(Optional.of(order2));

        orderService.changeStatus(2L, Order.OrderStatus.COMPLETED);

        assertEquals(Order.OrderStatus.COMPLETED, orders.get(1).getStatus());

        List<Request> orderRequests = orders.get(1).getRequests();
        assertEquals(Request.RequestStatus.INTERRUPTED, orderRequests.get(0).getRequestStatus());
        assertEquals(Request.RequestStatus.INTERRUPTED, orderRequests.get(1).getRequestStatus());

        Mockito.verify(orderRepository, Mockito.times(1)).findByOrderId(2L);
    }
    @Test
    void givenOrder_whenChangeStatusOnCanceled_thenItShouldChangeTheOrderStatus() {
        Mockito.when(orderRepository.findByOrderId(2L)).thenReturn(Optional.of(order2));

        orderService.changeStatus(2L, Order.OrderStatus.CANCELED);

        assertEquals(Order.OrderStatus.CANCELED, orders.get(1).getStatus());

        List<Request> orderRequests = orders.get(1).getRequests();
        assertEquals(Request.RequestStatus.CANCELED, orderRequests.get(0).getRequestStatus());
        assertEquals(Request.RequestStatus.CANCELED, orderRequests.get(1).getRequestStatus());

        Mockito.verify(orderRepository, Mockito.times(1)).findByOrderId(2L);
    }


    @Test
    void givenFilledOrder_whenAllRequestsInTheOrderCompleted_thenOrderShouldBeCompleted() {
        Book book1 = new Book(2L, "Lolita", 25, 3);
        Book book2 = new Book(4L, "Don Quixote", 42, 9);

        List<Request> requests = List.of(
                new Request(1L, book1, 2, Request.RequestStatus.IN_PROCESS),
                new Request(2L, book2, 5, Request.RequestStatus.IN_PROCESS));

        Order order = new Order(4L, 1L, null, Order.OrderStatus.IN_PROCESS, requests, 67);

        orderService.processOrder(order);

        assertEquals(Order.OrderStatus.COMPLETED, order.getStatus());
        assertNotNull(order.getCompletionDate());
    }

    @Test
    void givenFilledOrder_whenAtLeastOneRequestIsNotCompleted_thenOrderShouldBeInProcess() {
        Book book1 = new Book(2L, "Lolita", 25, 3);
        Book book2 = new Book(4L, "Don Quixote", 42, 9);

        List<Request> requests = List.of(
                new Request(1L, book1, 4, Request.RequestStatus.IN_PROCESS),
                new Request(2L, book2, 11, Request.RequestStatus.IN_PROCESS));

        Order order = new Order(4L, 1L, null, Order.OrderStatus.IN_PROCESS, requests, null);

        orderService.processOrder(order);

        assertEquals(Order.OrderStatus.IN_PROCESS, order.getStatus());
        assertNull(order.getCompletionDate());
    }

    @Test
    void givenUserInputForTheBook_whenInputIsCorrectAndOrderInitialized_thenItShouldAddNewRequest() {
        Book expectedBook = new Book(1L, "The Great Gatsby", 39, 5);

        Mockito.when(bookService.getBookById(1L)).thenReturn(Optional.of(expectedBook));

        orderService.processUserInput(order1, "1 2");

        Mockito.verify(bookService, Mockito.times(1)).getBookById(1L);

        assertEquals(1, order1.getRequests().size());

        Request request = order1.getRequests().get(0);
        assertEquals(expectedBook, request.getBook());
        assertEquals(2, request.getAmount());
    }

    @Test
    void givenUserInputForTheBook_whenInputIsCorrectAndOrderIsNotInitialized_thenItShouldCreateNewOrderAndAddNewRequest() {
        Book expectedBook = new Book(1L, "The Great Gatsby", 39, 5);

        Mockito.when(bookService.getBookById(1L)).thenReturn(Optional.of(expectedBook));

        Order actualOrder = new Order();
        orderService.processUserInput(actualOrder, "1 2");

        Mockito.verify(bookService, Mockito.times(1)).getBookById(1L);

        assertEquals(1, actualOrder.getRequests().size());

        Request request = actualOrder.getRequests().get(0);
        assertEquals(expectedBook, request.getBook());
        assertEquals(2, request.getAmount());
    }

    @Test
    void givenUserInputForTheBook_whenInputIsIncorrect_thenItShouldThrowAnException() {

        assertThrows(IllegalArgumentException.class, () -> orderService.processUserInput(order1, "1"), "Bad input");
        assertThrows(IllegalArgumentException.class, () -> orderService.processUserInput(order1, "a b"), "Wrong input parameters");
    }

    @Test
    void givenListOfOrders_whenFindAllCalledWithSortParameterDate_thenItShouldReturnAllOrdersSortedByDate() {
        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        String sortKey = "date";
        List<Order> sortedOrders = orderService.list(sortKey);

        orders.sort(Comparator.comparing(Order::getCompletionDate, Comparator.nullsLast(LocalDateTime::compareTo)));

        assertEquals(orders.size(), sortedOrders.size());
        assertEquals(order2, orders.get(0));
        assertEquals(order1, orders.get(1));
        assertEquals(order3, orders.get(2));
        assertEquals(orders, sortedOrders);

        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
        Mockito.verify(orderRepository, Mockito.times(1)).sort(orders, sortKey);

    }

    @Test
    void givenListOfOrders_whenFindAllCalledWithSortParameterPrice_thenItShouldReturnAllOrdersSortedByPrice() {
        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        String sortKey = "price";
        List<Order> sortedOrders = orderService.list(sortKey);

        orders.sort(Comparator.comparing(Order::getPrice));

        assertEquals(orders.size(), sortedOrders.size());
        assertEquals(order2, orders.get(0));
        assertEquals(order3, orders.get(1));
        assertEquals(order1, orders.get(2));
        assertEquals(orders, sortedOrders);

        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
        Mockito.verify(orderRepository, Mockito.times(1)).sort(orders, sortKey);

    }
    @Test
    void givenListOfOrders_whenFindAllCalledWithSortParameterStatus_thenItShouldReturnAllOrdersSortedByStatus() {
        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        String sortKey = "date";
        List<Order> sortedOrders = orderService.list(sortKey);

        orders.sort(Comparator.comparing(Order::getStatus));

        assertEquals(orders.size(), sortedOrders.size());
        assertEquals(order3, orders.get(0));
        assertEquals(order1, orders.get(1));
        assertEquals(order2, orders.get(2));
        assertEquals(orders, sortedOrders);

        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
        Mockito.verify(orderRepository, Mockito.times(1)).sort(orders, sortKey);

    }

    @Test
    void givenListOfOrders_whenFindAllCalledWithoutSortParameter_thenItShouldReturnAllOrders() {
        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        String sortKey = "date";
        List<Order> sortedOrders = orderService.list(sortKey);

        assertEquals(orders.size(), sortedOrders.size());
        assertEquals(order1, orders.get(0));
        assertEquals(order2, orders.get(1));
        assertEquals(order3, orders.get(2));
        assertEquals(orders, sortedOrders);

        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
        Mockito.verify(orderRepository, Mockito.times(1)).sort(orders, sortKey);

    }
}