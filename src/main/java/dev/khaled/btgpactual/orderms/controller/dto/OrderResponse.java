package dev.khaled.btgpactual.orderms.controller.dto;

import dev.khaled.btgpactual.orderms.entity.OrderEntity;
import dev.khaled.btgpactual.orderms.repository.OrderRepository;

import java.math.BigDecimal;

public record OrderResponse(Long orderId,
                            Long customerId,
                            BigDecimal total) {

    public static OrderResponse fromEntity(OrderEntity entity) {
        return new OrderResponse(entity.getId(), entity.getCustomerId(), entity.getTotal());
    }
}
