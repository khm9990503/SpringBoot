package kr.co.sboard1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sboard1.dao.TermsDAO;
import kr.co.sboard1.vo.TermsVO;

@Service
public class TermsService {

	@Autowired
	public TermsDAO dao;
	
	public TermsVO selectTerms() {
		return dao.selectTerms();
	};
}
