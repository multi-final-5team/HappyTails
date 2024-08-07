package com.multi.happytails.finddog.model.dto;

import lombok.*;

import java.util.Date;

/**
 * packageName    : com.multi.happytails.finddog.model.dto
 * fileName       : FindDogDto
 * author         : ehdtka
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        ehdtka       최초 생성
 */
@Getter
@Setter//
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindDogDto {
    private long findDogNo;
    private String writerId;
    private String title;
    private double longitude;
    private double latitude;
    private String area;
    private String content;
    private char state;
    private String breed;
    private Date createDate;
    private char category;
}
