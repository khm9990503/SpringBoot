package kr.co.sboard1.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.sboard1.service.ArticleService;
import kr.co.sboard1.vo.ArticleVO;

@Controller
public class ArticleController {

	@Autowired
	private ArticleService service;
	
	@GetMapping("/list")
	public String list(Model model, String pg) {
		int start = 0;
		int currentPage = 1;
		int currentPageGroup = 1;
		int total = 0;
		int pageStartNum = 0;
		int lastPageNum = 0;
		if(pg!=null){
			currentPage = Integer.parseInt(pg);
		}
		
		start = (currentPage - 1) * 10;
		
		// 전체 게시물 갯수
		total = service.selectCountTotal();
		
		// 시작 페이지 번호
		pageStartNum = total - start;
		
		// 마지막 페이지 번호
		if(total % 10 == 0){
			lastPageNum = total / 10;
		}else{
			lastPageNum = total / 10 + 1;
		}
		
		// 페이지 그룹 start, end 번호
		currentPageGroup = (int)Math.ceil(currentPage / 10.0);
		int pageGroupStart = (currentPageGroup - 1) * 10 + 1;
		int pageGroupEnd = currentPageGroup * 10;
		if(pageGroupEnd > lastPageNum){
			pageGroupEnd = lastPageNum;
		}
		int[] result = {pageGroupStart, pageGroupEnd};
		
		
		List<ArticleVO> articles = service.selectArticles(start);
		
		model.addAttribute("pg", pg);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentPageGroup", currentPageGroup);
		model.addAttribute("pageGroupStart", result[0]);
		model.addAttribute("pageGroupEnd", result[1]);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("articles", articles);
		return "/list";
		
	}
	
	@GetMapping("/write")
	public String write() {
		return "/write";
	}
	@PostMapping("/write")
	public String write(ArticleVO vo, HttpServletRequest req) {
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		int result = service.insertArticle(vo);
		return "redirect:/list";
	}
	
	@GetMapping("/view")
	public String view() {
		return "/view";
	}
	
	@GetMapping("/modify")
	public String modify() {
		return "/modify";
	}
	
}
