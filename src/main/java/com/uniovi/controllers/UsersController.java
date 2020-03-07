package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/user/add")
	public String getUser(Model model) {
		// model.addAttribute("rolesList", rolesService.getRoles());
		return "user/add";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String setUser(@ModelAttribute User user) {
		usersService.addUser(user);
		return "redirect:home";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
//		signUpFormValidator.validate(user, result);
//		if(result.hasErrors()) {
//			return "signup";
//		}
//		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
//		securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String dni = auth.getName();
//		User activeUser = usersService.getUserByDni(dni);
//		model.addAttribute("markList", activeUser.getMarks());
		return "home";
	}
}
