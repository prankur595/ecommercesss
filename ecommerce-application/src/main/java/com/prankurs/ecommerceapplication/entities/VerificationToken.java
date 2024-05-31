package com.prankurs.ecommerceapplication.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class VerificationToken
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	@Lob
	@Column(nullable = false, unique = true)
	private String token;

	@Column(nullable = false)
	private Timestamp creationTimestamp;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private LocalUser user;

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public Timestamp getCreationTimestamp()
	{
		return creationTimestamp;
	}

	public void setCreationTimestamp(Timestamp creationTimestamp)
	{
		this.creationTimestamp = creationTimestamp;
	}

	public long getId()
	{
		return id;
	}

	public LocalUser getUser()
	{
		return user;
	}

	public void setUser(LocalUser user)
	{
		this.user = user;
	}

}
