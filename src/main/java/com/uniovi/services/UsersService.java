package com.uniovi.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

	public Page<User> getUsers(Pageable pageable) {
		return usersRepository.findAll(pageable);
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

	public Page<User> getUsersByNamesOrEmailUser(Pageable pageable, String searchText, User user) {		
		searchText = "%" + searchText + "%";
		Page<User> users = usersRepository.findByNamesOrEmailUser(pageable, searchText);
		return new PageImpl<User>(users
				.stream()
				.filter(u -> u.getRole().equals(rolesService.getRoles()[0]))
				.filter(u -> !u.getEmail().equals(user.getEmail()))
				.collect(Collectors.toList()));
	}

	public Page<User> getUsersForListing(Pageable pageable, User user) {
		return new PageImpl<User>(getUsers(pageable)
				.stream()
				.filter(u -> u.getRole().equals(rolesService.getRoles()[0]))
				.filter(us -> !us.getEmail().equals(user.getEmail()))
				.collect(Collectors.toList()));
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

	public Page<User> getUsersForListingAdmin(Pageable pageable, User user) {
		return new PageImpl<User>(getUsers(pageable)
				.stream()
				.filter(us -> !us.getEmail().equals(user.getEmail()))
				.collect(Collectors.toList()));
	}
}
