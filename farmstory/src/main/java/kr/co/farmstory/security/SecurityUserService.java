package kr.co.farmstory.security;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class SecurityUserService implements UserDetailsService {

	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = repo.findById(username).get();
		
		if(user == null) {
			System.out.println("user 없음...");
			throw new UsernameNotFoundException(username);
		}
		
		UserDetails userDts = MyUserDetails.builder()
											.user(user)
											.build();
											
									

		return userDts;


	}

	
}
