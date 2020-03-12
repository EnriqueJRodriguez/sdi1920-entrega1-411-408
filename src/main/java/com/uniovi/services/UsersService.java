package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}

	public List<User> getUsersByNamesOrEmailUser(String searchText, User user) {
		searchText = "%" + searchText + "%";
		List<User> users = usersRepository.findByNamesOrEmailUser(searchText);
		return users
				.stream()
				.filter(u -> u.getRole().equals(rolesService.getRoles()[0]))
				.filter(u -> !u.getEmail().equals(user.getEmail()))
				.collect(Collectors.toList());
	}

	public List<User> getUsersForListing(User user) {
		return getUsers()
				.stream()
				.filter(u -> u.getRole().equals(rolesService.getRoles()[0]))
				.filter(us -> !us.getEmail().equals(user.getEmail()))
				.collect(Collectors.toList());
	}

	public void createUserInvitation(Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersRepository.findById(id).get();
		User activeUser = usersRepository.findByEmail(email);
		Invitation invitation = new Invitation();
		invitation.setSender(activeUser);
		invitation.setReceiver(user);
		invitationService.addInvitation(invitation);
	}
}
