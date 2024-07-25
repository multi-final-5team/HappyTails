package com.multi.happytails.shop.model.dto;

import lombok.*;

import java.sql.Timestamp;

/**
 * packageName    : com.multi.happytails.shop.model.dto
 * fileName       : ReviewDTO.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 리뷰 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ReviewDTO {

    private int no;
    private String id;
    private int goodsNo;
    private int starRating;
    private String content;
    private Timestamp reviewDate;
}
