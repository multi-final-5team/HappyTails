package com.multi.happytails.member.controller;

import com.multi.happytails.member.model.dto.BusinessDTO;
import com.multi.happytails.member.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/businessRegistration")
    public void businessRegistrationPage() {

    }

    @PostMapping("/businessRegistration")
    public String processRegistration(BusinessDTO businessDTO, Model model) {
        try {
            // 현재 로그인한 사용자의 ID를 가져옵니다.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserId = authentication.getName();

            // BusinessDTO에 현재 사용자 ID를 설정합니다.
            businessDTO.setBusinessId(currentUserId);

            businessService.registerBusiness(businessDTO);
            return "redirect:/member/mypage";
        } catch (Exception e) {
            model.addAttribute("error", "사업자 등록 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "/member/businessRegistration";
        }
    }
}