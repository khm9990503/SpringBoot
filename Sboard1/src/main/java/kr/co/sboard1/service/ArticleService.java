package kr.co.sboard1.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sboard1.dao.ArticleDAO;
import kr.co.sboard1.vo.ArticleVO;
import kr.co.sboard1.vo.FileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleService {

	@Autowired
	private ArticleDAO dao;
	
	public int insertArticle(ArticleVO vo) {
		
		int result = 0;
		MultipartFile file = vo.getFname();
		
		if(file.isEmpty()) {
			// 파일 첨부 X
			vo.setFile(0);
			result = dao.insertArticle(vo);
			
		}else {
			// 파일 첨부 O
			vo.setFile(1);
			result = dao.insertArticle(vo);
			
			// 파일 업로드
			
			FileVO fileVO = new FileVO();
			fileVO.setParent(vo.getNo());
			
			fileUpload(file, fileVO);
			
			// board_file 테이블에 등록
			dao.insertFile(fileVO);
		}
		
		return result;
	};
	public ArticleVO selectArticle(String no) {
		return dao.selectArticle(no);
	};
	public List<ArticleVO> selectArticles(int start) {
		return dao.selectArticles(start);
	};
	public void updateArticle(ArticleVO vo) {
		dao.updateArticle(vo);
	};
	public void deleteArticle(String no) {
		dao.deleteArticle(no);
	};
	
	// 파일 업로드
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath; // 프로젝트 내 가상 경로
	
	public void fileUpload(MultipartFile file, FileVO fileVO) {
		// 드라이브 내 실제 경로
		String path = new File(uploadPath).getAbsolutePath(); 
		
		String oName = file.getOriginalFilename();
		String ext = oName.substring(oName.lastIndexOf("."));
		String nName = UUID.randomUUID().toString()+ext;
		
		//
		fileVO.setOriName(oName);
		fileVO.setNewName(nName);
		
		try {
			file.transferTo(new File(path, nName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 게시물 총 갯수
	public int selectCountTotal() {
		return dao.selectCountTotal();
	}
}
