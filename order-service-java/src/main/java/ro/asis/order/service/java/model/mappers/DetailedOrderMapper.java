package ro.asis.order.service.java.model.mappers;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ro.asis.client.dto.Client;
import ro.asis.commons.model.Order;
import ro.asis.commons.utils.ModelMapper;
import ro.asis.order.java.dto.DetailedOrder;
import ro.asis.order.service.java.model.entity.DetailedOrderEntity;
import ro.asis.order.service.java.service.DetailedOrderService;
import ro.asis.provider.dto.Provider;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class DetailedOrderMapper implements ModelMapper<DetailedOrder, DetailedOrderEntity> {
    private final DetailedOrderService service;


    @Override
    public DetailedOrder toApi(DetailedOrderEntity source) {
        Order order = service.findOrderForDetailedOrder(source);
        Client client = service.findClientForDetailedOrder(source);
        Provider provider = service.findProviderForDetailedOrder(source);
        return DetailedOrder.builder()
                .id(source.getId())
                .orderId(order.getId())
                .clientId(client.getId())
                .providerId(provider.getId())
                .bags(order.getBags())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .clientName(client.getFirstName() + " " + client.getLastName())
                .providerName(provider.getName())
                .build();
    }

    @Override
    public DetailedOrderEntity toEntity(DetailedOrder source) {
        return DetailedOrderEntity.builder()
                .id(source.getId())
                .orderId(source.getOrderId())
                .clientId(source.getClientId())
                .providerId(source.getProviderId())
                .clientName(source.getClientName())
                .providerName(source.getProviderName())
                .build();
    }

    @NotNull
    @Override
    public List<DetailedOrder> toApi(@NotNull Collection<? extends DetailedOrderEntity> source) {
        return source.stream()
                .map(this::toApi)
                .collect(toList());
    }

    @NotNull
    @Override
    public List<DetailedOrderEntity> toEntity(@NotNull Collection<? extends DetailedOrder> source) {
        return source.stream()
                .map(this::toEntity)
                .collect(toList());
    }
}
