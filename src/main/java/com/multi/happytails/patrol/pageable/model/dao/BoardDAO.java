package com.multi.happytails.patrol.pageable.model.dao;

import com.multi.happytails.patrol.model.dto.PrecordDTO;
import com.multi.happytails.patrol.model.dto.PrecordPlaceDTO;
import com.multi.happytails.patrol.pageable.model.dto.RequestList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.multi.happytails.patrol.pageable.model.dao
 * fileName       : BoardDAO
 * author         : wss18
 * date           : 2024-08-08
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-08        wss18       최초 생성
 */
@Mapper
public interface BoardDAO {

    List<PrecordDTO> getListPrecord(RequestList<?> requestList);

    int getListPrecordCount(PrecordDTO board);
}
