package com.springboot.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springboot.entity.User;

@Component
public class UserValidator implements Validator {


	  @Override public boolean supports(Class<?> clazz) { 
		  return clazz.isAssignableFrom(User.class); }
	  
	  @Override public void validate(Object target, Errors errors) { 
		  User user = (User) target;
	  
	  if (user.getName().equals("") || user.getName().length() == 0) {
	      errors.rejectValue("name", "user.name.required"); 
	  } else if (user.getName().length() <= 6 || user.getName().length() >= 30) {
	      errors.rejectValue("name", "user.name.length"); 
	  }
	  
	  if (user.getPhoneno() == null) { 
		  errors.rejectValue("phoneno","user.phoneno.required"); 
      } 
	 
	  if (user.getEmail().equals("") || user.getEmail().length() == 0) {
	      errors.rejectValue("email", "user.email.required"); 
	  }
	 
	 if (user.getPassword() == null) { 
		 errors.rejectValue("password","user.password.required"); 
	 } 
     }
}
