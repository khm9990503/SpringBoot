package kr.co.ch09.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ch09.service.User2Service;
import kr.co.ch09.vo.User2VO;


@RestController // @responseBody 안 쓰고 대신 사용
public class User2Controller {
	@Autowired
	private User2Service service;

	//@CrossOrigin(origins = "*") // 이거 사용 시,cors 꺼도 가능 (CorsConfig.java. 로 설정)
	@GetMapping("/user2s")
	public List<User2VO> list() {
		List<User2VO> users = service.selectUser2s();
		
		/*
		 * builder 패턴 초기화
		User2VO User2 = User2VO.builder()
								.uid("a101")
								.pass("1234")
								.name("홍길동")
								.hp("010-1234-1001")
								.age(11)
								.build();
		*/
		return users; //jackson lib 가 자동으로 json 으로 출력
	};
	
	@GetMapping("/user2")
	public User2VO User2(String uid) {
		return service.selectUser2(uid);
	}
	
	@GetMapping("/user2/{uid}")
	public User2VO list(@PathVariable("uid") String uid) {
		return service.selectUser2(uid);
	}
	
	//@CrossOrigin(origins = "*") // 이거 사용 시,cors 꺼도 가능(CorsConfig.java. 로 설정)
	@PostMapping("/user2")
	public List<User2VO> register(@RequestBody User2VO vo) { // @RequestBody 써줘야 받는다.
		
		service.insertUser2(vo);
		return service.selectUser2s();
	}
	
	//@CrossOrigin(origins = "*") // 이거 사용 시,cors 꺼도 가능(CorsConfig.java. 로 설정)
	@PutMapping("/user2")
	public List<User2VO> modify(@RequestBody User2VO vo) {
		service.updateUser2(vo);
		return service.selectUser2s();
	}
	
	//@CrossOrigin(origins = "*") // 이거 사용 시,cors 꺼도 가능(CorsConfig.java. 로 설정)
	@DeleteMapping("/user2/{uid}") // 파라미터 이용 시
	public List<User2VO> delete(@PathVariable("uid") String uid) { // @PathVariable("parameter")
		service.deleteUser2(uid);
		return service.selectUser2s();
	}
}
