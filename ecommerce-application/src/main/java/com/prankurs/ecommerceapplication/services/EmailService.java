package com.prankurs.ecommerceapplication.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.prankurs.ecommerceapplication.entities.VerificationToken;
import com.prankurs.ecommerceapplication.exceptions.EmailFailureException;

/**
 * Service for handling emails being sent.
 */
@Service
public class EmailService
{
	/** The from address to use on emails. */
	@Value("${email.from}")
	private String fromAdress;

	/** The url of the front end for links. */
	@Value("${app.frontend.url}")
	private String url;

	/** The JavaMailSender instance. */
	private JavaMailSender javaMailSender;

	/**
	 * Constructor for spring injection.
	 *
	 * @param javaMailSender
	 */
	public EmailService(JavaMailSender javaMailSender)
	{
		super();
		this.javaMailSender = javaMailSender;
	}

	/**
	 * Makes a SimpleMailMessage for sending.
	 *
	 * @return The SimpleMailMessage created.
	 */
	private SimpleMailMessage makeMailMessage()
	{
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(fromAdress);
		return simpleMailMessage;
	}

	/**
	 * Sends a verification email to the user.
	 *
	 * @param verificationToken The verification token to be sent.
	 * @throws EmailFailureException Thrown if are unable to send the email.
	 */
	public void sendVerificationMail(VerificationToken verificationToken) throws EmailFailureException
	{
		SimpleMailMessage simpleMailMessage = makeMailMessage();
		simpleMailMessage.setTo(verificationToken.getUser().getEmail());
		simpleMailMessage.setSubject("Verify your email address to activatate your account");
		simpleMailMessage.setText("Please click on the link below to be redirected to the verification link: \n" + url
				+ "/auth/verify?token=" + verificationToken.getToken());
		try
		{
			javaMailSender.send(simpleMailMessage);
		} catch (MailException e)
		{
			throw new EmailFailureException();
			// TODO: handle exception
		}
	}

}
