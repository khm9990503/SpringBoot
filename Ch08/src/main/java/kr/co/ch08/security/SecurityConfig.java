package kr.co.ch08.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kr.co.ch08.service.User2Service;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 인가
		// 접근 권한 설정
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/manager/**").hasAnyRole("ADMIN","MANAGER");
		http.authorizeRequests().antMatchers("/member/**").hasAnyRole("ADMIN","MANAGER","MEMBER");
		
		// 사이트 위조 방지 설정
		http.csrf().disable();
		
		// 로그인 설정
		http.formLogin()
			.loginPage("/user2/login")
			.defaultSuccessUrl("/user2/loginSuccess")
			.usernameParameter("uid")
			.passwordParameter("pass");
			
		
		// 자동로그인 설정
		http.rememberMe()
			.rememberMeParameter("remember") // 체크박스의 name과 동일해야함
			.tokenValiditySeconds(60*60) // 만료 시간 default: 14일
			.alwaysRemember(false) // 사용자가 체크박스를 활성화하지 않아도 항상 실행 default: false 
			.userDetailsService(service); // 기능을 사용할 때 사용자 정보가 필요함. 반드시 이 설정 필요함
		
		// 로그아웃 설정
		http.logout()
					.invalidateHttpSession(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/user2/logout"))
					.logoutSuccessUrl("/user2/login");
					
		
	}
	
	
	private final User2Service service;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		// Security 사용자에 대한 권한 설정
		auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("manager").password("{noop}1234").roles("MANAGER");
		auth.inMemoryAuthentication().withUser("member").password("{noop}1234").roles("MEMBER");
		
		// 로그인인증 처리 서비스, 암호화 방식 설정 BCrypt = SHA2 + 'salt'
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
