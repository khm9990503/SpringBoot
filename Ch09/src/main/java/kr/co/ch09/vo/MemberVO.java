package kr.co.ch09.vo;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="member")
public class MemberVO {
	@Id
	private String uid;
	private String pass;
	private String name;
	private String hp;
	private String pos;
	private int dep;
	
	@CreationTimestamp
	private LocalDateTime rdate;
}
