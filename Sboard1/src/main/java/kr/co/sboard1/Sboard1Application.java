package kr.co.sboard1;

import java.security.Principal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class Sboard1Application {

	public static void main(String[] args) {
		SpringApplication.run(Sboard1Application.class, args);
	}

	@GetMapping(value = {"","index"})
	public String index(Principal principal) {
		if(principal != null) {
			return "redirect:/list";
		}else {
			return "redirect:/user/login";
		}
	}
}
