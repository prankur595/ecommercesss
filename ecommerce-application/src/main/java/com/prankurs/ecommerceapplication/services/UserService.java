package com.prankurs.ecommerceapplication.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.prankurs.ecommerceapplication.apiEntities.LoginBody;
import com.prankurs.ecommerceapplication.apiEntities.UserRegistrationBody;
import com.prankurs.ecommerceapplication.dao.LocalUserDao;
import com.prankurs.ecommerceapplication.dao.VerificationTokenDAO;
import com.prankurs.ecommerceapplication.entities.LocalUser;
import com.prankurs.ecommerceapplication.entities.VerificationToken;
import com.prankurs.ecommerceapplication.exceptions.EmailFailureException;
import com.prankurs.ecommerceapplication.exceptions.UserAlreadyExistsException;
import com.prankurs.ecommerceapplication.exceptions.UserNotVerifiedException;

import jakarta.transaction.Transactional;

/**
 * Service for handling user actions.
 */
@Service
public class UserService
{
	/** The LocalUserDAO. */
	private LocalUserDao localUserDao;

	/** The Reference to VerificationTokenDAO */
	private VerificationTokenDAO verificationTokenDAO;

	/** The Reference to EncryptService */
	private EncryptorService encryptorService;

	/** The Reference to JWTService */
	private JWTService jwtService;

	private EmailService emailService;

	/**
	 * Constructor injected by spring.
	 *
	 * @param localUserDAO
	 * @param encryptionService
	 * @param jwtService
	 */
	public UserService(LocalUserDao localUserDao, VerificationTokenDAO verificationTokenDAO,
			EncryptorService encryptorService, JWTService jwtService, EmailService emailService)
	{
		super();
		this.localUserDao = localUserDao;
		this.verificationTokenDAO = verificationTokenDAO;
		this.encryptorService = encryptorService;
		this.jwtService = jwtService;
		this.emailService = emailService;
	}

	public UserService()
	{
		super();
	}

	/**
	 * Attempts to register a user given the information provided.
	 *
	 * @param registrationBody The registration information.
	 * @return The local user that has been written to the database.
	 * @throws UserAlreadyExistsException Thrown if there is already a user with the
	 *                                    given information.
	 * @throws EmailFailureException
	 */
	public LocalUser saveUser(UserRegistrationBody userRegistrationBody)
			throws UserAlreadyExistsException, EmailFailureException
	{
		if (localUserDao.findByEmailIgnoreCase(userRegistrationBody.getEmail()).isPresent()
				|| localUserDao.findByUsernameIgnoreCase(userRegistrationBody.getUsername()).isPresent())
			throw new UserAlreadyExistsException();

		LocalUser localUser = new LocalUser(userRegistrationBody);
		localUser.setEncryptedPassword(encryptorService.encryptThePassword(userRegistrationBody.getPassword()));
		VerificationToken verificationToken = createVerificationToken(localUser);
		emailService.sendVerificationMail(verificationToken);
		// verificationTokenDAO.save(verificationToken);
		return localUserDao.save(localUser);
	}

	private VerificationToken createVerificationToken(LocalUser user)
	{
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
		verificationToken.setToken(jwtService.generateEmailVerificationToken(user));
		verificationToken.setUser(user);
		user.getVerificationTokens().add(verificationToken);
		return verificationToken;
	}

	/**
	 * Logins in a user and provides an authentication token back.
	 *
	 * @param loginBody The login request.
	 * @return The authentication token. Null if the request was invalid.
	 * @throws UserNotVerifiedException
	 * @throws EmailFailureException
	 */
	public String userLogin(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException
	{
		Optional<LocalUser> opUser = localUserDao.findByUsernameIgnoreCase(loginBody.getUsername());
		if (opUser.isPresent())
		{
			LocalUser localUser = opUser.get();
			if (encryptorService.comparePasswords(loginBody.getPassword(), localUser.getEncryptedPassword()))
			{
				if (localUser.isEmailVerified())
					return jwtService.generateJWTToken(localUser);
				else
				{
					List<VerificationToken> verificationTokens = localUser.getVerificationTokens();
					boolean resend = verificationTokens.size() == 0 || verificationTokens.get(0).getCreationTimestamp()
							.before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
					if (resend)
					{
						VerificationToken verificationToken = createVerificationToken(localUser);
						System.out.println(verificationToken);
						verificationTokenDAO.save(verificationToken);
						emailService.sendVerificationMail(verificationToken);
					}
					throw new UserNotVerifiedException(resend);
				}

			}
		}
		return null;

	}

	@Transactional
	public boolean verifyUser(String token)
	{
		Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);

		if (opToken.isPresent())
		{
			VerificationToken verificationToken = opToken.get();
			LocalUser user = verificationToken.getUser();
			if (!user.isEmailVerified())
			{
				user.setEmailVerified(true);
				localUserDao.save(user);
				verificationTokenDAO.deleteByUser(user);
				return true;
			}
		}
		return false;
	}



}
