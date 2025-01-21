package com.springboot.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.entity.Question;
import com.springboot.entity.User;
import com.springboot.entity.UserScore;
import com.springboot.repository.RepoUser;
import com.springboot.service.IServiceQuestion;
import com.springboot.service.IServiceUser;
import com.springboot.service.IServiceUserScore;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	public IServiceUser serviceUser;

	@Autowired
	public RepoUser repoUser;

	@Autowired
	public IServiceUserScore userScore;

	@Autowired
	public IServiceQuestion serviceQuestion;

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
		return "user/user_profile";
	}

	@GetMapping("/javaquiz")
	public String getAllJavaQuestion(@ModelAttribute Question question, Map<String, Object> map) {
		List<Question> op = serviceUser.getQuestion("Java");
		map.put("op", op);
		return "user/javaquiz";
	}

	@PostMapping("/javaquiz")
	public String getResultJava(@RequestParam Map<String, String> answers, Principal p, Model model) {
		String email = p.getName();
		List<Question> op = serviceUser.getQuestion("Java");
		int score = 0;
		for (int i = 0; i < op.size(); i++) {
			String correct = op.get(i).getCorrectAnswer();
			String userAnswer = answers.get("Answer" + i);
			if (correct != null && correct.equals(userAnswer)) {
				score++;
			}
		}
		userScore.saveScore(email, score, "Java");
		model.addAttribute("score", score);
		model.addAttribute("AllAnswers", op.size());
		return "user/allscore";
	}

	@GetMapping("/sqlquiz")
	public String getAllSQLQuestion(@ModelAttribute Question question, Map<String, Object> map) {
		List<Question> op = serviceUser.getQuestion("SQL");
		map.put("op", op);
		return "user/sqlquiz";
	}

	@PostMapping("/sqlquiz")
	public String getResultSQL(@RequestParam Map<String, String> answers, Model model, Principal p) {
		String email = p.getName();
		List<Question> op = serviceUser.getQuestion("SQL");
		int score = 0;
		for (int i = 0; i < op.size(); i++) {
			String correct = op.get(i).getCorrectAnswer();
			String userAnswer = answers.get("Answer" + i);
			if (correct != null && correct.equals(userAnswer)) {
				score++;
			}
		}
		userScore.saveScore(email, score, "SQL");
		model.addAttribute("score", score);
		model.addAttribute("AllAnswers", op.size());
		return "user/allscore";
	}

	@GetMapping("/uiquiz")
	public String getAllUIQuestion(@ModelAttribute Question question, 
			                       Map<String, Object> map) {
		List<Question> op = serviceQuestion.showQuetions("UI");
		map.put("op", op);
		return "user/uiquiz";
	}

	@PostMapping("/uiquiz")
	public String getResultUI(@RequestParam Map<String, String> answers, Model model, Principal p) {
		String email = p.getName();
		List<Question> op = serviceUser.getQuestion("UI");
		int score = 0;
		for (int i = 0; i < op.size(); i++) {
			Question question = op.get(i);
			String correctAnswer = question.getCorrectAnswer();
			String userAnswer = answers.get("Answer" + i);
			System.out.println("Question: " + question.getTitle());
			System.out.println("Correct Answer: " + correctAnswer);
			System.out.println("User Answer: " + userAnswer);

			if (correctAnswer != null && correctAnswer.equals(userAnswer)) {
				score++;
			}
		}
		userScore.saveScore(email, score, "UI");
		model.addAttribute("score", score);
		model.addAttribute("AllAnswers", op.size());
		return "user/allscore";
	}

