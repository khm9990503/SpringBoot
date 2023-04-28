package kr.co.voard2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.voard2.service.ArticleService;
import kr.co.voard2.vo.ArticleVO;



@RestController
public class ArticleController {

	@Autowired
	private ArticleService service;
	
	@GetMapping("getList")
	public Map<String, Object> list(String pg) {
		int currentPage = service.getCurrnetPage(pg);
		int start = service.getLimitStart(currentPage);
		int total = service.selectCountTotal();
		int pageStartNum = service.getPageStartNum(total, start);
		int lastPageNum = service.getLastPageNum(total);
		
		// 페이지 그룹 start, end 번호
		int pageGroupStart = service.getPageGroup(currentPage, lastPageNum)[0];
		int pageGroupEnd = service.getPageGroup(currentPage, lastPageNum)[1];
		
		
		List<ArticleVO> articles = service.selectArticles(start);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("articles", articles);
		resultMap.put("pg", pg);
		resultMap.put("currentPage", currentPage);
		resultMap.put("pageStartNum", pageStartNum-1);
		resultMap.put("lastPageNum", lastPageNum);
		resultMap.put("pageGroupStart", pageGroupStart);
		resultMap.put("pageGroupEnd", pageGroupEnd);
		
//		model.addAttribute("pg", pg);
//		model.addAttribute("pageStartNum", pageStartNum-1);
//		model.addAttribute("lastPageNum", lastPageNum);
//		model.addAttribute("currentPage", currentPage);
//		model.addAttribute("pageGroupStart", pageGroupStart);
//		model.addAttribute("pageGroupEnd", pageGroupEnd);
//		model.addAttribute("articles", articles);
		return resultMap;
		
	}
	
	@PostMapping("write")
	public int write(@RequestBody ArticleVO vo, HttpServletRequest req) {
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		return service.insertArticle(vo);
	}
	
	
	
	@GetMapping("getView")
	public ArticleVO view(Model model, int no) {
		// 게시물 들고오기
		ArticleVO article = service.selectArticle(no);
		model.addAttribute("article",article);
		// 게시물 조회수 +1
		service.updateArticleHit(no);
		// 댓글 가져오기
		return article;
	}
	
	/*
	 * @GetMapping("download") public ResponseEntity<Resource> download(int fno)
	 * throws IOException { FileVO vo =service.selectFile(fno);
	 * service.updateFileDownload(fno); ResponseEntity<Resource> respEntity =
	 * service.fileDownload(vo); return respEntity; }
	 */
	
//	@GetMapping("getModify")
//	public ArticleVO modify(Model model, int no) {
//		ArticleVO article = service.selectArticle(no);
//		
//		return article;
//	}
	@PutMapping("modify")
	public int modify(@RequestBody ArticleVO vo) {
		return service.updateArticle(vo);
	}
	
}
