package com.prankurs.ecommerceapplication.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prankurs.ecommerceapplication.dao.ProductDAO;
import com.prankurs.ecommerceapplication.entities.Product;

@Service
public class ProductService
{
	private ProductDAO productDAO;

	public ProductService(ProductDAO productDAO)
	{
		super();
		this.productDAO = productDAO;
	}

	public List<Product> getAllProducts()
	{
		return productDAO.findAll();
	}
}
