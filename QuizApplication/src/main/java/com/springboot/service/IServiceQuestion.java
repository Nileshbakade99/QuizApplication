package com.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.springboot.entity.Question;

public interface IServiceQuestion {

	public Question saveQuestion(Question question);
	
	public List<Question> showQuetions(String catagory);
	
	public String deleteQuestion(Integer id);
	
	public Question getByIdQuestion(Integer id);
	
	public Page<Question> getAdminPage(int pageno, int size, String catagory);
}
