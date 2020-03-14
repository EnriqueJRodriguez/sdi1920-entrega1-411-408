package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friendship;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

	Page<Friendship> findAll(Pageable pageable);
	
}
