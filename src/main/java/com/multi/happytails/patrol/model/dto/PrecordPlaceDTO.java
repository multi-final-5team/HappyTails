package com.multi.happytails.patrol.model.dto;

import lombok.Data;

/**
 * packageName    : com.multi.happytails.patrol.model.dto
 * fileName       : PrecordPlaceDTO
 * author         : wss18
 * date           : 2024-07-29
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-29        wss18       최초 생성
 */
@Data
public class PrecordPlaceDTO {
    private int precordPlaceNo;
    private int precordNo;
    private int patrolNo;
    private double precordTotal;
}
