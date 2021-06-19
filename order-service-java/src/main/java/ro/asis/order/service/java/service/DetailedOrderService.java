package ro.asis.order.service.java.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.asis.client.client.ClientApiClient;
import ro.asis.client.dto.Client;
import ro.asis.commons.exceptions.ResourceNotFoundException;
import ro.asis.commons.model.Order;
import ro.asis.order.java.client.OrderApiClient;
import ro.asis.order.service.java.model.entity.DetailedOrderEntity;
import ro.asis.order.service.java.model.entity.OrderEntity;
import ro.asis.order.service.java.repository.DetailedOrderRepository;
import ro.asis.provider.client.ProviderApiClient;
import ro.asis.provider.dto.Provider;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailedOrderService {
    private final DetailedOrderRepository repository;
    private final OrderApiClient orderApiClient;
    private final ClientApiClient clientApiClient;
    private final ProviderApiClient providerApiClient;

    public DetailedOrderEntity createDetailedOrder(OrderEntity order) {
        Client client = clientApiClient.getClient(order.getClientId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Could not find client with id " + order.getClientId())
                );
        Provider provider = providerApiClient.getProvider(order.getProviderId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Could not find provider with id " + order.getProviderId())
                );

        return repository.save(
                DetailedOrderEntity.builder()
                        .orderId(order.getId())
                        .clientId(client.getId())
                        .providerId(provider.getId())
                        .clientName(client.getFirstName() + " " + client.getLastName())
                        .providerName(provider.getName())
                        .build()
        );
    }

    public DetailedOrderEntity findDetailedOrderByOrderId(String orderId) {
        return repository.findByOrderId(orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Could not find detailed order for order with id " + orderId)
                );
    }

    public DetailedOrderEntity findDetailedOrderByClientId(String clientId) {
        return repository.findByClientId(clientId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Could not find detailed order for client with id " + clientId)
                );
    }

    public DetailedOrderEntity findDetailedOrderByProviderId(String providerId) {
        return repository.findByProviderId(providerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Could not find detailed order for provider with id " + providerId)
                );
    }

    public DetailedOrderEntity findDetailedOrderByOrder(OrderEntity order) {
        return findDetailedOrderByOrderId(order.getId());
    }

    public Order findOrderForDetailedOrder(DetailedOrderEntity detailedOrder) {
        return orderApiClient.getOrder(detailedOrder.getOrderId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Could not find order with id " + detailedOrder.getOrderId())
                );
    }

    public Client findClientForDetailedOrder(DetailedOrderEntity detailedOrder) {
        return clientApiClient.getClient(detailedOrder.getClientId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Could not find client with id " + detailedOrder.getClientId())
                );
    }

    public Provider findProviderForDetailedOrder(DetailedOrderEntity detailedOrder) {
        return providerApiClient.getProvider(detailedOrder.getProviderId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Could not find provider with id " + detailedOrder.getProviderId())
                );
    }
}
