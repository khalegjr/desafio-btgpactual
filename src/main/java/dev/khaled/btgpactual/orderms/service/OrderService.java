package dev.khaled.btgpactual.orderms.service;

import dev.khaled.btgpactual.orderms.controller.dto.OrderResponse;
import dev.khaled.btgpactual.orderms.entity.OrderEntity;
import dev.khaled.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import dev.khaled.btgpactual.orderms.repository.OrderRepository;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(OrderCreatedEvent event) {
        OrderEntity entity = event.toEntity();
        entity.setTotal(getTotal(entity));

        orderRepository.save(entity);
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageResquest){
        var orders = orderRepository.findAllByCustomerId(customerId, pageResquest);

        return orders.map(OrderResponse::fromEntity);
    }

    private BigDecimal getTotal(OrderEntity entity) {
        return entity.getItems().stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal findTotalOnOrdersByCustomersId(Long customerId){
        var aggregations = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);

        return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
    }
}
