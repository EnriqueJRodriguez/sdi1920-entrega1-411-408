package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {

	@Autowired
	private UsersService usersService;

	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private InvitationService invitationService;

	@PostConstruct
	public void init() {
		// Standard Users
		User user1 = new User("max@arcadia.com", "Max", "Caulfield");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[0]);

		User user2 = new User("pepitosdi@uniovi.es", "Pepito", "Martínez");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);

		User user3 = new User("jaimitosdi@uniovi.es", "Jaimito", "González");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);

		// Admin
		User user4 = new User("juansdi@uniovi.es", "Juan", "Pérez");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[1]);

		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		
		// Invitations
		Invitation invitationMaxPepito = new Invitation();
		invitationMaxPepito.setSender(user1); // Max sends the invitation
		invitationMaxPepito.setReceiver(user2); // Pepito receives the invitation
		invitationService.addInvitation(invitationMaxPepito); // We create the invitation
	}

}
