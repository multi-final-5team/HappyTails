package com.multi.happytails.DBTI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/DBTI")
public class DBTIController {

    @GetMapping("/start")
    public String dbtiIntro(Principal principal) {
        if (principal == null) {
            return "redirect:/member/login";
        }

        return "DBTI/start";
    }

    @GetMapping("/dbticontent")
    public String dbticontent() {
        return "DBTI/dbticontent";
    }

    @PostMapping("/result")
    @ResponseBody
    public Map<String, String> dbtiResult(@RequestBody Map<String, String> answers) {        int A = 0;
        int L = 0;
        int E = 0;
        int I = 0;
        int T = 0;
        int N = 0;
        int W = 0;
        int C = 0;

        for (int i = 0; i < answers.size(); i++) {
            String answer = answers.get(String.valueOf(i));
            switch (i % 4) {
                case 0:
                    if ("예".equals(answer)) W++; else C++;
                    break;
                case 1:
                    if ("예".equals(answer)) T++; else N++;
                    break;
                case 2:
                    if ("예".equals(answer)) E++; else I++;
                    break;
                case 3:
                    if ("예".equals(answer)) A++; else L++;
                    break;
            }
        }

        String personalityType = determineDBTI(A, L, E, I, T, N, W, C);
        String imageUrl = getImageUrlForDBTI(personalityType);


        ModelAndView modelAndView = new ModelAndView("DBTI/result");
        modelAndView.addObject("personalityType", personalityType);
        modelAndView.addObject("imageUrl", imageUrl);
        modelAndView.addObject("imageUrl", "/images/" + personalityType + ".png"); // 이미지 URL


        Map<String, String> result = new HashMap<>();
        result.put("personalityType", personalityType);
        result.put("imageUrl", imageUrl);
        return result;
    }

    private String determineDBTI(int A, int L, int E, int I, int T, int N, int W, int C) {
        StringBuilder dbti = new StringBuilder();
        dbti.append(C > W ? "C" : "W");
        dbti.append(T > N ? "T" : "N");
        dbti.append(E > I ? "E" : "I");
        dbti.append(A > L ? "A" : "L");
        return dbti.toString();
    }

    private String getImageUrlForDBTI(String dbti) {
        return "/images/" + dbti + ".png";
    }
}
