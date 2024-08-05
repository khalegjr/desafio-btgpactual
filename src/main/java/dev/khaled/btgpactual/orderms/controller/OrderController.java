package dev.khaled.btgpactual.orderms.controller;

import dev.khaled.btgpactual.orderms.controller.dto.ApiResponse;
import dev.khaled.btgpactual.orderms.controller.dto.OrderResponse;
import dev.khaled.btgpactual.orderms.controller.dto.PaginationResponse;
import dev.khaled.btgpactual.orderms.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable("customerId") Long customerId,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        BigDecimal totalOnOrders = orderService.findTotalOnOrdersByCustomersId(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnOrders", totalOnOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }
}
