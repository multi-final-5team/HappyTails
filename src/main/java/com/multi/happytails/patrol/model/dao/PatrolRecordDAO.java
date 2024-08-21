package com.multi.happytails.patrol.model.dao;

import com.multi.happytails.patrol.model.dto.PatrolDTO;
import com.multi.happytails.patrol.model.dto.PrecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatrolRecordDAO {


    List<PrecordDTO> findAllPatrolRecord();

    PrecordDTO findOnePatrolRecordByPrecordNo(int precordNo);

    int patrolRecordInsert(PrecordDTO precordDTO);

    PrecordDTO findOnePatrolRecord(int userNo);

    int patrolRecordUpdate(PrecordDTO precordDTO);

    int patrolRecordDelete(PrecordDTO precordDTO);

    int patrolRecordViewcountUpdate(@Param("precordNo") int precordNo, @Param("viewcount") int viewcount);

    List<PrecordDTO> findPrecordBySearch(String searchword);

    List<PrecordDTO> findAllPatrolRecordByUserNo(int userNo);
}
