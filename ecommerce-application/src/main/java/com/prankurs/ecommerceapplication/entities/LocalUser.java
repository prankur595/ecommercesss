package com.prankurs.ecommerceapplication.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prankurs.ecommerceapplication.apiEntities.UserRegistrationBody;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class LocalUser
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	@Column(nullable = false)
	private String username;

	@JsonIgnore
	@Column(name = "encrypted_password", length = 1000)
	private String encryptedPassword;

	@Column(length = 320, nullable = false, unique = true)
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String LastName;

	@Column(name = "email_verified", nullable = false)
	private boolean emailVerified = false;

	@JsonIgnore
	@OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Address> address = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("id desc")
	private List<VerificationToken> verificationTokens = new ArrayList<>();

	public LocalUser(String username, String encryptedPassword, String email, String firstName, String lastName,
			List<Address> address)
	{
		super();
		this.username = username;
		this.encryptedPassword = encryptedPassword;
		this.email = email;
		this.firstName = firstName;
		this.LastName = lastName;
		this.address = address;
	}

	public LocalUser(UserRegistrationBody userRegistrationBody)
	{
		super();
		this.username = userRegistrationBody.getUsername();
		this.email = userRegistrationBody.getEmail();
		this.firstName = userRegistrationBody.getFirstName();
		this.LastName = userRegistrationBody.getLastName();
	}

	public LocalUser()
	{
		super();
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getEncryptedPassword()
	{
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword)
	{
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return LastName;
	}

	public void setLastName(String lastName)
	{
		LastName = lastName;
	}

	public List<Address> getAddress()
	{
		return address;
	}

	public void setAddress(List<Address> address)
	{
		this.address = address;
	}

	public List<VerificationToken> getVerificationTokens()
	{
		return verificationTokens;
	}

	public void setVerificationTokens(List<VerificationToken> verificationTokens)
	{
		this.verificationTokens = verificationTokens;
	}

	public boolean isEmailVerified()
	{
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified)
	{
		this.emailVerified = emailVerified;
	}

	public long getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return "Users [id=" + id + ", username=" + username + ", encryptedPassword=" + encryptedPassword + ", email="
				+ email + ", firstName=" + firstName + ", LastName=" + LastName + ", address=" + address + "]";
	}

}
