package ro.asis.order.service.java.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ro.asis.commons.enums.OrderStatus;
import ro.asis.commons.model.GreenBag;

import java.util.List;

@Data
@Builder
@Document(collection = "java_orders")
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    private String id;

    private String clientId;
    private String providerId;
    private OrderStatus status;
    private List<GreenBag> bags;
    private Double totalPrice;
}
