package javaQuiz.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import javaQuiz.model.Question;
import javaQuiz.model.User;
import javaQuiz.model.UsersAnswer;
import javaQuiz.model.WrongAnswer;
import javaQuiz.service.QuestionService;
import javaQuiz.service.UserService;
import javaQuiz.service.UsersAnswerService;
import javaQuiz.service.WrongAnswerService;

@Controller
public class AplicationController {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;
	@Autowired 
	private WrongAnswerService wrongAnswerService;
	@Autowired
	private UsersAnswerService usersAnswerService;

	private ArrayList<Question> questions;
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) {
	        new SecurityContextLogoutHandler().logout(request, (jakarta.servlet.http.HttpServletResponse) response, auth);
	    }
	    return "redirect:/login"; // Redirect to home page after logout
	}
	
	@GetMapping("/")
	public String start() {
		return "redirect:/login";
	}
	
	
	@GetMapping("/admin/homeadmin")
	public String homeAdmin() {
		System.out.println("iz homeAdmin metode");
		return "homeadmin";
	}

	@GetMapping("/user/homeuser")
	public String homeUser() {
		System.out.println("iz homeUser metode");
		return "homeuser";
	}
	
	@GetMapping("/admin/all-users")
	public String allUsersMetoda(HttpServletRequest request) {
		request.setAttribute("participants", userService.findAllParticipants());
		request.setAttribute("msg", "Svi Ucesnici");
		System.out.println("iz allUsers metode");
		return "all-users";
	}
	@GetMapping("/admin/all-questions")
	public String allQuestionsMetoda( HttpServletRequest request) {
		request.setAttribute("questionsAndAnswers", questionService.findAllQueAndA());
		request.setAttribute("msg", "Sva pitanja");
		request.setAttribute("mode", "all-questions");
		System.out.println("iz allQuestions metode");
		return "all-questions";
	}
	
	@GetMapping("/admin/new-question")
	public String newQuestion( HttpServletRequest request) {
		request.setAttribute("msg", "Insert new question:");
		request.setAttribute("mode", "new-question");
		System.out.println("IZ NEW QUESTION");
		return "all-questions";
	}
	
	//Parametar @ModelAttribute Question question znači da će Spring MVC automatski kreirati i dodati objekat klase Question
	// u model, i popuniti njegova polja sa podacima iz POST zahteva koji se poklapaju po imenu
	@PostMapping("admin/save-question")
	public String saveQuestion(@ModelAttribute Question question, BindingResult bindingResult,HttpServletRequest request) {
		questionService.save(question);
		request.setAttribute("msg", "Insert wrong questions for  "+ question.getText());
		request.setAttribute("mode", "wrong-answers");
		System.out.println("Id unetog question-a je "+question.getId());
		request.setAttribute("question_id", question.getId()); 
		return "all-questions";
	}
	
	@GetMapping("admin/update-question")
	public String updateQuestion(@RequestParam int id, HttpServletRequest request) {
		request.setAttribute("oldQuestion", questionService.findQuestion(id));
		request.setAttribute("msg", "update-question");
		System.out.println("IZ UPDATE QUESTION "+questionService.findQuestion(id));
		request.setAttribute("mode", "update-question");
		return "all-questions";
	}
	
	@GetMapping("admin/delete-question")
	public String deleteQuestionAndAnswers(@RequestParam int id,HttpServletRequest request) {
		wrongAnswerService.deleteByQuestionId(id);
		questionService.delete(id);
		request.setAttribute("questionsAndAnswers", questionService.findAllQueAndA());
		request.setAttribute("msg", "Sva pitanja");
		request.setAttribute("mode", "all-questions");
		return "all-questions";
	}
	
	@PostMapping("admin/save-wrong-answers")
	public String saveWrongAnswers(@RequestParam("questionId") int questionId,@RequestParam("origin") String origin, @RequestParam("field[]") List<String>fields,HttpServletRequest request) {
		for (String field : fields) {
			WrongAnswer wrongAnswer = new WrongAnswer();
			wrongAnswer.setQuestionId(questionId);
			wrongAnswer.setText(field);
			wrongAnswerService.save(wrongAnswer);
		}
		request.setAttribute("questionsAndAnswers", questionService.findAllQueAndA());
		request.setAttribute("msg", "Svi pogresni odgovori za \""+questionService.findQuestion(questionId).getText() + "\"");
		if (origin.equals("newWrongAnswers")) {
			request.setAttribute("mode", "all-questions");
		}else {
			request.setAttribute("wrongAnswers", wrongAnswerService.findByQuestion_id(questionId));
			request.setAttribute("mode", "update-wrong-answers");
			request.setAttribute("question_id", questionId);
		}
		System.out.println("iz allQuestions metode");
		return "all-questions";	
	}
	
	@PostMapping("admin/update-wrong-answers")
	public String updateWrongAnswers(@ModelAttribute Question question, BindingResult bindingResult, HttpServletRequest request) { 
		questionService.save(question);
		request.setAttribute("msg", "Update wrong answers for  \"" +
				question.getText() +"\"");
		request.setAttribute("wrongAnswers", wrongAnswerService.findByQuestion_id(question.getId()));
		request.setAttribute("mode", "update-wrong-answers");
		request.setAttribute("question_id", question.getId());
		return "all-questions";
	}
	
	@GetMapping("admin/delete-wrong-answer")
	public String deleteWrongAnswer(@RequestParam int id,@RequestParam("questionId") int questionId,HttpServletRequest request) {
		wrongAnswerService.delete(id);
		request.setAttribute("msg", "Update wrong answers for  \"" +
				questionService.findQuestion(questionId).getText() +"\"");
		request.setAttribute("wrongAnswers", wrongAnswerService.findByQuestion_id(questionId));
		request.setAttribute("mode", "update-wrong-answers");
		request.setAttribute("question_id", questionService.findQuestion(questionId).getId());
		return "all-questions";
	}
	@GetMapping("admin/update-wrong-answer")
	public String updateWrongAnswer(@RequestParam int id,@RequestParam("questionId") int questionId,HttpServletRequest request) {
		request.setAttribute("msg", "Update one wrong answer for  \"" +
				questionService.findQuestion(questionId).getText() +"\"");
		request.setAttribute("wrongAnswer", wrongAnswerService.find(id));
		request.setAttribute("mode", "update-wrong-answer");
		request.setAttribute("question_id", questionService.findQuestion(questionId).getId());
		return "all-questions";
	}
	
	@PostMapping("admin/save-wrong-answer")
	public String saveWrongAnswer(@ModelAttribute WrongAnswer wrongAnswer,@RequestParam("questionId") int questionId, BindingResult bindingResult,HttpServletRequest request) {
		System.out.println(wrongAnswer);
		wrongAnswerService.save(wrongAnswer);
		request.setAttribute("msg", "Wrong answer updated for  \"" +
				questionService.findQuestion(questionId).getText() +"\"");
		request.setAttribute("wrongAnswers", wrongAnswerService.findByQuestion_id(questionId));
		request.setAttribute("mode", "update-wrong-answers");
		request.setAttribute("question_id", questionService.findQuestion(questionId).getId());
		return "all-questions";
	}
	
	@GetMapping("/admin/correct-answers")
		public String usersWithCorrectAnswers(HttpServletRequest request) {
			request.setAttribute("participants",userService.findUsersWithCorrectAnswers());
			request.setAttribute("msg","Ucesnici sa tacnim odgovorima");
			System.out.println("iz usersWithCorrectAnswers metode");
			return "all-users";
		
	}
	
	@GetMapping("/admin/users-all-answers")
	public String usersWithAllAnswers(HttpServletRequest request) {
		request.setAttribute("participants",userService.findUsersWithAllAnswers());
		request.setAttribute("msg","Ucesnici sa svim odgovorima");
		System.out.println("iz usersWithAlltAnswers metode");
		return "all-users";
	
	}
	
	@GetMapping("admin/users-with-answer-count")
	public String usersWithAllAnswersCount(HttpServletRequest request){
		request.setAttribute("participants",userService.getBestUsers());
		request.setAttribute("msg","Ucesnici po uspesnosti:");
		return "users-answers-count";
	}
	
	@GetMapping("admin/questions-with-answer-count")
	public String questionsWithAnswersCount(HttpServletRequest request) {
		request.setAttribute("msg", "Spisak pitanja uređenih po nerastućem procentu tačnih odgovora");
		request.setAttribute("mode","correct");
		request.setAttribute("questions",questionService.questionsCorrectPercentage());
		return "questions-answer-count";
	}
	
	@GetMapping("admin/questions-with-wrong-answer-count")
	public String questionsWithWrongAnswersCount(HttpServletRequest request) {
		request.setAttribute("msg", "Spisak pitanja uz prikaz procenta najčešćeg netačnog odgovora i samog odgovora");
		request.setAttribute("mode","wrong");
		request.setAttribute("questions",questionService.questionsIncorrectPercentage());
		return "questions-answer-count";
	}
	
	@GetMapping("/public/new-user")
	public String newUser(HttpServletRequest request) {
		return "new-user";
	}
	
	@GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Vraća ime vaše JSP stranice (login.jsp)
    }
	
	@PostMapping("public/save-user")
	public String saveUser(@ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request) {
		if (userService.findByUsername(user.getUsername()) != null) {
			request.setAttribute("msg",
					"Nije moguca registracija jer postoji korisnik sa username " + user.getUsername());
		} else {
			userService.save(user);
			request.setAttribute("msg", "Uspesno ste se registrovali!");
		}

		return "login";
	}
	
	@GetMapping("/user/question/start")
	public String firstQuestion(Model model) {
		questions = (ArrayList<Question>) questionService.findAll();
		Question question1 = questionService.findQuestion(questions.get(0).getId());
		model.addAttribute("questionText", question1.getText());
		// model.addAttribute("questionId", question1.getId());
		model.addAttribute("questionId", 0);
		return "redirect:/user/question/" + 0;
	}

	@GetMapping("/user/question/{questionSN}")
	public String viewQuestion(@PathVariable int questionSN, Model model) {
		Question question = questions.get(questionSN); // u ovom slucaju id ravnomerno raste
		model.addAttribute("questionText", question.getText());
		model.addAttribute("position",questionSN);
		model.addAttribute("numberOfQuestions", questions.size());
		List<WrongAnswer> wrongAnswers = questionService.allWrongAnForQue(question.getId());
		WrongAnswer fakeWrAn = new WrongAnswer(-1, question.getId(), question.getTrueAnswer());
		Random rand = new Random();
		int positionA = rand.nextInt(wrongAnswers.size() + 1);
		wrongAnswers.add(positionA, fakeWrAn);

		System.out.println("Stiglo questionSN: " + questionSN);
		model.addAttribute("answers", wrongAnswers);
		return "questions_view";
	}

	@PostMapping("/user/submitAnswers")
	public String submitAnswers(@RequestParam("nextQuestionId") int questionSN,
			@RequestParam("userName") String userName,
			@RequestParam(value = "selectedAnswers", required = false) List<Integer> selectedAnswerIds) {

		System.out.println("IZABRANE OPCIJE SU: " + selectedAnswerIds);
		System.out.println("NAKON IZBORA OPCIJE trenutno pitanje je " + questionSN);
		Question question = questions.get(questionSN);
		System.out.println(question);
		User user = userService.findByUsername(userName);
		usersAnswerService.deleteOldAnswers(user.getId(), question.getId());
		if (selectedAnswerIds != null) {
			for (Integer answer_id : selectedAnswerIds) {
				if (answer_id == -1) {
					UsersAnswer usersAnswer = new UsersAnswer(0, user.getId(), question.getId(), null);
					usersAnswerService.save(usersAnswer);
				} else {
					UsersAnswer usersAnswer = new UsersAnswer(0, user.getId(), question.getId(), answer_id);
					usersAnswerService.save(usersAnswer);
				}
			}
		}

		if ((questionSN+1) < questions.size()) {
			return "redirect:/user/question/" + (questionSN+1); // Redirect to the next question or a completion page
		} else {
			return "redirect:/user/result";
		}
	}

	@GetMapping("/user/result")
	public String quizResults(HttpServletRequest request) {
			request.setAttribute("participants", userService.get5BestUsers());
			request.setAttribute("msg", "5 Najboljih Ucesnika:");
			
		return "quiz-result";
	}

	
}
