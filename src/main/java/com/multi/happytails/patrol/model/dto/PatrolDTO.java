package com.multi.happytails.patrol.model.dto;

/**
 * packageName    : com.multi.happytails.patrol.model.dto
 * fileName       : PatrolDTO
 * author         : wss18
 * date           : 2024-07-22
 * 설명    : 순찰대 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        wss18       최초 생성
 */

import lombok.Data;

@Data
public class PatrolDTO {
    private int patrolNo;
    private int userNo;
    private String name;
    private int age;
    private String breed;
    private String location;
}
