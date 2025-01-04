package com.microservice.books_service.services;

import com.microservice.books_service.dto.BookResponse;
import com.microservice.books_service.dto.OrderRequest;
import com.microservice.books_service.dto.OrderResponse;
import com.microservice.books_service.models.Book;
import com.microservice.books_service.models.Order;
import com.microservice.books_service.repositories.BookRepository;
import com.microservice.books_service.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    @Value("${spring.kafka.topic.name}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    public void createOrder(OrderRequest orderRequest) {
        kafkaTemplate.send(topic,  "Попытка создать заказ для клиента - " + orderRequest.getClient());
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        // Проверяем, существует ли книга в базе данных
        log.info("Looking for book with ID: {}", orderRequest.getBookRequest().getId());
        var bookOptional = bookRepository.findById(orderRequest.getBookRequest().getId());
        if (bookOptional.isPresent()) {
            order.setBook(bookOptional.get());
            order.setClient(orderRequest.getClient());
            orderRepository.save(order);
            kafkaTemplate.send(topic,  "Успех");
            log.info("Order {} was created with Book", order.getId());
        } else {
            log.warn("Book with ID {} not found", orderRequest.getBookRequest().getId());
            kafkaTemplate.send(topic,  "Неудача");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }

    public List<OrderResponse> getOrdersByClientId(int clientId) {
        List<Order> orders = orderRepository.findByClient(clientId);
        kafkaTemplate.send(topic,  "Попытка получть все заказы для клиета - " + clientId);
        return orders.stream().map(this::mapToBookResponse).toList();
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::mapToBookResponse).toList();
    }

    public OrderResponse mapToBookResponse(Order order){
        Book book = order.getBook();
        return OrderResponse.builder()
                .id(order.getId())
                .bookResponse(new BookResponse(order.getBook()))
                .number(order.getOrderNumber())
                .client(order.getClient()).build();
    }
}
