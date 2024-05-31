package com.prankurs.ecommerceapplication.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.prankurs.ecommerceapplication.apiEntities.LoginBody;
import com.prankurs.ecommerceapplication.apiEntities.UserRegistrationBody;
import com.prankurs.ecommerceapplication.exceptions.EmailFailureException;
import com.prankurs.ecommerceapplication.exceptions.UserAlreadyExistsException;
import com.prankurs.ecommerceapplication.exceptions.UserNotVerifiedException;
import com.prankurs.ecommerceapplication.services.UserService;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@SpringBootTest
public class TestUserServiceTest
{

	@RegisterExtension
	private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP).withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "secret")).withPerMethodLifecycle(true);

	@Autowired
	private UserService userService;

//	public UserServiceTest(UserService userService)
//	{
//		super();
//		this.userService = userService;
//	}

	@Test
	@Transactional
	public void testRegisterUser() throws MessagingException
	{
		UserRegistrationBody body = new UserRegistrationBody();
		body.setEmail("UserServiceTest$testRegisterUser@junit.com");
		body.setFirstName("RandomFirstName");
		body.setLastName("RandomLastName");
		body.setUsername("UserA");
		body.setPassword("MySecretpassword2134");
		Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(body),
				"Username should already be in use.");

		body.setUsername("UserServiceTest$testRegisterUser");
		body.setEmail("UserA@junit.com");
		Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(body),
				"Email should already be in use.");

		body.setEmail("UserServiceTest$testRegisterUser@junit.com");
		Assertions.assertDoesNotThrow(() -> userService.saveUser(body), "user should registersuccessfully.");
		Assertions.assertEquals(body.getEmail(),
				greenMailExtension.getReceivedMessages()[0].getRecipients(Message.RecipientType.TO)[0].toString());
	}

	@Test
	@Transactional
	public void testLoginuser() throws UserNotVerifiedException, EmailFailureException
	{
		LoginBody body = new LoginBody();
		body.setUsername("UserA_incorrect");
		body.setPassword("PasswordA123_incorrect");
		Assertions.assertNull(userService.userLogin(body), "The user should not exist.");

		body.setUsername("UserA");
		Assertions.assertNull(userService.userLogin(body), "The user Password should be correct.");

		body.setPassword("PasswordA123");
		Assertions.assertNotNull(userService.userLogin(body), "The user email should logged in successfully.");

		body.setUsername("UserB");
		body.setPassword("PasswordB123");
		try
		{
			userService.userLogin(body);
			Assertions.assertTrue(false, "User should not be verified.");
		} catch (UserNotVerifiedException e)
		{
			Assertions.assertTrue(e.isNewEmailSent(), "Email verification should be sent.");
			Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
		}

		try
		{
			userService.userLogin(body);
			Assertions.assertTrue(false, "User should not be verified.");
		} catch (UserNotVerifiedException e)
		{
			Assertions.assertFalse(e.isNewEmailSent(), "Email verification should not be resent.");
			Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
		}
	}


}
