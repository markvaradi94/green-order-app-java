package ro.asis.order.service.java.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.asis.order.service.java.model.entity.DetailedOrderEntity;

import java.util.Optional;

public interface DetailedOrderRepository extends MongoRepository<DetailedOrderEntity, String> {
    Optional<DetailedOrderEntity> findByClientId(String clientId);
    Optional<DetailedOrderEntity> findByProviderId(String providerId);
    Optional<DetailedOrderEntity> findByOrderId(String orderId);
}
