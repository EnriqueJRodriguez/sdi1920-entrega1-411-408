package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Invitation;

public interface InvitationRepository extends CrudRepository<Invitation, Long> {
	
	Page<Invitation> findAll(Pageable pageable);
	
}
