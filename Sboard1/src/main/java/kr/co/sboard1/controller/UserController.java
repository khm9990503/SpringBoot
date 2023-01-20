package kr.co.sboard1.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sboard1.service.TermsService;
import kr.co.sboard1.service.UserService;
import kr.co.sboard1.vo.TermsVO;
import kr.co.sboard1.vo.UserVO;

@Controller
public class UserController {

	@Autowired
	private TermsService ts;
	
	@Autowired
	private UserService service;
	
	@GetMapping("user/login")
	public String login() {
		return "user/login";
	}
	
	@GetMapping("user/register")
	public String register() {
		return "user/register";
	}
	@PostMapping("user/register")
	public String register(UserVO vo, HttpServletRequest req) {
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		int result = service.insertUser(vo);
		
		return "redirect:/user/login?success="+result;
	}
	@ResponseBody
	@GetMapping("user/checkUid")
	public Map<String , Integer> checkUid(String uid) {
		int result = service.countUser(uid);
		Map<String , Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	@ResponseBody
	@GetMapping("user/checkNick")
	public Map<String , Integer> checkNick(String nick) {
		int result = service.countNick(nick);
		Map<String , Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	@GetMapping("user/terms")
	public String terms(Model model) {
		TermsVO vo = ts.selectTerms();
		
		model.addAttribute("vo",vo);
		return "user/terms";
	}
}
