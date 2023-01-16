package kr.co.ch07.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ch07.dao.User6DAO;
import kr.co.ch07.repository.User6Repo;
import kr.co.ch07.vo.User6VO;

@Service
public class User6Service {
	
	@Autowired
	private User6DAO dao;
	
	@Autowired
	private User6Repo repo;

	public void insertUser6(User6VO vo) {
		// dao.insertUser6(vo);
		repo.save(vo);
	};
	public User6VO selectUser6(String name) {
		// return dao.selectUser6(name);
		return repo.findById(name).get();
	};
	public List<User6VO> selectUser6s() {
		// return dao.selectUser6s();
		return repo.findAll();
	};
	public void updateUser6(User6VO vo) {
		// dao.updateUser6(vo);
		repo.save(vo);
	};
	public void deleteUser6(String name) {
		// dao.deleteUser6(name);
		repo.deleteById(name);
	};
}
