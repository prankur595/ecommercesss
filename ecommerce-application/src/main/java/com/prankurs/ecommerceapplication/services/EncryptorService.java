package com.prankurs.ecommerceapplication.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

/**
 * Service for handling encryption of passwords.
 */
@Service
public class EncryptorService
{

	/** How many salt rounds should the encryption run. */
	@Value("${encryption.salt.rounds:10}")
	private int saltRounds;

	/** The salt built after construction. */
	private String salt;

	/**
	 * Post construction method.
	 */

	@PostConstruct
	public void postContruct()
	{
		salt = BCrypt.gensalt(saltRounds);
	}

	/**
	 * Encrypts the given password.
	 *
	 * @param password The plain text password.
	 * @return The encrypted password.
	 */

	public String encryptThePassword(String password)
	{
		return BCrypt.hashpw(password, salt);
	}

	/**
	 * Verifies that a password is correct.
	 *
	 * @param password The plain text password.
	 * @param hash     The encrypted password.
	 * @return True if the password is correct, false otherwise.
	 */

	public Boolean comparePasswords(String password, String encryptedPassword)
	{
		return BCrypt.checkpw(password, encryptedPassword);
	}

}
