package ro.asis.order.service.java.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.asis.commons.exceptions.ResourceNotFoundException;
import ro.asis.commons.filters.OrderFilters;
import ro.asis.commons.model.Order;
import ro.asis.order.service.java.model.mappers.OrderMapper;
import ro.asis.order.service.java.service.OrderService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("java/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;
    private final OrderMapper orderMapper;

    @GetMapping
    List<Order> getAllOrders(OrderFilters filters) {
        return orderMapper.toApi(service.findAllOrders(filters));
    }

    @GetMapping("{orderId}")
    Order getOrder(@PathVariable String orderId) {
        return service.findOrder(orderId)
                .map(orderMapper::toApi)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find order with id " + orderId));
    }

    @PostMapping
    Order addOrder(@Valid @RequestBody Order order) {
        return orderMapper.toApi(service.addOrder(orderMapper.toEntity(order)));
    }

    @PatchMapping("{orderId}")
    Order patchOrder(@PathVariable String orderId, @RequestBody JsonPatch patch) {
        return orderMapper.toApi(service.patchOrder(orderId, patch));
    }

    @DeleteMapping("{orderId}")
    Order deleteOrder(@PathVariable String orderId) {
        return service.deleteOrder(orderId)
                .map(orderMapper::toApi)
                .orElse(null);
    }
}
