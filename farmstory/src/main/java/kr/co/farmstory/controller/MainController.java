package kr.co.farmstory.controller;

import kr.co.farmstory.service.ArticleService;
import kr.co.farmstory.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class MainController {

    @Autowired
    private ArticleService service;

    @GetMapping(value={"","index"})
    public String index(Model model){

        List<ArticleVO> grows = service.selectArticlesIDX("grow");
        List<ArticleVO> schools = service.selectArticlesIDX("school");
        List<ArticleVO> stories = service.selectArticlesIDX("story");

        model.addAttribute("grows",grows);
        model.addAttribute("schools",schools);
        model.addAttribute("stories",stories);

        return "index";
    }


}
