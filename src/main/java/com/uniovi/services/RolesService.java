package com.uniovi.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
	
	String[] roles = { "ROLE_STANDARD", "ROLE_ANON", "ROLE_ADMIN" };

	public String[] getRoles() {
		return roles;
	}

}
