package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.User;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.UsersService;

@Controller
public class FriendshipController {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private FriendshipService friendshipService;
	
	@RequestMapping("/friend/list")
	public String getListado(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);		
		model.addAttribute("friendshipList", friendshipService.getFriendsForUser(activeUser));
		model.addAttribute("activeUser", activeUser);
		return "friend/list";
	}

}
