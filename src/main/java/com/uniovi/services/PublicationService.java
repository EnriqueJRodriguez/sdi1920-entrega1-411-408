package com.uniovi.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.repositories.PublicationRepository;

@Service
public class PublicationService {
	
	@Autowired
	private PublicationRepository publicationRepository;
	
	public Page<Publication> getPublications(Pageable pageable) {
		return publicationRepository.findAll(pageable);
	}
	
	public Page<Publication> getPublicationsForUser(Pageable pageable, User user) {
		return new PageImpl<Publication>(getPublications(pageable).stream()
				.filter(p -> p.getAuthor().getEmail().equals(user.getEmail())).collect(Collectors.toList()));
	}
	
	public void addPublication(Publication publication) {
		publicationRepository.save(publication);
	}

}
