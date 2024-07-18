package com.multi.happytails.help.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class QuestionDto {
    private long questionNo;
    private String helpCategoryCode;
    private String questionContent;
    private String answerContent;
}
