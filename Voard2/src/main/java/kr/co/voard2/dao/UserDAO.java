package kr.co.voard2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.voard2.vo.TermsVO;
import kr.co.voard2.vo.UserVO;


@Mapper
@Repository
public interface UserDAO {

	public int insertUser(UserVO vo);
	public UserVO selectUser(String uid);
	public List<UserVO> selectUsers();
	public void updateUser(UserVO vo);
	public void deleteUser(String uid);
	public TermsVO selectTerms();
}
