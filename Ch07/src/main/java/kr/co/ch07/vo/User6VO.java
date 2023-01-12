package kr.co.ch07.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="user6")
public class User6VO {

	@Id
	private String name;
	private String birth;
	private int gender;
	private int age;
	private String addr;
	private String hp;
}
