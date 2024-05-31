package com.prankurs.ecommerceapplication.dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.prankurs.ecommerceapplication.entities.LocalUser;
import com.prankurs.ecommerceapplication.entities.OrdersByUser;

public interface OrdersDAO extends ListCrudRepository<OrdersByUser, Long>
{
	List<OrdersByUser> findByUser(LocalUser user);
}
