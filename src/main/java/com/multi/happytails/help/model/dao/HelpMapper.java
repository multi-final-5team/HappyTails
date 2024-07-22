package com.multi.happytails.help.model.dao;

import com.multi.happytails.help.model.dto.HelpCategoryDto;
import com.multi.happytails.help.model.dto.InquiryDto;
import com.multi.happytails.help.model.dto.QuestionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HelpMapper {
    public List<HelpCategoryDto> helpCategorySelectList();
    public int inquiryInsert(InquiryDto inquiryDto);
    public int getCurrentInquiryNo();

    public List<QuestionDto> questionList(String categoryCode);
}
