package dev.khaled.btgpactual.orderms.controller;

import dev.khaled.btgpactual.orderms.service.OrderService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderControllerTest {
	@Test
	public void OrderController() {
		OrderService orderService = new OrderService(null, null);
		OrderController expected = new OrderController(new OrderService(null, null));
		OrderController actual = new OrderController(orderService);

		assertEquals(expected, actual);
	}
}
