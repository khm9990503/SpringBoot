package kr.co.ch09.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ch09.service.User1Service;
import kr.co.ch09.vo.User1VO;

@RestController // @responseBody 안 쓰고 대신 사용
public class User1Controller {
	@Autowired
	private User1Service service;

	@GetMapping("/user1")
	public List<User1VO> list() {
		List<User1VO> users = service.selectUser1s();
		
		/*
		 * builder 패턴 초기화
		User1VO user1 = User1VO.builder()
								.uid("a101")
								.pass("1234")
								.name("홍길동")
								.hp("010-1234-1001")
								.age(11)
								.build();
		*/
		return users; //jackson lib 가 자동으로 json 으로 출력
	};
	
	@GetMapping("/user1/{uid}")
	public User1VO list(@PathVariable("uid") String uid) {
		return service.selectUser1(uid);
	}
	
	@PostMapping("/user1")
	public List<User1VO> register(User1VO vo) {
		service.insertUser1(vo);
		return service.selectUser1s();
	}
	
	@PutMapping("/user1")
	public List<User1VO> modify(User1VO vo) {
		service.updateUser1(vo);
		return service.selectUser1s();
	}
	
	@DeleteMapping("/user1/{uid}") // 파라미터 이용 시
	public List<User1VO> delete(@PathVariable("uid") String uid) { // @PathVariable("parameter")
		service.deleteUser1(uid);
		return service.selectUser1s();
	}
}
