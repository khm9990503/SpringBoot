package kr.co.ch09.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ch09.vo.MemberVO;

public interface Member1Repo extends JpaRepository<MemberVO, String> {

}
