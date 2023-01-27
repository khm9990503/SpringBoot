package kr.co.farmstory.controller;

import kr.co.farmstory.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @Autowired
    private ArticleService service;

    @GetMapping(value={"","index"})
    public String index(){



        return "index";
    }


}
