package kr.co.todoApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.todoApp.dao.UserDao;
import kr.co.todoApp.vo.UserVO;

@Service
public class UserService {
	
	@Autowired
	private UserDao dao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public int insertUser (UserVO vo) {
		vo.setPass(passwordEncoder.encode(vo.getPass()));
		return dao.insertUser(vo);
	};
}
