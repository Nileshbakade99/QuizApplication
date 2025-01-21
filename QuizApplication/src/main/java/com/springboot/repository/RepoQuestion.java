package com.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entity.Question;

@Repository
public interface RepoQuestion extends JpaRepository<Question, Integer>{

	List<Question> findByCatagory(String catagory);
	
	Page<Question> findByCatagory(String category, Pageable pageable);
}
