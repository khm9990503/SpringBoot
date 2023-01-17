package kr.co.ch10.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class MainControllerTest {

	@Autowired
	private MockMvc mvc; // test mvc 객체
	
	
	public void index() throws Exception {
		
		mvc.perform(get("/index")).andExpect(status().isOk()).andDo(print()); // index 요청을 test
	}
	
	@BeforeAll
	public static void before() {
		System.out.println("before 실행...");
	}
	
	@Test // 테스트할 곳에 어노테이션 (단위 실행)
	public void hello() throws Exception {
		
		mvc.perform(get("/hello")).andExpect(status().isOk()).andDo(print()); // index 요청을 test
	}
	
	@AfterAll
	public static void after() {
		System.out.println("after 실행...");
	}
}
