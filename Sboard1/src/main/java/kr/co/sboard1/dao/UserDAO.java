package kr.co.sboard1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.sboard1.vo.UserVO;

@Mapper
@Repository
public class UserDAO {

	@Autowired
	private SqlSessionTemplate myBatis;
	
	public int insertUser(UserVO vo) {
		int result = myBatis.insert("user.insertUser",vo);
		return result;
	};
	public UserVO selectUser(String uid) {
		return null;
	};
	public List<UserVO> selectUsers() {
		return null;
	};
	public void updateUser(UserVO vo) {
		
	};
	public void deleteUser(String uid) {
		
	};
}
