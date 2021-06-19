package ro.asis.order.java.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;
import ro.asis.commons.enums.OrderStatus;
import ro.asis.commons.model.GreenBag;

import java.util.List;

@Value
@Builder
@JsonDeserialize(builder = DetailedOrder.DetailedOrderBuilder.class)
public class DetailedOrder {
    String id;
    String orderId;
    String clientId;
    String providerId;
    List<GreenBag> bags;
    OrderStatus status;
    Double totalPrice;
    String clientName;
    String providerName;
}
