package kr.co.todoApp.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.todoApp.vo.TodoVO;
import kr.co.todoApp.vo.UserVO;

@Mapper
@Repository
public interface UserDao {
	public int insertUser (UserVO vo);
	public int insertTodo (TodoVO vo);
	
}
