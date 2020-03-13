package com.uniovi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.services.InvitationService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	@Autowired
	private RolesService rolesService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private InvitationService invitationService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;
	
	@RequestMapping("/user/list")
	public String getListado(Model model, @RequestParam(value = "", required=false) String searchText) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		List<Invitation> invitTo =  invitationService.getInvitationsToUser(activeUser);
		List<Invitation> invitFor = invitationService.getInvitationsForUser(activeUser);
		List<Invitation> invitations = new ArrayList<Invitation>();
		invitations.addAll(invitTo);
		invitations.addAll(invitFor);
		model.addAttribute("invitations", invitations);
		if (searchText != null && !searchText.isEmpty()) {			
			model.addAttribute("usersList", usersService.getUsersByNamesOrEmailUser(searchText, activeUser));
		} else {			
			model.addAttribute("usersList", usersService.getUsersForListing(activeUser));
			
		}		
		return "user/list";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}
	
	@RequestMapping(value = "/user/{id}/invitation/send", method = RequestMethod.GET)
	public String sendInvitation(Model model, @PathVariable Long id) {
		usersService.createUserInvitation(id);
		return "redirect:/user/list";
	}
	
	@RequestMapping("/user/list/update")
	public String updateList(Model model, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		List<Invitation> invitTo =  invitationService.getInvitationsToUser(user);
		List<Invitation> invitFor = invitationService.getInvitationsForUser(user);
		List<Invitation> invitations = new ArrayList<Invitation>();
		invitations.addAll(invitTo);
		invitations.addAll(invitFor);
		model.addAttribute("usersList", usersService.getUsersForListing(user));
		model.addAttribute("invitations", invitations);
		return "user/list :: tableusers";
	}
	
}
