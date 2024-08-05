package dev.khaled.btgpactual.orderms.listener.dto;

import dev.khaled.btgpactual.orderms.entity.OrderEntity;
import dev.khaled.btgpactual.orderms.entity.OrderItem;

import java.util.List;

public record OrderCreatedEvent(
        Long codigoPedido,
        Long codigoCliente,
        List<OrderItemEvent> itens
) {
    public OrderEntity toEntity() {
        OrderEntity entity = new OrderEntity();

        entity.setId(codigoPedido);
        entity.setCustomerId(codigoCliente);
        entity.setItems(getOrderItems());

        return entity;
    }

    private List<OrderItem> getOrderItems() {
        return itens.stream()
                .map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco()))
                .toList();
    }
}
