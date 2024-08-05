package dev.khaled.btgpactual.orderms.listener;

import dev.khaled.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import dev.khaled.btgpactual.orderms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import static dev.khaled.btgpactual.orderms.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);
    private final OrderService service;

    public OrderCreatedListener(OrderService orderService) {
        service = orderService;
    }

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEvent> message) {
        logger.info("Message consumed: {}", message);

        service.save(message.getPayload());
    }
}