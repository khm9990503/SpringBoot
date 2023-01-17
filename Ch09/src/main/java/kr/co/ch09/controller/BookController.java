package kr.co.ch09.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.ch09.vo.BookVO;
import kr.co.ch09.vo.BooksVO;

@Controller
public class BookController {

	@GetMapping("/book/list")
	public String list(Model model,String text,int display,int start) {
		
		String clientId = "_4F0ULZ5_uLcwuJNWfY9"; //애플리케이션 클라이언트 아이디
        String clientSecret = "6YMKma224Y"; //애플리케이션 클라이언트 시크릿
		
		// naver open API 요청
		//String apiURL = "https://openapi.naver.com/v1/search/book.json?query={text}";    // JSON 결과
		
		URI uri = UriComponentsBuilder
									.fromUriString("https://openapi.naver.com")
									.path("/v1/search/book.json")
									.queryParam("query", text)
									.queryParam("display", display)
									.queryParam("start", start)
									.queryParam("sort", "sim")
									.encode()
									.build()
									.toUri();
									
		RequestEntity<Void> req = RequestEntity
											.get(uri)
											.header("X-Naver-Client-Id", clientId)
											.header("X-Naver-Client-Secret", clientSecret)
											.build();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.exchange(req, String.class);
		
		System.out.println(result);
		
		//json 파싱
		BooksVO books = null;
		try {
			ObjectMapper om = new ObjectMapper();
			books = om.readValue(result.getBody(), BooksVO.class );
		}catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		BookVO[] bookList = books.getItems();
		
		model.addAttribute("bookList",bookList);
		model.addAttribute("text",text);
		model.addAttribute("start",start);
		model.addAttribute("total",books.getTotal());
		
		return "/book/list";
	}
}
