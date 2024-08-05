package dev.khaled.btgpactual.orderms.repository;

import dev.khaled.btgpactual.orderms.controller.dto.OrderResponse;
import dev.khaled.btgpactual.orderms.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
    Page<OrderEntity> findAllByCustomerId(Long customerId, PageRequest pageResquest);
}
