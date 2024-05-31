package com.prankurs.ecommerceapplication.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class OrdersByUser
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private LocalUser user;

	@ManyToOne(optional = false)
	@JoinColumn(name = "address_id", nullable = false)
	private Address addresses;

	@OneToMany(mappedBy = "orders", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<OrdersQuantity> ordersQuantities = new ArrayList<>();

	public OrdersByUser(LocalUser user, Address addresses, List<OrdersQuantity> ordersQuantities)
	{
		super();
		this.user = user;
		this.addresses = addresses;
		this.ordersQuantities = ordersQuantities;
	}

	public OrdersByUser()
	{
		super();
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public LocalUser getUser()
	{
		return user;
	}

	public void setUser(LocalUser user)
	{
		this.user = user;
	}

	public List<OrdersQuantity> getOrdersQuantities()
	{
		return ordersQuantities;
	}

	public void setOrdersQuantities(List<OrdersQuantity> ordersQuantities)
	{
		this.ordersQuantities = ordersQuantities;
	}

	public Address getAddresses()
	{
		return addresses;
	}

	@Override
	public String toString()
	{
		return "Orders [id=" + id + ", user=" + user + ", addresses=" + addresses + ", ordersQuantities="
				+ ordersQuantities + "]";
	}

}
