package com.multi.happytails.help.service;

import com.multi.happytails.help.model.dao.HelpMapper;
import com.multi.happytails.help.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HelpService {

    @Autowired
    HelpMapper helpMapper;

    public List<HelpCategoryDto> helpCategorySelectList() {
        return helpMapper.helpCategorySelectList();
    }

    public long inquiryInsert(InquiryDto inquiryDto) {
        helpMapper.inquiryInsert(inquiryDto);
        return helpMapper.getCurrentInquiryNo();
    }

    public List<QuestionDto> questionList(String categoryCode) {
        return helpMapper.questionList(categoryCode);
    }

    public int questionListCount(PageDto pageDto, Map<String,Object> searchMap) {
        return helpMapper.questionListCount(pageDto, searchMap);
    }

    public List<QuestionDto> getQuestionList(PageDto pageDto, Map<String,Object> searchMap) {
        System.out.println(searchMap + "=-=-==");
        return helpMapper.getQuestionList(pageDto, searchMap);
    }//


    public int inquiryListCount(PageDto pageDto, Map<String,Object> searchMap) {
        return helpMapper.inquiryListCount(pageDto, searchMap);
    }

    public List<InquiryDto> getInquiryList(PageDto pageDto, Map<String,Object> searchMap) {
        return helpMapper.getInquiryList(pageDto, searchMap);
    }

    public InquiryDto inquiryDetail(long inquiryNo) {

        return helpMapper.inquiryDetail(inquiryNo);
    }

    public long inquiryResultWrite(InquiryResultDto inquiryResultDto) {
        helpMapper.inquiryResultWrite(inquiryResultDto);
        return helpMapper.getCurrentInquiryResultNo();
    }

    public int inquiryResultChange(InquiryDto inquiryDto) {
        return helpMapper.inquiryResultChange(inquiryDto);
    }

    public InquiryResultDto inquiryResultDetail(long inquiryNo) {
        return helpMapper.inquiryResultDetail(inquiryNo);
    }

    public int inquiryDelete(long inquiryNo) {
        return helpMapper.inquiryDelete(inquiryNo);
    }

    public int inquiryUpdate(InquiryDto inquiryDto) {
        return helpMapper.inquiryUpdate(inquiryDto);
    }

    public int questionInsert(QuestionDto questionDto) {
        return helpMapper.questionInsert(questionDto);
    }

    public int questionDelete(long questionNo) {
        return helpMapper.questionDelete(questionNo);
    }

    public int questionUpdate(QuestionDto questionDto) {
        return helpMapper.questionUpdate(questionDto);
    }
}
