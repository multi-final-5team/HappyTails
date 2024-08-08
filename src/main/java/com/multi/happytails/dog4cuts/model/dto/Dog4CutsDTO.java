package com.multi.happytails.dog4cuts.model.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * packageName    : com.multi.happytails.dog4cuts.model.dto
 * fileName       : Dog4CutsDTO
 * author         : wss18
 * date           : 2024-08-06
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-06        wss18       최초 생성
 */
@Data
public class Dog4CutsDTO {
    private int dog4cutsNo;
    private int userNo;
    private String userId;
    private Timestamp createtime;
}
