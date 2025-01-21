package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entity.User;


@Repository
public interface RepoUser extends JpaRepository<User, Integer>{

	public User findByEmail(String email);
	
	public User findByVerificationCode(String code);
	
	public User findByPasswordResetToken(String token);
}
