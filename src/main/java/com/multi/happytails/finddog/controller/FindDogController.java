package com.multi.happytails.finddog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.multi.happytails.finddog.controller
 * fileName       : FindDogController
 * author         : ehdtka
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        ehdtka       최초 생성
 */
@Controller
@RequestMapping("/finddog")
public class FindDogController {

    @GetMapping("/main")
    public void main() {}
    @GetMapping("/detail")
    public void detail() {}
    @GetMapping("/update")
    public void update() {}
    @GetMapping("/write")
    public void write() {}
}
