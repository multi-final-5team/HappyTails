package com.multi.happytails.help.model.dao;

import com.multi.happytails.help.model.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HelpMapper {
    public List<HelpCategoryDto> helpCategorySelectList();

    public int inquiryInsert(InquiryDto inquiryDto);

    public int getCurrentInquiryNo();

    public List<QuestionDto> questionList(String categoryCode);

    public int inquiryListCount(@Param("pageDto")PageDto pageDto, @Param("searchMap") Map<String,Object> searchMap);

    public List<InquiryDto> getInquiryList(@Param("pageDto")PageDto pageDto, @Param("searchMap")Map<String,Object> searchMap);

    public InquiryDto inquiryDetail(@Param("inquiryNo") long inquiryNo);

    int inquiryResultWrite(InquiryResultDto inquiryResultDto);

    long getCurrentInquiryResultNo();

    int inquiryResultChange(InquiryDto inquiryDto);

    InquiryResultDto inquiryResultDetail(long inquiryNo);
}
