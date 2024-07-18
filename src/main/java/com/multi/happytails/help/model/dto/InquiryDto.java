package com.multi.happytails.help.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
public class InquiryDto {
    private long inquiryNo;
    private String helpCategoryCode;
    private String writerId;
    private String title;
    private String content;
    private char result; // N대기 S열람 Y완료
    private Date createDate;
}
