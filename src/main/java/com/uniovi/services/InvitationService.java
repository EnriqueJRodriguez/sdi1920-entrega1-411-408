package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.repositories.InvitationRepository;

@Service
public class InvitationService {
	
	@Autowired
	private InvitationRepository invitationRepository;
	
	public List<Invitation> getInvitations() {
		List<Invitation> invitations = new ArrayList<Invitation>();
		invitationRepository.findAll().forEach(invitations::add);
		return invitations;
	}

	public List<Invitation> getInvitationsForUser(User user) {
		return getInvitations()
				.stream()
				.filter(i -> i.getReceiver().getEmail().equals(user.getEmail()))
				.collect(Collectors.toList());
	}

	public void addInvitation(Invitation invitation) {
		invitationRepository.save(invitation);
	}	

}
