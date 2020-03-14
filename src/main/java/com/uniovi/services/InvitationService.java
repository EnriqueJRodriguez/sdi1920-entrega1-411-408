package com.uniovi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.repositories.InvitationRepository;

@Service
public class InvitationService {

	@Autowired
	private InvitationRepository invitationRepository;

	@Autowired
	private FriendshipService friendshipService;

	public Page<Invitation> getInvitations(Pageable pageable) {
		return invitationRepository.findAll(pageable);
	}

	public Page<Invitation> getInvitationsForUser(Pageable pageable, User user) {
		return new PageImpl<Invitation>(getInvitations(pageable).stream()
				.filter(i -> i.getReceiver().getEmail().equals(user.getEmail())).collect(Collectors.toList()));
	}

	public Page<Invitation> getInvitationsFromUser(Pageable pageable, User user) {
		return new PageImpl<Invitation>(getInvitations(pageable).stream()
				.filter(i -> i.getSender().getEmail().equals(user.getEmail())).collect(Collectors.toList()));
	}

	public Map<Long, Boolean> calculateInvitationsForUser(Pageable pageable, User user, Page<User> users) {
		Page<Invitation> invitations = getAllInvitationsForUser(pageable, user);
		HashMap<Long, Boolean> invitationsForUsers = new HashMap<Long, Boolean>();
		return fillInvitationsMap(users, invitations, invitationsForUsers);
	}

	private Map<Long, Boolean> fillInvitationsMap(Page<User> users, Page<Invitation> invitations,
			HashMap<Long, Boolean> invitationsForUsers) {
		int counter = 0;
		for (User u : users) {
			for (Invitation i : invitations) {
				if (i.getReceiver().getId() == u.getId() || i.getSender().getId() == u.getId()) {
					counter++;
				}
			}
			if (counter == 0) {
				counter = 0;
				invitationsForUsers.put(u.getId(), false);
			} else {
				counter = 0;
				invitationsForUsers.put(u.getId(), true);
			}
		}
		return invitationsForUsers;
	}

	private Page<Invitation> getAllInvitationsForUser(Pageable pageable, User user) {
		List<Invitation> invitFrom = getInvitationsFromUser(pageable, user).toList();
		List<Invitation> invitFor = getInvitationsForUser(pageable, user).toList();
		List<Invitation> invitations = new ArrayList<Invitation>();
		invitations.addAll(invitFrom);
		invitations.addAll(invitFor);
		return new PageImpl<Invitation>(invitations);
	}

	public void addInvitation(Invitation invitation) {
		invitationRepository.save(invitation);
	}

	public void createFriendship(Long id) {
		Invitation invitation = invitationRepository.findById(id).get();
		Friendship friendship = new Friendship();
		friendship.setUser1(invitation.getSender());
		friendship.setUser2(invitation.getReceiver());
		friendshipService.addFriendship(friendship);
		invitationRepository.delete(invitation);
	}

}
