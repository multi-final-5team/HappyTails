package com.multi.happytails.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.multi.happytails.main.controller
 * fileName       : AdminController
 * author         : EUN SOO
 * date           : 2024-08-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-16        EUN SOO       최초 생성
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/index")
    public void adminMain() {}

    @RequestMapping("/tables2")
    public void test() {}

    @RequestMapping("/tables1")
    public void tables1() {}

}
