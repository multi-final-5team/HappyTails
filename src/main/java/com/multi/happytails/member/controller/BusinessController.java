package com.multi.happytails.member.controller;

import com.multi.happytails.member.model.dto.BusinessDTO;
import com.multi.happytails.member.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/businessSignUp")
    public void businessSignUpPage() {

    }

    @PostMapping("/businessSignUp")
    public String processSignup(BusinessDTO businessDTO, Model model) {
        try {
            businessService.registerBusiness(businessDTO);
            return "redirect:/member/mypage";
        } catch (Exception e) {
            model.addAttribute("error", "사업자 등록 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "businessSignUp";
        }
    }
}