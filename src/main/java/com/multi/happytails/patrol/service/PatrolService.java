package com.multi.happytails.patrol.service;

/**
 * packageName    : com.multi.happytails.patrol.service
 * fileName       : PatrolService
 * author         : wss18
 * date           : 2024-07-23
 * 설명    : 순찰대 crud 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        wss18       최초 생성
 */

import com.multi.happytails.patrol.model.dao.PatrolDAO;
import com.multi.happytails.patrol.model.dto.PatrolDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatrolService {

    @Autowired
    PatrolDAO patrolDAO;


    public int patrolInsert(PatrolDTO patrolDTO){
        return patrolDAO.patrolInsert(patrolDTO);
    }

    public List<PatrolDTO> findAllPatrol() {
        return patrolDAO.findAllPatrol();
    }

    public PatrolDTO findOnePatrol(int userNo, String name) {
        return patrolDAO.findOnePatrol(userNo, name);
    }

    public PatrolDTO findOnePatrolByPatrolNo(int patrolNo) {
        return patrolDAO.findOnePatrolByPatrolNo(patrolNo);
    }

    public int patrolUpdate(PatrolDTO patrolDTO) {
        return patrolDAO.patrolUpdate(patrolDTO);
    }

    public int patrolDelete(int pnum) {
        return patrolDAO.patrolDelete(pnum);
    }

}
