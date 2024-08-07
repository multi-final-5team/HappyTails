package com.multi.happytails.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String home() {
        return "redirect:/main/main"; // 뷰 리졸버가 index.html 또는 index.jsp 등을 찾습니다.
    }

    @RequestMapping("/main/main")
    public String homePage() {
        return "/main/main"; // 뷰 리졸버가 index.html 또는 index.jsp 등을 찾습니다.
    }
}
