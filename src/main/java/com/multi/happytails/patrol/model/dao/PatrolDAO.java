package com.multi.happytails.patrol.model.dao;

/**
 * packageName    : com.multi.happytails.patrol.model.dao
 * fileName       : PatrolDAO
 * author         : wss18
 * date           : 2024-07-22
 * 설명    : 순찰대 crud DAO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        wss18       최초 생성
 */

import com.multi.happytails.patrol.model.dto.PatrolDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatrolDAO {

    int patrolInsert(PatrolDTO patrolDTO);

    List<PatrolDTO> findAllPatrol();

    PatrolDTO findOnePatrol(@Param("userNo") int userNo,@Param("name") String name);

    PatrolDTO findOnePatrolByPatrolNo(int parolNo);

    int patrolUpdate(PatrolDTO patrolDTO);

    int patrolDelete(int pnum);

}
