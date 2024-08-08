package com.multi.happytails.patrol.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String userId;
    private String userName;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createtime;
    private Timestamp updatetime;
}
