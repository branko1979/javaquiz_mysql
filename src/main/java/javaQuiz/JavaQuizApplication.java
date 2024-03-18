package javaQuiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
//@ComponentScan(basePackages = {"javaQuiz.controller", "javaQuiz.dao","javaQuiz.model", "javaQuiz.service"})
public class JavaQuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaQuizApplication.class, args);
	}

}
