package kr.co.farmstory.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/*
import kr.co.sboard1.entity.UserEntity;
import kr.co.sboard1.repository.UserRepo;
import kr.co.sboard1.vo.UserVO;
*/
@Service
public class SecurityUserService implements UserDetailsService {
	/*
	@Autowired
	private UserRepo repo;
	*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*
		UserEntity user = repo.findById(username).get();
		
		if(user == null) {
			System.out.println("user 없음...");
			throw new UsernameNotFoundException(username);
		}
		
		UserDetails userDts = MyUserDetails.builder()
											.user(user)
											.build();
											
									

		return userDts;
		*/
		return null;

	}

	
}
