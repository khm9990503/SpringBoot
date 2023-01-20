package kr.co.sboard1.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.sboard1.service.ArticleService;
import kr.co.sboard1.vo.ArticleVO;
import kr.co.sboard1.vo.FileVO;

@Controller
public class ArticleController {

	@Autowired
	private ArticleService service;
	
	@GetMapping("list")
	public String list(Model model, String pg) {
		int currentPage = service.getCurrnetPage(pg);
		int start = service.getLimitStart(currentPage);
		int total = service.selectCountTotal();
		int pageStartNum = service.getPageStartNum(total, start);
		int lastPageNum = service.getLastPageNum(total);
		
		// 페이지 그룹 start, end 번호
		int pageGroupStart = service.getPageGroup(currentPage, lastPageNum)[0];
		int pageGroupEnd = service.getPageGroup(currentPage, lastPageNum)[1];
		
		
		List<ArticleVO> articles = service.selectArticles(start);
		
		model.addAttribute("pg", pg);
		model.addAttribute("pageStartNum", pageStartNum-1);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageGroupStart", pageGroupStart);
		model.addAttribute("pageGroupEnd", pageGroupEnd);
		model.addAttribute("articles", articles);
		return "list";
		
	}
	
	@GetMapping("write")
	public String write() {
		return "write";
	}
	@PostMapping("write")
	public String write(ArticleVO vo, HttpServletRequest req) {
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		service.insertArticle(vo);
		return "redirect:/list";
	}
	
	@GetMapping("view")
	public String view(Model model, int no) {
		// 게시물 들고오기
		ArticleVO article = service.selectArticle(no);
		model.addAttribute("article",article);
		// 게시물 조회수 +1
		
		// 댓글 가져오기
		return "view";
	}
	
	@GetMapping("download")
	public ResponseEntity<Resource> download(int fno) throws IOException {
		FileVO vo =service.selectFile(fno);
		service.updateFileDownload(fno);
		ResponseEntity<Resource> respEntity = service.fileDownload(vo);
		return respEntity;
	}
	
	@GetMapping("modify")
	public String modify() {
		return "modify";
	}
	
}
