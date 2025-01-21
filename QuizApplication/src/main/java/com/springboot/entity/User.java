package com.springboot.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@Column(unique = true)
	private Long phoneno;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	private String role;
	
	private String gender;
	
	private String GenderCharecter;
	
	private boolean enable;
	
	private String verificationCode;
	
	private String passwordResetToken;
	
	private LocalDateTime resetTokenExpire;
}








