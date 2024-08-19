package com.multi.happytails.score.service;

import com.multi.happytails.score.model.dao.ScoreDAO;
import com.multi.happytails.score.model.dto.ScoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.multi.happytails.score.service
 * fileName       : ScoreService
 * author         : wss18
 * date           : 2024-08-13
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-13        wss18       최초 생성
 */

@Service
public class ScoreService {

    @Autowired
    ScoreDAO scoreDAO;

    public int scoreInsert(int userNo){

        return scoreDAO.scoreInsert(userNo);
    }

    public  int scoreCountByUserNo(int userNo){

        return scoreDAO.scoreCountByUserNo(userNo);
    }

    public List<ScoreDTO> findAllScore(){

        return scoreDAO.findAllScore();
    }

    public ScoreDTO findOneScoreByUserNo(int userNo){

        return scoreDAO.findOneScoreByUserNo(userNo);
    }

    public int scoreUpdate(ScoreDTO scoreDTO){

        return scoreDAO.scoreUpdate(scoreDTO);
    }

    public int scoreDelete(int no){

        return scoreDAO.scoreDelete(no);
    }

    public List<ScoreDTO> findPatrolRank(){

        return scoreDAO.findPatrolRank();
    }

    public List<ScoreDTO> getSortedByPoliceScore(String sort) {
        return scoreDAO.getSortedByPoliceScore(sort);
    }
}
