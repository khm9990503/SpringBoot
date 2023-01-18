package kr.co.sboard1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.sboard1.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class Sboard1ApplicationTests {

	@Autowired
	private UserRepo repo;
	
	void contextLoads() {
	}

	@Test
	public void countTest() {
		int result = repo.countByUid("admin12");
		
		log.info("result : "+result);
	}
	
}
