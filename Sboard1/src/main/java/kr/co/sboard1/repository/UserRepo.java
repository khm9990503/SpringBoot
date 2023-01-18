package kr.co.sboard1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sboard1.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, String> {

	public int countByUid(String uid);
	public int countByNick(String nick);
}
