package com.prankurs.ecommerceapplication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

/**
 * Configuration of the security on endpoints.
 */
@Configuration
public class WebSecurityConfig
{
	private JWTRequestFilter jwtRequestFilter;

	public WebSecurityConfig(JWTRequestFilter jwtRequestFilter)
	{
		super();
		this.jwtRequestFilter = jwtRequestFilter;
	}

	/**
	 * Filter chain to configure security.
	 *
	 * @param http The security object.
	 * @return The chain built.
	 * @throws Exception Thrown on error configuring.
	 */
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception
	{
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable());
//		http.authorizeHttpRequests().anyRequest().permitAll();
//		return http.build();

		http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
		return http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/products", "/auth/register", "/auth/login", "/auth/verify").permitAll();
			auth.anyRequest().authenticated();

		}).build();


	}

}
//auth.requestMatchers("/products").permitAll();
//auth.requestMatchers("/auth").permitAll();