package com.prankurs.ecommerceapplication.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;


@Entity
public class Product
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String shortDescription;

	private String longDescription;

	@Column(nullable = false)
	private Double price;

	@OneToOne(mappedBy = "products", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true)
	private Inventory inventory;

	public Product(String name, String shortDescription, String longDescription, Double price, Inventory inventory)
	{
		super();
		this.name = name;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.price = price;
		this.inventory = inventory;
	}

	public Product()
	{
		super();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getShortDescription()
	{
		return shortDescription;
	}

	public void setShortDescription(String shortDescription)
	{
		this.shortDescription = shortDescription;
	}

	public String getLongDescription()
	{
		return longDescription;
	}

	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	public long getId()
	{
		return id;
	}

	public Inventory getInventory()
	{
		return inventory;
	}

	public void setInventory(Inventory inventory)
	{
		this.inventory = inventory;
	}

	@Override
	public String toString()
	{
		return "Products [id=" + id + ", name=" + name + ", shortDescription=" + shortDescription + ", longDescription="
				+ longDescription + ", price=" + price + ", inventory=" + inventory + "]";
	}

}
