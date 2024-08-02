package com.multi.happytails.finddog.model.dto;

import lombok.*;

import java.util.Date;

/**
 * packageName    : com.multi.happytails.finddog.model.dto
 * fileName       : FindDogReplyDto
 * author         : ehdtka
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        ehdtka       최초 생성
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindDogReplyDto {
    private long FindDogReplyNo;
    private long findDogNo;
    private String writerId;
    private String content;
    private Date createDate;
}
