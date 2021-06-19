package ro.asis.order.service.java.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.asis.commons.exceptions.ResourceNotFoundException;
import ro.asis.commons.filters.OrderFilters;
import ro.asis.order.service.java.model.entity.OrderEntity;
import ro.asis.order.service.java.repository.OrderDao;
import ro.asis.order.service.java.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDao dao;
    private final ObjectMapper mapper;
    private final OrderRepository repository;

    public List<OrderEntity> findAllOrders(OrderFilters filters) {
        return dao.findAllOrders(filters);
    }

    public Optional<OrderEntity> findOrder(String orderId) {
        return repository.findById(orderId);
    }

    public OrderEntity addOrder(OrderEntity order) {
        order.setId(null);
        OrderEntity dbOrder = repository.save(order);
        return dbOrder;
    }

    public Optional<OrderEntity> deleteOrder(String orderId) {
        Optional<OrderEntity> orderToDelete = repository.findById(orderId);
        orderToDelete.ifPresent(this::deleteExistingOrder);
        return orderToDelete;
    }

    @SneakyThrows
    public OrderEntity patchOrder(String orderId, JsonPatch patch) {
        OrderEntity dbOrder = getOrThrow(orderId);
        JsonNode patchedOrderJson = patch.apply(mapper.valueToTree(dbOrder));
        OrderEntity patchedOrder = mapper.treeToValue(patchedOrderJson, OrderEntity.class);
        copyOrder(patchedOrder, dbOrder);
        return repository.save(dbOrder);
    }

    private void copyOrder(OrderEntity newOrder, OrderEntity dbOrder) {
        log.info("Copying order: " + newOrder);
        dbOrder.setBags(newOrder.getBags());
        dbOrder.setStatus(newOrder.getStatus());
        dbOrder.setTotalPrice(newOrder.getTotalPrice());
    }

    private void deleteExistingOrder(OrderEntity orderEntity) {
        log.info("Deleting order: " + orderEntity);
        repository.delete(orderEntity);
    }

    private OrderEntity getOrThrow(String orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find order with id " + orderId));
    }
}
