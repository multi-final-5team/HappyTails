package com.multi.happytails.patrol.model.dao;

import com.multi.happytails.patrol.model.dto.PrecordPlaceDTO;
import com.multi.happytails.patrol.model.dto.PrecordPlaceHistoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName    : com.multi.happytails.patrol.model.dao
 * fileName       : PatrolPlaceDAO
 * author         : wss18
 * date           : 2024-07-29
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-29        wss18       최초 생성
 */
@Mapper
public interface PatrolPlaceDAO {

    int patrolPlaceInsert(PrecordPlaceDTO precordPlaceDTO);

    int findPatrolNo(int userNo);

    int findLastPlaceNo(int patrolNo);

    int precordPlaceHistoryInsert(PrecordPlaceHistoryDTO precordPlaceHistoryDTO);

    List<PrecordPlaceDTO> findAllPrecordPlace(int patrolNo);

    int deletePlace(PrecordPlaceDTO precordPlaceDTO);

    List<PrecordPlaceDTO> findNonSelectedPrecourdPlace(int patrolNo);

    int precordPlaceHistoryDelete(int precordPlaceNo);

    int updatePrecordNo(@Param("precordNo") int precordNo, @Param("precordPlaceNo") int precordPlaceNo);

    int findPrecordPlaceNoByPrecordNo(int precordNo);

    int updatePrecordNoNULL(int precordPlaceNo);
}
