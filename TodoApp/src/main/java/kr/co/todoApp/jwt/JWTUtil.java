package kr.co.todoApp.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTUtil {

	private final long TOKEN_VALIDATE_DAY = 1000 * 60 * 60 * 1; // 1 hour
	private SecretKey secretKey;
	
	public JWTUtil(@Value("${jwt.secret}") String secret) { // jwt.secret 값을 들고옴
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	// JWT에서 클레임값 추출
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		// claims : payload 안에 들어가 있는 정보들
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(secretKey)
							.build()
							.parseClaimsJws(token)
							.getBody();
		
		return claimsResolver.apply(claims);
	}
	
	// JWT에서 사용자 아이디 추출
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	// JWT 유효성 검사 (iat : 만기일)
	public boolean validateToken(String token) {
		Date expiration = getClaimFromToken(token, Claims::getExpiration);
		return !expiration.before(new Date()); // new Date() 오늘날짜 (만기일이 오늘 이전이면)
	}
	
	// HTTP 헤더에서 Token 추출
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}
	
	// JWT 생성 (토큰 발행 메서드)
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		
		Date createDate = new Date();
		Date expireDate = new Date(createDate.getTime() + TOKEN_VALIDATE_DAY);
		
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(username)
					.setIssuedAt(createDate)
					.setExpiration(expireDate)
					.signWith(secretKey, SignatureAlgorithm.HS256)
					.compact();
	}
	
}
