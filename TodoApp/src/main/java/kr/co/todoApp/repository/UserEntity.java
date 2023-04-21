package kr.co.todoApp.repository;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="user")
public class UserEntity {
	@Id
	private String uid;
	private String pass;
	private int grade;
}
