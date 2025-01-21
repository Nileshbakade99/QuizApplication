package com.springboot.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.entity.Question;
import com.springboot.entity.User;
import com.springboot.repository.RepoUser;
import com.springboot.service.IServiceQuestion;
import com.springboot.service.IServiceUser;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	public IServiceQuestion serviceQues;

	@Autowired
	public IServiceUser serviceUser;

	@Autowired
	public RepoUser repoUser;

	@ModelAttribute
	public void commonUser(Principal p, Map<String, Object> map) {
		if (p != null) {
			String email = p.getName();
			User user = repoUser.findByEmail(email);
			map.put("user", user);
		}
	}

	@GetMapping("/profile")
	public String profile() {
		return "admin/admin_profile";
	}

	@GetMapping("/CreateQuestions")
	public String create() {
		return "admin/CreateQuestions";
	}

	@GetMapping("/AddQues")
	public String createJava(@ModelAttribute Question question) {
		return "admin/AddQues";
	}

	@PostMapping("/AddQues")
	public String saveJava(@ModelAttribute Question question, RedirectAttributes attr, @RequestParam String title,
			@RequestParam String options, @RequestParam String correctAnswer, @RequestParam String catagory) {
		Question que = serviceQues.saveQuestion(question);
		if (que != null) {
			attr.addFlashAttribute("msg", "Question and answer add successfully");
		} else {
			attr.addFlashAttribute("msg", "Something problem on server");
		}
		return "redirect:AddQues";
	}
	
	@GetMapping("/AddProgramsQues")
	public String createProgramJava(@ModelAttribute Question question) {
		return "admin/AddProgramsQues";
	}

	@PostMapping("/AddProgramsQues")
	public String saveProgramJava(@ModelAttribute Question question, RedirectAttributes attr, @RequestParam String title,
			@RequestParam String options, @RequestParam String correctAnswer, @RequestParam String catagory) {
		Question que = serviceQues.saveQuestion(question);
		if (que != null) {
			attr.addFlashAttribute("msg", "Question and answer add successfully");
		} else {
			attr.addFlashAttribute("msg", "Something problem on server");
		}
		return "redirect:AddProgramsQues";
	}

	@GetMapping("/showQuestionJava")
	public String showQuestionCatagory(Map<String, Object> map,

			@RequestParam(value = "catagory", required = false) String catagory) {
		return getallPage(map, 1, catagory);
	}

	@GetMapping("/delete/{id}")
	public String deleteQuestion(@PathVariable Integer id, RedirectAttributes attr) {
		String del = serviceQues.deleteQuestion(id);
		if (del != null) {
			attr.addFlashAttribute("msg", "Question delete successfully");
		} else {
			attr.addFlashAttribute("msg", "Something problem on server");
		}
		return "redirect:/admin/showQuestionJava";
	}

	@GetMapping("/edit/{id}")
	public String getBYIDQUESTION(@PathVariable Integer id, @ModelAttribute Question question) {
		Question que = serviceQues.getByIdQuestion(id);
		BeanUtils.copyProperties(que, question);
		return "admin/updateQues";
	}

	@PostMapping("/edit")
	public String saveUpdateQuestion(@ModelAttribute Question question, RedirectAttributes attr) {
		Question que = serviceQues.saveQuestion(question);
		if (que != null) {
			attr.addFlashAttribute("msg", "Question update successfully");
		} else {
			attr.addFlashAttribute("msg", "Something problem on server");
		}
		return "redirect:/admin/showQuestionJava";
	}

	@GetMapping("/adminAbout")
	public String getAbout(Map<String, Object> map, Principal p) {
		String email = p.getName();
		User details = serviceUser.getUserDeatails(email);
		map.put("details", details);
		return "admin/adminAbout";
	}

	@GetMapping("/page/{pageno}")
	public String getallPage(Map<String, Object> map, @PathVariable int pageno,
			@RequestParam(value = "catagory", required = false) String catagory) {
		int size = 4;
		Page<Question> page = serviceQues.getAdminPage(pageno - 1, size, catagory);
		List<Question> javaQuestion = page.getContent();

		map.put("currentPage", pageno);
		map.put("totalPages", page.getTotalPages());
		map.put("totalItems", page.getTotalElements());
		map.put("javaQuestion", javaQuestion);
		map.put("catagory", catagory);
		return "admin/showQuestionJava";
	}

	@GetMapping("/UserDetails")
	public String getUserDetails(@RequestParam(required = false) String searchTerm, Map<String, Object> map) {
		List<User> userDetails = serviceUser.getUserDetailsData();

		if (searchTerm != null && !searchTerm.trim().isEmpty()) {

			String searchTermLower = searchTerm.toLowerCase();

			userDetails = userDetails.stream().filter(user -> user.getName().toLowerCase().contains(searchTermLower)
					|| String.valueOf(user.getPhoneno()).contains(searchTermLower) || // Convert phone number to String
					user.getGender().toLowerCase().contains(searchTermLower)
					|| user.getEmail().toLowerCase().contains(searchTermLower)).collect(Collectors.toList());
		}
		map.put("userDetails", userDetails);
		map.put("searchTerm", searchTerm);
		return "admin/UserDetails";
	}
}
