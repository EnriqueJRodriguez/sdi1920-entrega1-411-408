package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Component
public class LoginValidator implements Validator {
	
	@Autowired
	private UsersService usersService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Error.empty");
			
		User userbd = usersService.getUserByEmail(user.getEmail());
		if(userbd == null) {
			errors.rejectValue("password", "Error.login.match");
		}
		if (userbd != null && userbd.getPassword() != user.getPassword()) {
			errors.rejectValue("password", "Error.login.match");
		}
	}
}
