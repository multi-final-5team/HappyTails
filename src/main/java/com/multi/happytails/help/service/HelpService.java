package com.multi.happytails.help.service;

import com.multi.happytails.help.model.dao.HelpMapper;
import com.multi.happytails.help.model.dto.HelpCategoryDto;
import com.multi.happytails.help.model.dto.InquiryDto;
import com.multi.happytails.help.model.dto.PageDto;
import com.multi.happytails.help.model.dto.QuestionDto;
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

    public int inquiryListCount(PageDto pageDto, Map<String,Object> searchMap) {
        return helpMapper.inquiryListCount(pageDto, searchMap);
    }

    public List<InquiryDto> getInquiryList(PageDto pageDto, Map<String,Object> searchMap) {
        System.out.println(searchMap + "=-=-==");
        return helpMapper.getInquiryList(pageDto, searchMap);
    }

    public InquiryDto inquiryDetail(long inquiryNo) {

        return helpMapper.inquiryDetail(inquiryNo);
    }
}
