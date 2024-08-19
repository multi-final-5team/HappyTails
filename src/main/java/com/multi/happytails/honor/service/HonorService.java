package com.multi.happytails.honor.service;

/**
 * packageName    : com.multi.happytails.honor.service
 * fileName       : HonorService
 * author         : EUN SOO
 * date           : 2024-08-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-19        EUN SOO       최초 생성
 */

import com.multi.happytails.community.service.DogloveService;
import com.multi.happytails.score.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HonorService {

    @Autowired
    private DogloveService dogloveService;

    @Autowired
    private ScoreService scoreService;

    public List<?> getRanking(String category, String sort) {
        if ("doglove".equals(category)) {
            return dogloveService.getSortedByRecommendation(sort);
        } else {
            return scoreService.getSortedByPoliceScore(sort);
        }
    }
}
