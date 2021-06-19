package ro.asis.order.service.java.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ro.asis.commons.enums.OrderStatus;
import ro.asis.commons.filters.OrderFilters;
import ro.asis.order.service.java.model.entity.OrderEntity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class OrderDao {
    private final MongoTemplate mongo;

    public List<OrderEntity> findAllOrders(OrderFilters filters) {
        Query query = new Query();
        List<Criteria> criteria = buildCriteria(filters);

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        return mongo.find(query, OrderEntity.class);
    }

    private List<Criteria> buildCriteria(OrderFilters filters) {
        List<Criteria> criteria = new ArrayList<>();

        ofNullable(filters.getId())
                .ifPresent(id -> criteria.add(Criteria.where("id").is(id)));
        ofNullable(filters.getClientId())
                .ifPresent(clientId -> criteria.add(Criteria.where("clientId").is(clientId)));
        ofNullable(filters.getProviderId())
                .ifPresent(providerId -> criteria.add(Criteria.where("providerId").is(providerId)));
        ofNullable(filters.getStatus())
                .ifPresent(status ->
                        criteria.add(Criteria.where("status").is(OrderStatus.valueOf(status.toUpperCase()).name()))
                );
        return criteria;
    }
}
