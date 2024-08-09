package com.multi.happytails.main.dto;

import lombok.*;

/**
 * packageName    : com.multi.happytails.main.dto
 * fileName       : RssNewsDTO
 * author         : ShinHyeoncheol
 * date           : 2024-08-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-09        ShinHyeoncheol       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class RssNewsDTO {

    private String title;
    private String link;
    private String pubDate;
    private String description;
    private String thumbnail;
}