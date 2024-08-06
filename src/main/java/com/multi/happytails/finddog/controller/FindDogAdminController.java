package com.multi.happytails.finddog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.multi.happytails.finddog.controller
 * fileName       : FindDogAdminController
 * author         : ehdtka
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        ehdtka       최초 생성
 */
@Controller
@RequestMapping("/admin")
public class FindDogAdminController {


    @GetMapping("/finddog/manager")
    public String manage() {

        return "/finddog/admin/findDogManage";
    }

}
