package com.multi.happytails.DBTI.controller;

import com.multi.happytails.DBTI.model.dao.DBTISaveDAO;
import com.multi.happytails.DBTI.model.dto.DBTISaveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/DBTI/dog")
public class DBTIController {

    @Autowired
    private DBTISaveDAO dbtiSaveDAO;

    @GetMapping("/start")
    public String showDogForm() {
        return "DBTI/dbticontent";
    }

    @PostMapping("/saveDog")
    public String saveForm(@RequestParam("dogName") String name,
                           @RequestParam("dogBreed") String breed,
                           @RequestParam("dogBirthdate") String birthdateStr,
                           @RequestParam("dogGender") String gender,
                           Model model) {

        LocalDate birthdate = LocalDate.parse(birthdateStr);

        DBTISaveDTO dog = new DBTISaveDTO();
        dog.setName(name);
        dog.setBreed(breed);
        dog.setBirthdate(birthdate);
        dog.setGender(gender);

        dbtiSaveDAO.insertDog(dog);

        model.addAttribute("dog", dog);

        return "DBTI/save";
    }

    @PostMapping("/submitDbti")
    public String submitDbti(@RequestParam Map<String, String> params, Model model) {
        int W_C = 0;
        int T_N = 0;
        int E_I = 0;
        int A_L = 0;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            int score = Integer.parseInt(entry.getValue());

            switch (key) {
                case "question1":
                    if (score >= 3) W_C += score;  // 예시: 3 이상의 점수는 'W' 성향
                    else W_C -= score;             // 그 이하의 점수는 'C' 성향
                    break;
                case "question2":
                    if (score >= 3) T_N += score;  // 예시: 3 이상의 점수는 'T' 성향
                    else T_N -= score;             // 그 이하의 점수는 'N' 성향
                    break;
                // 다른 질문들에 대한 점수 계산 추가
                // 예: case 'question3': ...
            }
        }

        String W_C_type = W_C > 0 ? "W" : "C"; //개체성향
        String T_N_type = T_N > 0 ? "T" : "N"; //대인관계
        String E_I_type = E_I > 0 ? "E" : "I"; //타견관계
        String A_L_type = A_L > 0 ? "A" : "L"; //환경적응

        String dbtiResult = W_C_type + T_N_type + E_I_type + A_L_type;
        model.addAttribute("result", dbtiResult);

        return "DBTI/result"; // 결과를 보여줄 HTML 파일 이름
    }

    @GetMapping("/rollback")
    public String dogrollback() {
        return "redirect:/DBTI/dog/rollback";
    }
}
