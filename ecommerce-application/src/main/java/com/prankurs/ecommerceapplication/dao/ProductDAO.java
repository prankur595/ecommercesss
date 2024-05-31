package com.prankurs.ecommerceapplication.dao;

import org.springframework.data.repository.ListCrudRepository;

import com.prankurs.ecommerceapplication.entities.Product;

public interface ProductDAO extends ListCrudRepository<Product, Long>
{

}
