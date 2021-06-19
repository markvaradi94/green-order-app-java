package ro.asis.order.service.java.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.asis.order.service.java.model.entity.OrderEntity;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {
}