//	@GetMapping("/restrictime")
//	public String restricTime() {
//		return "user/TimeRestric";
//	}

	@GetMapping("/userAbout")
	public String userAbouts(Map<String, Object> map, Principal p) {
		String email = p.getName();
		User user = serviceUser.getUserDeatails(email);
		map.put("details", user);
		return "user/userAbout";
	}

	@GetMapping("/updateAbout/{email}")
	public String userUpdate(@PathVariable String email, @ModelAttribute User user) {
		User update = serviceUser.getUserDeatails(email);
		BeanUtils.copyProperties(update, user);
		return "user/updateAbout";
	}

	@PostMapping("/updateAbout")
	public String userUpdateSave(@ModelAttribute User user, Principal p, RedirectAttributes attr) {
		String update = p.getName();
		user.setEmail(update);
		User uu = serviceUser.saveEmail(user);
		attr.addAttribute("uu", uu);
		return "redirect:userAbout";
	}

	@GetMapping("/userScore")
	public String getuserScoreData(Map<String, Object> map,
			@RequestParam(value = "catagory", required = false) String catagory) {
		List<UserScore> list = userScore.gtUserScore(catagory);
		map.put("allQuestion", list);
		map.put("catagory", catagory);
		return "user/userScore";
	}

	@GetMapping("/delete/{id}")
	public String deleteUserScore(@PathVariable Integer id, RedirectAttributes rtta) {
		String del = userScore.deleteUserScore(id);
		rtta.addFlashAttribute("del", del);
		return "redirect:/user/userScore";
	}
	
	@GetMapping("/javaprogramquiz")
	public String getAllJavaQuestionProgram(@ModelAttribute Question question, Map<String, Object> map) {
		List<Question> op = serviceUser.getQuestion("JavaProgram");
		map.put("op", op);
		return "user/javaprogramquiz";
	}

	@PostMapping("/javaprogramquiz")
	public String getResultJavaProgram(@RequestParam Map<String, String> answers, Principal p, Model model) {
		String email = p.getName();
		List<Question> op = serviceUser.getQuestion("JavaProgram");
		int score = 0;
		for (int i = 0; i < op.size(); i++) {
			String correct = op.get(i).getCorrectAnswer();
			String userAnswer = answers.get("Answer" + i);
			if (correct != null && correct.equals(userAnswer)) {
				score++;
			}
		}
		userScore.saveScore(email, score, "Java");
		model.addAttribute("score", score);
		model.addAttribute("AllAnswers", op.size());
		return "user/allscore";
	}
	
	@GetMapping("/sqlsyntaxquiz")
	public String getAllSQLSyntaxProgram(@ModelAttribute Question question, Map<String, Object> map) {
		List<Question> op = serviceUser.getQuestion("SQLSyntax");
		map.put("op", op);
		return "user/sqlsyntaxquiz";
	}

	@PostMapping("/sqlsyntaxquiz")
	public String getResultSQLSyntax(@RequestParam Map<String, String> answers, Principal p, Model model) {
		String email = p.getName();
		List<Question> op = serviceUser.getQuestion("SQLSyntax");
		int score = 0;
		for (int i = 0; i < op.size(); i++) {
			String correct = op.get(i).getCorrectAnswer();
			String userAnswer = answers.get("Answer" + i);
			if (correct != null && correct.equals(userAnswer)) {
				score++;
			}
		}
		userScore.saveScore(email, score, "Java");
		model.addAttribute("score", score);
		model.addAttribute("AllAnswers", op.size());
		return "user/allscore";
	}
	
	@GetMapping("/uisyntaxquiz")
	public String getAllUISyntax(@ModelAttribute Question question, Map<String, Object> map) {
		List<Question> op = serviceUser.getQuestion("UISyntax");
		map.put("op", op);
		return "user/uisyntaxquiz";
	}

	@PostMapping("/uisyntaxquiz")
	public String getResultUISyntax(@RequestParam Map<String, String> answers, Principal p, Model model) {
		String email = p.getName();
		List<Question> op = serviceUser.getQuestion("UISyntax");
		int score = 0;
		for (int i = 0; i < op.size(); i++) {
			String correct = op.get(i).getCorrectAnswer();
			String userAnswer = answers.get("Answer" + i);
			if (correct != null && correct.equals(userAnswer)) {
				score++;
			}
		}
		userScore.saveScore(email, score, "Java");
		model.addAttribute("score", score);
		model.addAttribute("AllAnswers", op.size());
		return "user/allscore";
	}
	
}