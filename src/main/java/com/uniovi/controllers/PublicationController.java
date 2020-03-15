package com.uniovi.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.services.PublicationService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.PublicationValidator;

@Controller
public class PublicationController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private PublicationService publicationService;

	@Autowired
	private PublicationValidator publicationValidator;

	@RequestMapping("/publication/list")
	public String getListado(Model model, Principal principal, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		Page<Publication> publications = publicationService.getPublicationsForUser(pageable, activeUser);
		model.addAttribute("publicationsList", publications.getContent());
		model.addAttribute("page", publications);
		return "publication/list";
	}

	@RequestMapping(value = "/publication/add", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("publication", new Publication());
		return "publication/add";
	}

	@RequestMapping(value = "/publication/add", method = RequestMethod.POST)
	public String signup(@Validated Publication publication, BindingResult result) {
		publicationValidator.validate(publication, result);
		if (result.hasErrors()) {
			return "publication/add";
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		publication.setAuthor(activeUser);
		publication.setDate(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime()));
		publicationService.addPublication(publication);
		return "redirect:list";
	}

}
