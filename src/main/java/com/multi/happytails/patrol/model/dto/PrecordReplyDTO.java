package com.multi.happytails.patrol.model.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * packageName    : com.multi.happytails.patrol.model.dto
 * fileName       : PatrolReplyDTO
 * author         : wss18
 * date           : 2024-08-01
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-01        wss18       최초 생성
 */
@Data
public class PrecordReplyDTO {
    private int precordReplyNo;
    private int precordNo;
    private int userNo;
    private String content;
    private Timestamp createtime;
    private Timestamp updatetime;
}
