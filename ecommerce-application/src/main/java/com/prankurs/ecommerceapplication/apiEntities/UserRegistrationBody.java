package com.prankurs.ecommerceapplication.apiEntities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * The information required to register a user.
 */
public class UserRegistrationBody
{
	/** The username. */
	@NotNull
	@NotBlank
	@Size(min = 4, max = 20)
	private String username;

	/** The email. */
	@Email
	@NotNull
	@NotBlank
	private String email;

	/** The password. */
	@NotNull
	@NotBlank
	@Size(min = 5, max = 20)
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
	private String password;

	/** The first name. */
	@NotNull
	@NotBlank
	private String firstName;

	/** The last name. */
	@NotNull
	@NotBlank
	private String lastName;

	protected UserRegistrationBody(String username, String email, String password, String firstName, String lastName)
	{
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public UserRegistrationBody()
	{
		// TODO Auto-generated constructor stub
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
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
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

}
