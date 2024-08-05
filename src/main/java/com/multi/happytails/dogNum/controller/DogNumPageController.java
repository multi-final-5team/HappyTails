package com.multi.happytails.dogNum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.multi.happytails.dogNum
 * fileName       : DogNumPageController
 * author         : User
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        User       최초 생성
 */
@Controller
@RequestMapping("/dogNum")
public class DogNumPageController {

    @GetMapping("/dogNumForm")
    public String showForm() {
        return "dogNum/dogNumForm";
    }
}