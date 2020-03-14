package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.User;
import com.uniovi.services.InvitationService;
import com.uniovi.services.UsersService;

@Controller
public class InvitationsController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private InvitationService invitationService;

	@RequestMapping("/invitation/list")
	public String getListado(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		model.addAttribute("invitationsList", invitationService.getInvitationsForUser(activeUser));
		return "invitation/list";
	}

	@RequestMapping("/invitation/list/update")
	public String updateList(Model model, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		model.addAttribute("invitationsList", invitationService.getInvitationsForUser(user));
		return "invitation/list :: tableinvitations";
	}
	
	@RequestMapping(value = "/invitation/{id}/accept", method = RequestMethod.GET)
	public String sendInvitation(Model model, @PathVariable Long id) {
		invitationService.createFriendship(id);
		return "redirect:/invitation/list";
	}

}
