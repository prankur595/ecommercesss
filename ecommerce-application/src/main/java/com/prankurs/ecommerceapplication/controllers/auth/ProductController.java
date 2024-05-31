package com.prankurs.ecommerceapplication.controllers.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prankurs.ecommerceapplication.entities.Product;
import com.prankurs.ecommerceapplication.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController
{
	@Autowired
	private ProductService productService;

	public ProductController(ProductService productService)
	{
		super();
		this.productService = productService;
	}

	@GetMapping
	public List<Product> getAllProducts()
	{
		return productService.getAllProducts();
	}
}