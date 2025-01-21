package com.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springboot.entity.Question;
import com.springboot.repository.RepoQuestion;

@Service
public class ServiceQuestion implements IServiceQuestion {

	@Autowired
	public RepoQuestion repoQues;

	@Override
	public Question saveQuestion(Question question) {
		return repoQues.save(question);
	}

	@Override
	public List<Question> showQuetions(String catagory) {
		List<Question> list = repoQues.findByCatagory(catagory);
		return list;
	}

	@Override
	public String deleteQuestion(Integer id) {
		repoQues.deleteById(id);
		return "Question Remove Successfully!!";
	}

	@Override
	public Question getByIdQuestion(Integer id) {
		Question result = repoQues.getReferenceById(id);
		return result;
	}

	@Override
	public Page<Question> getAdminPage(int pageno, int size, String catagory) {
		PageRequest pageRequest = PageRequest.of(pageno, size);
		Page<Question> questionPage = repoQues.findByCatagory(catagory, pageRequest);
		return questionPage;
	}
}











