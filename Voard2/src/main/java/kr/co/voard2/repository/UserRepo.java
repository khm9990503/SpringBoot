package kr.co.voard2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, String> {

	public int countByUid(String uid);
	public int countByNick(String nick);
}
