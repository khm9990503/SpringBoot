package kr.co.todoApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.todoApp.dao.UserDao;
import kr.co.todoApp.vo.UserVO;

@Service
public class UserService {
	
	@Autowired
	private UserDao dao;
	
	public int insertUser (UserVO vo) {
		return dao.insertUser(vo);
	};
}