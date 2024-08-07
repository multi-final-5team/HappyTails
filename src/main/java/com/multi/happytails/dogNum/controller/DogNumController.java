package com.multi.happytails.dogNum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.multi.happytails.dogNum
 * fileName       : DogNumController
 * author         : User
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        User       최초 생성
 */
@RestController
@RequestMapping("/dogNum")
public class DogNumController {

    public String testPage() {
        return "test";
    }
}
