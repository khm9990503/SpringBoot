package kr.co.sboard1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import kr.co.sboard1.dao.UserDAO;
import kr.co.sboard1.entity.UserEntity;
import kr.co.sboard1.repository.UserRepo;
import kr.co.sboard1.security.MyUserDetails;
import kr.co.sboard1.vo.UserVO;

@Service
public class UserService implements UserDetailsManager {

	@Autowired
	private UserDAO dao;
	
	@Autowired
	private UserRepo repo;
	
	public int insertUser(UserVO vo) {
		String pass = vo.getPass2();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		vo.setPass(encoder.encode(pass));
		
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
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = repo.findById(username).get();
		if(user == null) {
			System.out.println("user 없음...");
			throw new UsernameNotFoundException(username);
		}
		MyUserDetails myUser = new MyUserDetails();
		myUser.setUid(user.getUid());
		myUser.setPass(user.getUid());
		myUser.setName(user.getUid());
		myUser.setNick(user.getUid());
		myUser.setEmail(user.getUid());
		myUser.setHp(user.getUid());
		myUser.setGrade(user.getGrade());
		myUser.setZip(user.getZip());
		myUser.setAddr1(user.getAddr1());
		myUser.setAddr2(user.getAddr2());
		myUser.setRegip(user.getRegip());
		myUser.setRdate(user.getRdate());
		
		return myUser;
	}
	@Override
	public void createUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean userExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
