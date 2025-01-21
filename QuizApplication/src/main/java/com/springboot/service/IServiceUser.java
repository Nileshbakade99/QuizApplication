package com.springboot.service;

import java.util.List;

import com.springboot.entity.Question;
import com.springboot.entity.User;

public interface IServiceUser {

	public User saveUser(User user, String url);
	
	public User saveEmail(User update);
	
	public List<Question> getQuestion(String catagory);
	
	public User getUserDeatails(String email);
	
	public List<User> getUserDetailsData();
	
	public void sendEmail(User user, String url);
	
	public boolean getVerify(String verificationCode);
	
	public String getForgetPassword(String email, String url);
	
	public void resetSendEmail(User user,String url);
	
	public User getByResetPasswordExpire(String token);
	
	public void resetPassword(User user, String newPassword);
}
