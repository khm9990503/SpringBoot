package kr.co.ch07.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.ch07.service.User6Service;
import kr.co.ch07.vo.User6VO;

@Controller
public class User6Controller {

	@Autowired
	private User6Service service;
	
	@GetMapping("/user6/list")
	public String list(Model model) {
		List<User6VO> users = service.selectUser6s();
		model.addAttribute("users",users);
		return "/user6/list";
	}
	
	@GetMapping("/user6/register")
	public String register() {
		return "/user6/register";
	}
	@GetMapping("/user6/modify")
	public String modify() {
		return "/user6/modify";
	}
	@GetMapping("/user6/delete")
	public String delete() {
		return "redirect:/user6/list";
	}
}
