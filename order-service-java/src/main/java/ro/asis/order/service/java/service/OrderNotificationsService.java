package ro.asis.order.service.java.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ro.asis.order.service.java.model.mappers.OrderMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderNotificationsService {
    private final OrderMapper mapper;
    private final RabbitTemplate rabbitTemplate;
}
