package kr.co.ch07.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.co.ch07.vo.User1VO;

//@Repository jpa가 자동으로 repo로 잡음
public interface User1Repo extends JpaRepository<User1VO, String>{

	// JPA 쿼리메서드
	public User1VO findUser1VOByUid(String uid);
	public List<User1VO> findUser1VOByName(String name);
	public List<User1VO> findUser1VOByNameNot(String name);
	
	public List<User1VO> findUser1VOByUidAndName(String uid, String name);
	public List<User1VO> findUser1VOByUidOrName(String uid, String name);
	
	public List<User1VO> findUser1VOByAgeGreaterThan(int age);
	public List<User1VO> findUser1VOByAgeGreaterThanEqual(int age);
	public List<User1VO> findUser1VOByAgeLessThan(int age);
	public List<User1VO> findUser1VOByAgeLessThanEqual(int age);
	
	public List<User1VO> findUser1VOByNameLike(String name); // %name% 넣어줘야 함
	public List<User1VO> findUser1VOByNameContains(String name); // %가 자동으로 포함
	public List<User1VO> findUser1VOByNameStartsWith(String name); // name%
	public List<User1VO> findUser1VOByNameEndsWith(String name); // %name
	
	public List<User1VO> findUser1VOByOrderByName();
	public List<User1VO> findUser1VOByOrderByAgeAsc();
	public List<User1VO> findUser1VOByOrderByAgeDesc();
	public List<User1VO> findUser1VOByAgeGreaterThanOrderByAgeDesc(int age);
	
	public int countUser1VOByUid(String uid);
	public int countUser1VOByName(String name);
	
	// group, limit는 각자 찾기
	// JPQL : DB에서 조회가 아니라 Persistence Context에서 조회
	@Query("SELECT u1 FROM User1VO AS u1 WHERE age < 30")
	public List<User1VO> selectUser1UnderAge30();
	
	@Query("SELECT u1 FROM User1VO AS u1 WHERE u1.name = ?1")
	public List<User1VO> selectUser1ByName(String name);
	
	@Query("SELECT u1 FROM User1VO AS u1 WHERE u1.name = :name")
	public List<User1VO> selectUser1ByWithParam(@Param("name")String name);
}
