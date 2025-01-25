package com.springboot.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.entity.Question;
import com.springboot.entity.User;
import com.springboot.repository.RepoQuestion;
import com.springboot.repository.RepoUser;

import jakarta.mail.internet.MimeMessage;

@Service
public class ServiceUser implements IServiceUser {

	@Autowired
	public RepoUser repoUser;

	@Autowired
	public RepoQuestion repoQues;

	@Autowired
	public BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public JavaMailSender mailSender;

	@Override
	public User saveUser(User user, String url) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		user.setRole("ROLE_USER");
		user.setEnable(false);
		user.setVerificationCode(UUID.randomUUID().toString());
		User uu = repoUser.save(user);
		if (uu != null) {
			sendEmail(uu, url);
		}
		return uu;
	}

	@Override
	public void sendEmail(User user, String url) {

		String from = "nileshbakade786@gmail.com";
		String to = user.getEmail();
		String subject = "Account verification";
		String content = "Dear [[name]],<br> " + "Please click the link below to verify your registration:<br> "
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank You,<br>" + "Quizzica Team";

		try {

			MimeMessage messgae = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(messgae);

			helper.setFrom(from, "Quizzica Team");
			helper.setTo(to);
			helper.setSubject(subject);

			content = content.replace("[[name]]", user.getName());

			String siteUrl = "http://localhost:4041" + "/verify?code=" + user.getVerificationCode();
			content = content.replace("[[URL]]", siteUrl);

			helper.setText(content, true);
			mailSender.send(messgae);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Question> getQuestion(String catagory) {
		List<Question> list = repoQues.findByCatagory(catagory);
		return list;
	}

	@Override
	public User getUserDeatails(String email) {
		return repoUser.findByEmail(email);
	}

	@Override
	public User saveEmail(User update) {
		User uu = repoUser.save(update);
		return uu;
	}

	@Override
	public List<User> getUserDetailsData() {
		List<User> user = repoUser.findAll();
		return user;
	}

	@Override
	public boolean getVerify(String verificationCode) {
		User user = repoUser.findByVerificationCode(verificationCode);
		if (user == null) {
			return false;
		} else {

			user.setEnable(true);
			user.setVerificationCode(null);
			repoUser.save(user);

			return true;
		}
	}

	@Override
	public String getForgetPassword(String email, String url) {
		User emp = repoUser.findByEmail(email);
		if (emp != null) {
			String resetPassword = UUID.randomUUID().toString();
			emp.setPasswordResetToken(resetPassword);

			LocalDateTime ldt = LocalDateTime.now().plusMinutes(15);
			emp.setResetTokenExpire(ldt);

			repoUser.save(emp);
			resetSendEmail(emp, url);
			return "Password reset link has been send to your email";
		} else {
			return "No user found with that email";
		}
	}

	@Override
	public void resetSendEmail(User user, String url) {
		String from = "nileshbakade786@gmail.com";
		String to = user.getEmail();
		String subject = "Password reset token";
		String content = "Dear [[name]]<br>"
				+ "Please click the below link to reset password (link will be valid 15 mins)<br>"
				+ "<h2><a href=\"[[URL]]\"target=\"self_\">Reset Password</a></h2>" + "This will expire in 15 mins.<br>"
				+ "Thank You<br>" + "Quizzica Team";

		try {
			MimeMessage mesage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mesage);

			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);

			content = content.replace("[[name]]", user.getName());

			String siteUrl = "http://localhost:4041" + "/reset-password?token=" + user.getPasswordResetToken();

			content = content.replace("[[URL]]", siteUrl);

			helper.setText(content, true);

			mailSender.send(mesage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public User getByResetPasswordExpire(String token) {
		User user = repoUser.findByPasswordResetToken(token);
		return user;
	}

	@Override
	public void resetPassword(User user, String newPassword) {
		String Encoderpassword = passwordEncoder.encode(newPassword);
		user.setPassword(Encoderpassword);
		
		user.setPasswordResetToken(null);
		user.setResetTokenExpire(null);
		repoUser.save(user);
	}
}













