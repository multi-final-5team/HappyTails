package com.multi.happytails.score.model.dao;

import com.multi.happytails.score.model.dto.ScoreDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName    : com.multi.happytails.score.model.dao
 * fileName       : ScoreDAO
 * author         : wss18
 * date           : 2024-08-13
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-13        wss18       최초 생성
 */

@Mapper
public interface ScoreDAO {

    int scoreInsert(int userNo);

    int scoreCountByUserNo(int userNo);

    List<ScoreDTO> findAllScore();

    ScoreDTO findOneScoreByUserNo(int userNo);

    int scoreUpdate(ScoreDTO scoreDTO);

    int scoreDelete(int no);

    List<ScoreDTO> findPatrolRank();

    List<ScoreDTO> getSortedByPoliceScore(@Param("sort") String sort);
}
