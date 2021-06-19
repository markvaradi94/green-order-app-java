package ro.asis.order.service.java.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.asis.order.service.java.model.entity.OrderEntity;
import ro.asis.order.service.java.service.OrderService;

import static java.util.Collections.emptyList;
import static ro.asis.commons.enums.OrderStatus.COMPLETED;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final OrderService service;

    @Override
    public void run(String... args) throws Exception {
        service.addOrder(
                OrderEntity.builder()
                        .clientId("1234test")
                        .providerId("56345test")
                        .status(COMPLETED)
                        .bags(emptyList())
                        .totalPrice(289.34)
                        .build()
        );
    }
}
