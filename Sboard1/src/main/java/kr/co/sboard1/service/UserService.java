package kr.co.sboard1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import kr.co.sboard1.dao.UserDAO;
import kr.co.sboard1.entity.UserEntity;
import kr.co.sboard1.repository.UserRepo;
import kr.co.sboard1.security.MyUserDetails;
import kr.co.sboard1.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private UserDAO dao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo repo;
	
	public int insertUser(UserVO vo) {
		vo.setPass(passwordEncoder.encode(vo.getPass2()));
		
		int result = dao.insertUser(vo);
		return result;
	};
	public UserVO selectUser(String uid) {
		return dao.selectUser(uid);
	};
	public List<UserVO> selectUsers() {
		return dao.selectUsers();
	};
	public void updateUser(UserVO vo) {
		dao.updateUser(vo);
	};
	public void deleteUser(String uid) {
		dao.deleteUser(uid);
	};
	
	public int countUser(String uid) {
		return repo.countByUid(uid);
	}
	public int countNick(String nick) {
		return repo.countByNick(nick);
	}
	
	
}
