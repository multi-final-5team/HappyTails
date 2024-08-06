package com.multi.happytails.finddog.service;

import com.multi.happytails.finddog.model.dao.FindDogMapper;
import com.multi.happytails.finddog.model.dto.FindDogDto;
import com.multi.happytails.finddog.model.dto.FindDogReplyDto;
import com.multi.happytails.help.model.dto.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.multi.happytails.finddog.service
 * fileName       : FindDogService
 * author         : ehdtka
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        ehdtka       최초 생성
 */
@Service
public class FindDogService {

    @Autowired
    FindDogMapper findDogMapper;

    public long findDogInsert(FindDogDto findDogDto) {
        findDogMapper.findDogInsert(findDogDto);
        return findDogMapper.getCurrentFindDogNo();
    }

    public int findDogListCount(PageDto pageDto, Map<String,Object> searchMap) {
        return findDogMapper.findDogListCount(pageDto, searchMap);
    }

    public List<FindDogDto> getFindDogList(PageDto pageDto, Map<String,Object> searchMap) {
        return findDogMapper.getFindDogList(pageDto, searchMap);
    }

    public FindDogDto findDogDetail(long findDogNo) {
        return findDogMapper.findDogDetail(findDogNo);
    }

    public int findDogDelete(long findDogNo) {
        return findDogMapper.findDogDelete(findDogNo);
    }

    public int findDogUpdate(FindDogDto findDogDto) {
        return findDogMapper.findDogUpdate(findDogDto);
    }

    public int findDogReplyWrite(FindDogReplyDto findDogReplyDto) {
        return findDogMapper.findDogReplyWrite(findDogReplyDto);
    }

    public List<FindDogReplyDto> findDogReplyList(long findDogNo) {
        return findDogMapper.findDogReplyList(findDogNo);
    }
}
