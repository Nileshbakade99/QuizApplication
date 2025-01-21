package com.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.springboot.entity.User;
import com.springboot.repository.RepoUser;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	public RepoUser repoUser;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User uu = repoUser.findByEmail(username);
		if(uu==null) {
			throw new UsernameNotFoundException("User name not found!!");
		} else {
			return new CustomUser(uu);
		}
	}
}
