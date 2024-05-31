package com.prankurs.ecommerceapplication.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.prankurs.ecommerceapplication.dao.LocalUserDao;
import com.prankurs.ecommerceapplication.entities.LocalUser;
import com.prankurs.ecommerceapplication.services.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for decoding a JWT in the Authorization header and loading the user
 * object into the authentication context.
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter
{
	/** The JWT Service. */
	private JWTService jwtService;

	/** The Local User DAO. */
	private LocalUserDao localUserDao;

	/**
	 * Constructor for spring injection.
	 *
	 * @param jwtService
	 * @param localUserDAO
	 */
	public JWTRequestFilter(JWTService jwtService, LocalUserDao localUserDao)
	{
		super();
		this.jwtService = jwtService;
		this.localUserDao = localUserDao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		String headerToken = request.getHeader("Authorization");
		if (headerToken != null && headerToken.startsWith("Bearer "))
		{
			String token = headerToken.substring(7);

			try
			{
				String username = jwtService.getUsernameFromToken(token);
				Optional<LocalUser> opUser = localUserDao.findByUsernameIgnoreCase(username);
				if (opUser.isPresent())
				{
					LocalUser user = opUser.get();
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							user, null, new ArrayList());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			} catch (JWTDecodeException e)
			{
			}
		}
		filterChain.doFilter(request, response);

	}

}

