package com.prankurs.ecommerceapplication.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prankurs.ecommerceapplication.dao.OrdersDAO;
import com.prankurs.ecommerceapplication.entities.LocalUser;
import com.prankurs.ecommerceapplication.entities.OrdersByUser;

@Service
public class OrderService
{
	private OrdersDAO ordersByUserDAO;

	public OrderService(OrdersDAO ordersByUserDAO)
	{
		super();
		this.ordersByUserDAO = ordersByUserDAO;
	}

	public List<OrdersByUser> getAllOrdersForThisUser(LocalUser user)
	{
		return ordersByUserDAO.findByUser(user);

	}
}
