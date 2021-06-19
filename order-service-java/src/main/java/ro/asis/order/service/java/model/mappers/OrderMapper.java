package ro.asis.order.service.java.model.mappers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ro.asis.commons.model.Order;
import ro.asis.commons.utils.ModelMapper;
import ro.asis.order.service.java.model.entity.OrderEntity;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OrderMapper implements ModelMapper<Order, OrderEntity> {
    @Override
    public Order toApi(OrderEntity source) {
        return new Order(
                source.getId(),
                source.getClientId(),
                source.getProviderId(),
                source.getStatus(),
                source.getBags(),
                source.getTotalPrice()
        );
    }

    @Override
    public OrderEntity toEntity(Order source) {
        return OrderEntity.builder()
                .id(source.getId())
                .clientId(source.getClientId())
                .providerId(source.getProviderId())
                .status(source.getStatus())
                .bags(source.getBags())
                .totalPrice(source.getTotalPrice())
                .build();
    }

    @NotNull
    @Override
    public List<Order> toApi(@NotNull Collection<? extends OrderEntity> source) {
        return source.stream()
                .map(this::toApi)
                .collect(toList());
    }

    @NotNull
    @Override
    public List<OrderEntity> toEntity(@NotNull Collection<? extends Order> source) {
        return source.stream()
                .map(this::toEntity)
                .collect(toList());
    }
}
