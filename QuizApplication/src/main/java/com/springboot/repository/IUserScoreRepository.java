package com.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.entity.UserScore;

public interface IUserScoreRepository extends JpaRepository<UserScore, Integer>{
	List<UserScore> findByCatagory(String catagory);
	
	Optional<UserScore> findById(Integer id);
}
