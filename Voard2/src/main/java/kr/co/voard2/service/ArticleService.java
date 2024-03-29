package kr.co.voard2.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.voard2.dao.ArticleDAO;
import kr.co.voard2.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleService {

	@Autowired
	private ArticleDAO dao;
	
	public int insertArticle(ArticleVO vo) {
		
		
		// 글 등록
				int result = dao.insertArticle(vo);
				// 파일 업로드
				/* FileVO fvo = fileUpload(vo); */
				// 파일 등록
				/*
				 * if(fvo != null) { dao.insertFile(fvo); }
				 */
				
				return result;	
	};
	public ArticleVO selectArticle(int no) {
		return dao.selectArticle(no);
	};
	public List<ArticleVO> selectArticles(int start) {
		return dao.selectArticles(start);
	};

	/*
	 * public FileVO selectFile(int fno) { return dao.selectFile(fno); }; public int
	 * updateFileDownload(int fno) { return dao.updateFileDownload(fno); };
	 */
	public int updateArticle(ArticleVO vo) {
		return dao.updateArticle(vo);
	};
	public int updateArticleHit(int no) {
		return dao.updateArticleHit(no);
	};
	public int deleteArticle(int no) {
		return dao.deleteArticle(no);
	};
	// 파일 다운로드 //////////////////////////////////////////////////////
	/*
	 * public ResponseEntity<Resource> fileDownload(FileVO vo) throws IOException {
	 * // String path = new File(uploadPath).getAbsolutePath()+"/"+vo.getNewName();
	 * Path path = Paths.get(uploadPath+vo.getNewName()); String contentType =
	 * Files.probeContentType(path); HttpHeaders headers = new HttpHeaders(); //
	 * 스프링에서 제공 : HttpHeaders, ResponseEntity
	 * headers.setContentDisposition(ContentDisposition .builder("attachment")
	 * .filename(vo.getOriName(), StandardCharsets.UTF_8) .build());
	 * headers.add(HttpHeaders.CONTENT_TYPE, contentType);
	 * 
	 * Resource resource = new InputStreamResource(Files.newInputStream(path));
	 * 
	 * return new ResponseEntity<>(resource, headers, HttpStatus.OK); // res 객체 }
	 */
	// 파일 업로드 ///////////////////////////////////////////////////////
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath; // 프로젝트 내 가상 경로
	
	/*
	 * public FileVO fileUpload(ArticleVO vo) { // 첨부 파일 MultipartFile file =
	 * vo.getFname(); FileVO fvo = null;
	 * 
	 * if(!file.isEmpty()) { // 시스템 경로 String path = new
	 * File(uploadPath).getAbsolutePath();
	 * 
	 * // 새 파일명 생성 String oName = file.getOriginalFilename(); String ext =
	 * oName.substring(oName.lastIndexOf(".")); String nName =
	 * UUID.randomUUID().toString()+ext;
	 * 
	 * // 파일 저장 try { file.transferTo(new File(path, nName)); } catch
	 * (IllegalStateException e) { log.error(e.getMessage()); } catch (IOException
	 * e) { log.error(e.getMessage()); }
	 * 
	 * fvo = FileVO.builder() .parent(vo.getNo()) .oriName(oName) .newName(nName)
	 * .build(); }
	 * 
	 * return fvo; }
	 */
	// 페이징 처리 시작 ///////////////////////////////////////////////////////
	// 현재 페이지 번호
	public int getCurrnetPage(String pg) {
		int currentPage = 1;
		if(pg != null) {
			currentPage = Integer.parseInt(pg);
		}
		return currentPage;
	}
	// 페이지 시작값
	public int getLimitStart(int currentPage) {
		return (currentPage-1) * 10;
	}
	// 게시물 총 갯수
	public int selectCountTotal() {
		return dao.selectCountTotal();
	}
	// 마지막 페이지 번호
	public int getLastPageNum(int total) {
		int lastPageNum = 0;
		if(total % 10 == 0){
			lastPageNum = total / 10;
		}else{
			lastPageNum = total / 10 + 1;
		}
		return lastPageNum;
	}
	// 시작 페이지 번호
	public int getPageStartNum(int total, int start) {
		return total - start;
	}
	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPageNum) {
		int currentPageGroup = (int)Math.ceil(currentPage / 10.0);
		int pageGroupStart = (currentPageGroup - 1) * 10 + 1;
		int pageGroupEnd = currentPageGroup * 10;
		if(pageGroupEnd > lastPageNum){
			pageGroupEnd = lastPageNum;
		}
		int[] result = {pageGroupStart,pageGroupEnd};
		return result;
	}
	// 페이징 처리 끝 ///////////////////////////////////////////////////////
	
}
