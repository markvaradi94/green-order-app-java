package ro.asis.order.java.events;

import lombok.Value;
import ro.asis.commons.enums.EventType;
import ro.asis.commons.model.Order;

@Value
public class OrderEvent {
    Order order;
    EventType type;
}
