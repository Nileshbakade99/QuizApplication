package com.springboot.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entity.User;
import com.springboot.entity.UserScore;
import com.springboot.repository.IUserScoreRepository;
import com.springboot.repository.RepoUser;

import jakarta.transaction.Transactional;

@Service
public class ServiceUserScore implements IServiceUserScore {

	@Autowired
	public IUserScoreRepository userScoreRepo;

	@Autowired
	public RepoUser userRepo;

	@Override
	public void saveScore(String email, Integer score, String catagory) {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			UserScore userscore = new UserScore();
			userscore.setScore(score);
			userscore.setLdt(LocalDateTime.now());
			userscore.setCatagory(catagory);
			userscore.setUser(user);
			userScoreRepo.save(userscore);
		}
	}

	@Override
	public List<UserScore> gtUserScore(String catagory) {
		List<UserScore> score = userScoreRepo.findByCatagory(catagory);
		return score;
	}

	@Transactional
	@Override
	public String deleteUserScore(Integer id) {
		userScoreRepo.deleteById(id);
		return "Score Delete Successfully!";
	}
}
