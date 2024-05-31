package com.prankurs.ecommerceapplication.dao;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.prankurs.ecommerceapplication.entities.LocalUser;
import com.prankurs.ecommerceapplication.entities.VerificationToken;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long>
{

	Optional<VerificationToken> findByToken(String token);

	void deleteByUser(LocalUser user);

}
