package com.uniovi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	public List<Invitation> getInvitationsToUser(User user) {
		return getInvitations()
				.stream()
				.filter(i -> i.getSender().getEmail().equals(user.getEmail()))
				.collect(Collectors.toList());
	}
	
	public Map<Long,Boolean> calculateInvitationsForUser(User user, List<User> users){
		List<Invitation> invitTo =  getInvitationsToUser(user);
		List<Invitation> invitFor = getInvitationsForUser(user);
		List<Invitation> invitations = new ArrayList<Invitation>();
		invitations.addAll(invitTo);
		invitations.addAll(invitFor);
		HashMap<Long,Boolean> invitationsForUsers = new HashMap<Long, Boolean>();
		int counter = 0;
		for(User u : users) {
			for(Invitation i: invitations) {
				if(i.getReceiver().getId() == u.getId() || i.getSender().getId() == u.getId()) {
					counter++;
				}
			}
			if(counter == 0) {
				counter = 0;
				invitationsForUsers.put(u.getId(), false);
			}
			else {
				counter = 0;
				invitationsForUsers.put(u.getId(), true);
			}
		}
		return invitationsForUsers;
	}

	public void addInvitation(Invitation invitation) {
		invitationRepository.save(invitation);
	}	

}
