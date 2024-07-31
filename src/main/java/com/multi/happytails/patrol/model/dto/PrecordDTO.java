package com.multi.happytails.patrol.model.dto;

import lombok.Data;

/**
 * packageName    : com.multi.happytails.patrol.model.dto
 * fileName       : patrolRecordDTO
 * author         : wss18
 * date           : 2024-07-30
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        wss18       최초 생성
 */
@Data
public class PrecordDTO {
    private int precordNo;
    private int userNo;
    private String title;
    private String content;
    private int viewcount;
}
