package com.multi.happytails.member.controller;

import com.multi.happytails.member.model.dto.BusinessDTO;
import com.multi.happytails.member.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BusinessAdminController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/businessAdmin")
    public String businessAdminPage(Model model) {
        List<BusinessDTO> businesses = businessService.getAllBusinesses();
        model.addAttribute("businesses", businesses);
        return "admin/businessAdmin";
    }

    @PostMapping("/approveRegistration")
    @ResponseBody
    public String approveRegistration(@RequestParam int businessNo) {
        businessService.updateBusinessRegistrationFlag(businessNo, "R");
        return "success";
    }

    @PostMapping("/cancelRegistration")
    @ResponseBody
    public String cancelRegistration(@RequestParam int businessNo) {
        businessService.updateBusinessRegistrationFlag(businessNo, "W");
        return "success";
    }
}
