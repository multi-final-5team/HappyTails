package com.multi.happytails.honor.controller;

/**
 * packageName    : com.multi.happytails.honor.controller
 * fileName       : HonorController
 * author         : EUN SOO
 * date           : 2024-08-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-19        EUN SOO       최초 생성
 */

import com.multi.happytails.honor.service.HonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/honor")
public class HonorController {

    @Autowired
    private HonorService honorService;

    @GetMapping
    public String honor(
            @RequestParam(value = "category", defaultValue = "doglove") String category,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            Model model) {

        model.addAttribute("ranking", honorService.getRanking(category, sort));
        model.addAttribute("category", category);
        model.addAttribute("sort", sort);
        System.out.println(honorService.getRanking(category, sort));

        return "honor/honor";
    }
}