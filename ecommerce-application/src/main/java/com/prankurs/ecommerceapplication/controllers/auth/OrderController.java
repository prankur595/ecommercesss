package com.prankurs.ecommerceapplication.controllers.auth;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prankurs.ecommerceapplication.entities.LocalUser;
import com.prankurs.ecommerceapplication.entities.OrdersByUser;
import com.prankurs.ecommerceapplication.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController
{
	private OrderService orderService;

	public OrderController(OrderService orderService)
	{
		super();
		this.orderService = orderService;
	}

	@GetMapping
	public List<OrdersByUser> getAllOrdersForUser(@AuthenticationPrincipal LocalUser user)
	{
		return orderService.getAllOrdersForThisUser(user);
	}
}
