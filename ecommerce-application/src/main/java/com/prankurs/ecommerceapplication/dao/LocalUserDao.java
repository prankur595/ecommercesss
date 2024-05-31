package com.prankurs.ecommerceapplication.dao;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.prankurs.ecommerceapplication.entities.LocalUser;

/**
 * Data Access Object for the LocalUser data.
 */
public interface LocalUserDao extends ListCrudRepository<LocalUser, Long>
{
	Optional<LocalUser> findByUsernameIgnoreCase(String username);

	Optional<LocalUser> findByEmailIgnoreCase(String email);
}
