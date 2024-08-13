package com.multi.happytails.DBTI.controller;

import com.multi.happytails.DBTI.model.dao.DBTISaveDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/DBTI")
public class DBTIController {

    @Autowired
    private DBTISaveDAO dbtiSaveDAO;

    @GetMapping("/start")
    public String dbtiIntro() {
        return "DBTI/start";
    }

    @GetMapping("/dbticontent1")
    public String dbticontent1() {
        return "/DBTI/dbticontent1";
    }

    @GetMapping("/dbticontent2")
    public String dbticontent2() {
        return "/DBTI/dbticontent2";
    }

    @GetMapping("/dbticontent3")
    public String dbticontent3() {
        return "/DBTI/dbticontent3";
    }

    @GetMapping("/dbticontent4")
    public String dbticontent4() {
        return "/DBTI/dbticontent4";
    }

    @GetMapping("/result")
    public String dbtiResult() {
        return "/DBTI/result";
    }
    @PostMapping("/calculate")
    public String calculateDBTI(@RequestBody List<Integer> answers) {
        int[] scores = new int[4];  // W/C, T/N, E/I, A/L 순서

        // 질문별 점수 계산
        for (int i = 0; i < answers.size(); i++) {
            int answer = answers.get(i);
            switch (i) {
                case 0:
                case 9:
                    scores[0] += answer > 3 ? 1 : 0;  // C
                    break;
                case 1:
                case 4:
                case 7:
                    scores[2] += answer > 3 ? 1 : 0;  // E
                    break;
                case 2:
                case 10:
                case 14:
                    scores[0] += answer > 3 ? 0 : 1;  // W
                    break;
                case 3:
                case 6:
                case 11:
                    scores[1] += answer > 3 ? 0 : 1;  // T
                    break;
                case 5:
                case 13:
                    scores[3] += answer > 3 ? 1 : 0;  // A
                    break;
                case 8:
                case 12:
                    scores[1] += answer > 3 ? 1 : 0;  // N
                    break;
            }
        }
        StringBuilder dbti = new StringBuilder();
        dbti.append(scores[0] >= 2 ? 'C' : 'W');
        dbti.append(scores[1] >= 2 ? 'N' : 'T');
        dbti.append(scores[2] >= 2 ? 'E' : 'I');
        dbti.append(scores[3] >= 1 ? 'A' : 'L');

        return dbti.toString();
    }
}

