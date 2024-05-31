package com.prankurs.ecommerceapplication.controllers.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prankurs.ecommerceapplication.apiEntities.LoginBody;
import com.prankurs.ecommerceapplication.apiEntities.LoginReponseBody;
import com.prankurs.ecommerceapplication.apiEntities.UserRegistrationBody;
import com.prankurs.ecommerceapplication.entities.LocalUser;
import com.prankurs.ecommerceapplication.exceptions.EmailFailureException;
import com.prankurs.ecommerceapplication.exceptions.UserAlreadyExistsException;
import com.prankurs.ecommerceapplication.exceptions.UserNotVerifiedException;
import com.prankurs.ecommerceapplication.services.UserService;

import jakarta.validation.Valid;

/**
 * Rest Controller for handling authentication requests.
 */

@RestController
@RequestMapping("/auth")
public class AuthenticationController
{

	/** The user service. */
	private UserService userService;

	/**
	 * Spring injected constructor.
	 *
	 * @param userService
	 */
	public AuthenticationController(UserService userRegistrationService)
	{
		super();
		this.userService = userRegistrationService;
	}

	/**
	 * Post Mapping to handle registering users.
	 *
	 * @param registrationBody The registration information.
	 * @return Response to front end.
	 * @throws EmailFailureException
	 */
	@PostMapping("/register")
	public ResponseEntity registerUser(@Valid @RequestBody UserRegistrationBody userRegistrationBody)
			throws EmailFailureException
	{
		try
		{
			userService.saveUser(userRegistrationBody);
			return ResponseEntity.ok().build();
		} catch (UserAlreadyExistsException e)
		{
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EmailFailureException e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * { "username": "test-1", "password": "password1", "email": "test@tester.com",
	 * "firstName": "Dave", "lastName": "Tester" }
	 */

	@PostMapping("/login")
	public ResponseEntity<LoginReponseBody> loginUser(@Valid @RequestBody LoginBody loginBody)
	{
		System.out.println("sdfsdfsd");
		String jwt = null;
		try
		{
			jwt = userService.userLogin(loginBody);
		} catch (UserNotVerifiedException e)
		{
			// TODO Auto-generated catch block
			LoginReponseBody response = new LoginReponseBody();
			response.setSuccess(false);
			String reason = "USER_NOT_VERIFIED";
			if (e.isNewEmailSent())
			{
				reason += "_EMAIL_RESENT";
			}
			response.setFailureReason(reason);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		} catch (EmailFailureException e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		if (jwt == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		else
		{
			LoginReponseBody response = new LoginReponseBody(jwt);
			response.setSuccess(true);
			return ResponseEntity.ok(response);
		}

	}

	@PostMapping("/verify")
	public ResponseEntity verifyEmail(@RequestParam String token)
	{
		if (userService.verifyUser(token))
			return ResponseEntity.ok().build();
		else
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	/**
	 * Gets the profile of the currently logged-in user and returns it.
	 *
	 * @param user The authentication principal object.
	 * @return The user profile.
	 */
	@GetMapping("/me")
	public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user)
	{
		return user;
	}

}
