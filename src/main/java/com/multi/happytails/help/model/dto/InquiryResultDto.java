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
public class InquiryResultDto {
    private long inquiryResultNo;
    private long inquiryNo;
    private String content;
    private Date createDate;
}
