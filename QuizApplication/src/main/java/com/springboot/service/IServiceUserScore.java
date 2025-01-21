package com.springboot.service;

import java.util.List;

import com.springboot.entity.UserScore;

public interface IServiceUserScore {

	public void saveScore(String email, Integer score, String catagory);
	
	public List<UserScore> gtUserScore(String catagory);
	
	public String deleteUserScore(Integer id);
}
