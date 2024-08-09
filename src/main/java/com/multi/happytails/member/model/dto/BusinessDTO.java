package com.multi.happytails.member.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class BusinessDTO {

    private int businessNo;        // 사업 번호 (PK)
    private String businessId;     // 사업자 아이디 (FK)
    private String businessOwner;  // 대표자
    private String businessTel;    // 대표 번호
    private String businessNumber; // 사업자 번호(유니크)
    private String businessAddress;   // 업장주소

}