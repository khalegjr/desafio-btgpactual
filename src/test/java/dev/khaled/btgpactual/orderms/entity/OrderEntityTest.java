package dev.khaled.btgpactual.orderms.entity;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderEntityTest {
	@Test
	public void OrderEntity() {
		OrderEntity expected = new OrderEntity();
		OrderEntity actual = new OrderEntity();

		assertEquals(expected, actual);
	}
}
