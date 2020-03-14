package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?1) "
			+ "OR LOWER(u.email) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?1))")
	Page<User> findByNamesOrEmailUser(Pageable pageable, String searchtext);
	
	Page<User> findAll(Pageable pageable);
}
