package com.prankurs.ecommerceapplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Address for the user to be billed/delivered to.
 */
@Entity
public class Address
{
	/** Unique id for the address. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	/** The user the address is associated with. */
	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private LocalUser users;

	/** The first line of address. */
	@Column(name = "address_line_1", nullable = false, length = 100)
	private String addressLine1;

	/** The second line of address. */
	@Column(name = "address_line_2", length = 100)
	private String addressLine2;

	/** The city of the address. */
	@Column(nullable = false)
	private String city;

	/** The country of the address. */
	@Column(nullable = false, length = 100)
	private String country;


	public Address(LocalUser users, String addressLine1, String addressLine2, String city, String country,
			long postalCode, Boolean active)
	{
		super();
		this.users = users;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.country = country;

	}

	public Address()
	{
		super();
	}

	public LocalUser getUsers()
	{
		return users;
	}

	public void setUsers(LocalUser users)
	{
		this.users = users;
	}

	public String getAddressLine1()
	{
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1)
	{
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2()
	{
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2)
	{
		this.addressLine2 = addressLine2;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}


	public long getId()
	{
		return id;
	}



}
