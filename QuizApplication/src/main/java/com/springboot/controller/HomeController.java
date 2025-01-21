package com.springboot.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.entity.User;
import com.springboot.repository.RepoUser;
import com.springboot.service.IServiceUser;
import com.springboot.validator.UserValidator;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	@Autowired
	private IServiceUser serviceUser;

	@Autowired
	private RepoUser repoUser;
	
	@Autowired
	private UserValidator validUser;

	@ModelAttribute
	public void commonUser(Principal p, Map<String, Object> map) {
		if (p != null) {
			String email = p.getName();
			User user = repoUser.findByEmail(email);
			map.put("user", user);
		}
	}

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/signin")
	public String loginin(@ModelAttribute User user) {
		return "signin";
	}
	
	@GetMapping("/message")
	public String messageGet() {
		return "message";
	}

	@GetMapping("/register")
	public String registerCreate(@ModelAttribute User user) {
		return "register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute ("user") User user, RedirectAttributes attr, BindingResult errors, 
			               HttpServletRequest request) {

		if(validUser.supports(user.getClass())) {
			validUser.validate(user, errors);
			if(errors.hasErrors()) {
				return "register";
			}
		}
		
		String url = request.getRequestURI().toString();
		System.out.println(url);

		url = url.replace(request.getServletPath(), "");
		System.out.println(url);
		
		if ("Male".equalsIgnoreCase(user.getGender())) {
			user.setGenderCharecter("/images/malelogo.png");
		} else {
			user.setGenderCharecter("/images/femalelogo.png");
		}

		User uu = serviceUser.saveUser(user,url);
		
		if (uu != null) {
			attr.addFlashAttribute("add", "Register successfully");
		} else {
			attr.addFlashAttribute("add", "Something problem in server");
		}
		return "redirect:signin";
	}
	
	@GetMapping("/verify")
	public String getverifyUser(@Param("code") String code, Model m) {
		boolean f = serviceUser.getVerify(code);
		if (f) {
			m.addAttribute("msg", "Sucessfully your account is verified");
		} else {
			m.addAttribute("msg", "may be your vefication code is incorrect or already veified ");
		}
		return "message";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
	
	//--------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping("/forget-password")
	public String forgetPassword(@ModelAttribute User user) {
		return "forget-password";
	}
	
	@PostMapping("/forget-password")
	public String sendforgetPassword(@ModelAttribute User user, 
			                         @RequestParam ("email") String email
			                         ,HttpServletRequest request
			                         ,RedirectAttributes attr) {
		String url = request.getRequestURI().toString();
		url = url.replace(request.getServletPath(), "");
		String uu = serviceUser.getForgetPassword(email, url);
		attr.addFlashAttribute("msg", uu);
		return "redirect:forget-password";
	}
	
	@GetMapping("/reset-password")
	public String resetPasswordUser(@Param ("token") String token,
			                        Model model) {
		User uu = serviceUser.getByResetPasswordExpire(token);
		if(uu!=null) {
			model.addAttribute("token",token);
			return "reset-password";
		} else {
			model.addAttribute("msg", "Invalid or expire token");
			return "message";
		}
	}
	
	@PostMapping("/reset-password")
	public String saveResetPassword(@RequestParam ("token") String token,
			                        @RequestParam ("password") String password,
			                        Model model) {
		
		System.out.println("password " + password);
		System.out.println("token" + token);
		User user = serviceUser.getByResetPasswordExpire(token);
		if(user==null) {
			model.addAttribute("msg", "Invalid token");
			return "message";
		} else {
			serviceUser.resetPassword(user, password);
			model.addAttribute("msg", "You have successfully change your password");
			return "message";
		}
	}
}




















