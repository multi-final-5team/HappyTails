package com.multi.happytails.patrol.service;

import com.multi.happytails.patrol.model.dao.PatrolRecordDAO;
import com.multi.happytails.patrol.model.dto.PatrolDTO;
import com.multi.happytails.patrol.model.dto.PrecordDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.multi.happytails.patrol.service
 * fileName       : PatrolRecordService
 * author         : wss18
 * date           : 2024-07-30
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        wss18       최초 생성
 */
@Service
public class PatrolRecordService {

    @Autowired
    PatrolRecordDAO patrolRecordDAO;

    public List<PrecordDTO> findAllPatrolRecord() {
        return patrolRecordDAO.findAllPatrolRecord();
    }

    public List<PrecordDTO> findAllPatrolRecordByUserNo(int userNo) {
        return patrolRecordDAO.findAllPatrolRecordByUserNo(userNo);
    }

    public PrecordDTO findOnePatrolRecordByPrecordNo(int precordNo) {
        return patrolRecordDAO.findOnePatrolRecordByPrecordNo(precordNo);
    }

    public int patrolRecordInsert(PrecordDTO precordDTO) {
        return patrolRecordDAO.patrolRecordInsert(precordDTO);
    }

    public PrecordDTO findOnePatrolRecord(int userNo) {
        return patrolRecordDAO.findOnePatrolRecord(userNo);
    }

    public int patrolRecordUpdate(PrecordDTO precordDTO) {
        return patrolRecordDAO.patrolRecordUpdate(precordDTO);
    }

    public int patrolRecordDelete(PrecordDTO precordDTO) {

        return patrolRecordDAO.patrolRecordDelete(precordDTO);
    }

    public int patrolRecordViewcountUpdate(@Param("precordNo") int precordNo, @Param("viewcount") int viewcount) {

        return patrolRecordDAO.patrolRecordViewcountUpdate(precordNo, viewcount);
    }

    public List<PrecordDTO> findPrecordBySearch(String searchword) {

        return patrolRecordDAO.findPrecordBySearch(searchword);
    }
}
