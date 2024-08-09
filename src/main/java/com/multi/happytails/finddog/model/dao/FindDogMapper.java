package com.multi.happytails.finddog.model.dao;

import com.multi.happytails.finddog.model.dto.FindDogDto;
import com.multi.happytails.finddog.model.dto.FindDogReplyDto;
import com.multi.happytails.help.model.dto.PageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.multi.happytails.finddog.model.dao
 * fileName       : FinddogMapper
 * author         : ehdtka
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        ehdtka       최초 생성
 */
@Mapper
public interface FindDogMapper {

    void findDogInsert(FindDogDto findDogDto);

    long getCurrentFindDogNo();

    List<FindDogDto> getFindDogList(@Param("pageDto") PageDto pageDto, @Param("searchMap") Map<String, Object> searchMap);

    int findDogListCount(@Param("pageDto") PageDto pageDto, @Param("searchMap") Map<String, Object> searchMap);

    FindDogDto findDogDetail(@Param("findDogNo") long fingDogNo);

    int findDogDelete(long findDogNo);

    int findDogUpdate(FindDogDto findDogDto);

    int findDogReplyWrite(FindDogReplyDto findDogReplyDto);

    List<FindDogReplyDto> findDogReplyList(long findDogNo);

    int findDogStateUpdate(long findDogNo);
}
