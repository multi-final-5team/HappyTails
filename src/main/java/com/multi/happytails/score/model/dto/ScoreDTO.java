package com.multi.happytails.score.model.dto;

import lombok.Data;

/**
 * packageName    : com.multi.happytails.score.model.dto
 * fileName       : ScoreDTO
 * author         : wss18
 * date           : 2024-08-13
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-13        wss18       최초 생성
 */

@Data
public class ScoreDTO {
    private int no;
    private int userNo;
    private int policeScore;
    private int policeScoreMonth;
    private int recommend;
    private int recommendMonth;
}
