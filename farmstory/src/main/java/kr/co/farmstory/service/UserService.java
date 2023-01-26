package kr.co.farmstory.service;

import kr.co.farmstory.dao.UserDAO;
import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO dao;

    @Autowired
    private PasswordEncoder pwEnc;
    public void insertUser(UserVO vo){
        vo.setPass(pwEnc.encode(vo.getPass2()));
        dao.insertUser(vo);
    };
    public UserVO selectUser(String uid){
        return dao.selectUser(uid);
    };
    public TermsVO selectTerms(){
        return dao.selectTerms();
    };
    public List<UserVO> selectUsers(){
        return dao.selectUsers();
    };
    public void updateUser(UserVO vo){
        dao.updateUser(vo);
    };
    public void deleteUser(String uid){
        dao.deleteUser(uid);
    };
    public  int checkUid(String uid){
        int result = dao.checkUid(uid);
        return result;
    };
    public  int checkNick(String nick){
        int result = dao.checkNick(nick);
        return result;
    };
}
