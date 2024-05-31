package com.prankurs.ecommerceapplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * Inventory of a product that available for purchase.
 */
@Entity
public class Inventory
{
	/** Unique id for the inventory. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	/** The product this inventory is of. */
	@JsonIgnore
	@OneToOne(orphanRemoval = true, optional = false)
	@JoinColumn(name = "product_id", nullable = false, unique = true)
	private Product products;

	/** The quantity in stock. */
	@Column(nullable = false)
	private long inStockQuantity;

	public Inventory(long id, Product products, long inStockQuantity)
	{
		super();
		this.id = id;
		this.products = products;
		this.inStockQuantity = inStockQuantity;
	}

	public Inventory()
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

	public long getInStockQuantity()
	{
		return inStockQuantity;
	}

	/**
	 * Gets the quantity in stock.
	 *
	 * @return The quantity.
	 */
	public void setInStockQuantity(long inStockQuantity)
	{
		this.inStockQuantity = inStockQuantity;
	}

	public long getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return "Inventory [id=" + id + ", products=" + products + ", inStockQuantity=" + inStockQuantity + "]";
	}

}
