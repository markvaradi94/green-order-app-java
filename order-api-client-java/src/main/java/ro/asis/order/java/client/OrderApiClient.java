package ro.asis.order.java.client;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ro.asis.commons.filters.OrderFilters;
import ro.asis.commons.model.Order;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.*;

@Slf4j
@Component
public class OrderApiClient {
    private final String baseUrl;
    private final RestTemplate restTemplate;

    public OrderApiClient(@Value("${java-order-service-location:NOT_DEFINED}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
    }

    public List<Order> getAllOrders(OrderFilters filters) {
        String url = buildQueriedUrl(filters);
        return restTemplate.exchange(
                url,
                GET,
                EMPTY,
                new ParameterizedTypeReference<List<Order>>() {
                }
        ).getBody();
    }

    public Optional<Order> getOrder(String orderId) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/java/orders/")
                .path(orderId)
                .toUriString();

        try {
            return ofNullable(restTemplate.getForObject(url, Order.class));
        } catch (HttpClientErrorException exception) {
            return empty();
        }
    }

    public Order addOrder(Order order) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/java/orders")
                .toUriString();

        return restTemplate.postForObject(url, order, Order.class);
    }

    public Optional<Order> patchOrder(String orderId, JsonPatch patch) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/java/orders/")
                .path(orderId)
                .toUriString();
        HttpEntity<JsonPatch> patchedOrder = new HttpEntity<>(patch);

        return ofNullable(
                restTemplate.exchange(
                        url,
                        PATCH,
                        patchedOrder,
                        Order.class
                ).getBody()
        );
    }

    public Optional<Order> deleteOrder(String orderId) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/java/orders")
                .path(orderId)
                .toUriString();

        return ofNullable(
                restTemplate.exchange(
                        url,
                        DELETE,
                        EMPTY,
                        Order.class
                ).getBody()
        );
    }

    private String buildQueriedUrl(OrderFilters filters) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/java/orders/");

        ofNullable(filters.getId())
                .ifPresent(id -> builder.queryParam("id", id));
        ofNullable(filters.getClientId())
                .ifPresent(clientId -> builder.queryParam("clientId", clientId));
        ofNullable(filters.getProviderId())
                .ifPresent(providerId -> builder.queryParam("providerId", providerId));
        ofNullable(filters.getStatus())
                .ifPresent(status -> builder.queryParam("status", status));

        return builder.toUriString();
    }
}
