package kr.co.todoApp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.todoApp.jwt.JWTUtil;
import kr.co.todoApp.repository.UserEntity;
import kr.co.todoApp.security.MyUserDetails;
import kr.co.todoApp.security.SecurityUserService;
import kr.co.todoApp.service.UserService;
import kr.co.todoApp.vo.TodoVO;
import kr.co.todoApp.vo.UserVO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private SecurityUserService securityUserService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTUtil jwtUtil;
	
	//@CrossOrigin(origins = "*")
	@PostMapping("user/login")
	public Map<String, Object> login(@RequestBody UserVO vo) {
		log.info("vo : " + vo);
		// 사용자 정보 객체생성
		String uid = vo.getUid();
		String pass = vo.getPass();
		MyUserDetails myUserDetails = securityUserService.loadUserByUsername(uid);
		log.info("login...1");
		
		// Security 인증처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(myUserDetails, pass));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.info("login...2");
		
		// JWT 생성
		String token = jwtUtil.generateToken(uid);
		log.info("login...3 : " + token);
		
		// 데이터 출력
		UserEntity user = myUserDetails.getUser();
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("accessToken", token);
		resultMap.put("user", user);
		
		return resultMap;		
	}
	
	@GetMapping("user/auth")
	public Map<String, Object> auth(Authentication authentication) {
		
		log.info("auth...1");
		
		// Security 사용자 인증 객체 
		MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
		UserEntity user = myUserDetails.getUser();
		
		log.info("auth...3 : " + user);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("user", user);
		
		return resultMap;	
	}
	
	@PostMapping("user/regist")
	public int regist(@RequestBody UserVO vo) {
		int result = service.insertUser(vo);
		return result;
	}
	
	@PostMapping("addTodo")
	public int addTodo(@RequestBody TodoVO vo) {
		return service.insertTodo(vo);
	}
	
}
