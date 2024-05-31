package com.prankurs.ecommerceapplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrdersQuantity
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product products;

	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	private OrdersByUser orders;

	@Column(nullable = false)
	private Integer quantity;

	public OrdersQuantity(Product products, OrdersByUser orders, Integer quantity)
	{
		super();
		this.products = products;
		this.orders = orders;
		this.quantity = quantity;
	}

	public OrdersQuantity()
	{
		super();
	}

	public Product getProducts()
	{
		return products;
	}

	public void setProducts(Product products)
	{
		this.products = products;
	}

	public OrdersByUser getOrders()
	{
		return orders;
	}

	public void setOrders(OrdersByUser orders)
	{
		this.orders = orders;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public long getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return "OrdersQuantity [id=" + id + ", products=" + products + ", orders=" + orders + ", quantity=" + quantity
				+ "]";
	}

}
