package com.prankurs.ecommerceapplication.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.prankurs.ecommerceapplication.entities.LocalUser;

import jakarta.annotation.PostConstruct;

/**
 * Service for handling JWTs for user authentication.
 */
@Service
public class JWTService
{

	/** The secret key to encrypt the JWTs with. */
	@Value("${jwt.algorithm.key}")
	private String key;

	/** The issuer the JWT is signed with. */
	@Value("${jwt.algorithm.issuer}")
	private String issuer;

	/** How many seconds from generation should the JWT expire? */
	@Value("${jwt.algorithm.expiryInSeconds}")
	private int expiryInSeconds;

	/** The algorithm generated post construction. */
	private Algorithm algorithm;

	/** The JWT claim key for the username. */
	private final String USERNAME_KEY = "USERNAME";

	/** The JWT claim key for the Email Verification. */
	private final String EMAIL_KEY = "EMAILVERIFICATION";

	/**
	 * Post construction method.
	 */
	@PostConstruct
	public void postConstruct() {
		algorithm = Algorithm.HMAC256(key);
	}

	/**
	 * Generates a JWT based on the given user.
	 *
	 * @param user The user to generate for.
	 * @return The JWT.
	 */
	public String generateJWTToken(LocalUser user)
	{
		return JWT.create().withClaim(USERNAME_KEY, user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds))).withIssuer(issuer)
				.sign(algorithm);
	}

	/**
	 * Generates a JWT based on the given email verification.
	 *
	 * @param user The user to generate for.
	 * @return The JWT.
	 */
	public String generateEmailVerificationToken(LocalUser user)
	{
		return JWT.create().withClaim(EMAIL_KEY, user.getEmail())
				.withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds))).withIssuer(issuer)
				.sign(algorithm);
	}

	/**
	 * Gets the username out of a given JWT.
	 *
	 * @param token The JWT to decode.
	 * @return The username stored inside.
	 */
	public String getUsernameFromToken(String token)
	{
		return JWT.decode(token).getClaim(USERNAME_KEY).asString();
	}

}
