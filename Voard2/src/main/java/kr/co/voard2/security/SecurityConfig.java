package kr.co.voard2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kr.co.voard2.jwt.JwtAuthenticationFilter;


@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private SecurityUserService userService;
	
	// 기존의 configure 를 대체
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.httpBasic().disable() // 기본 로그인 UI 폼을 비활성
			.csrf().disable()	   // 브라우저를 사용하는 환경이 아니기 때문에 비활성화
			.formLogin().disable() // 폼 로그인 방식 해제
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 인증을 사용하기 때문에 Session을 끔
			.and()
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		// 인가설정은 잠시 뺌
		return http.build();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
}
