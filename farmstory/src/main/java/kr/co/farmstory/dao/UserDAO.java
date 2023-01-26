package kr.co.farmstory.dao;

import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDAO {
    public void insertUser(UserVO vo);
    public UserVO selectUser(String uid);
    public TermsVO selectTerms();
    public List<UserVO> selectUsers();
    public void updateUser(UserVO vo);
    public void deleteUser(String uid);
    public int checkUid(String uid);
    public int checkNick(String nick);
}
