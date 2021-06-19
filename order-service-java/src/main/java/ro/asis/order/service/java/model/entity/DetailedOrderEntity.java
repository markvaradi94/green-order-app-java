package ro.asis.order.service.java.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "java_detailed_orders")
@NoArgsConstructor
@AllArgsConstructor
public class DetailedOrderEntity {
    @Id
    private String id;

    private String orderId;
    private String clientId;
    private String providerId;
    private String clientName;
    private String providerName;
}
