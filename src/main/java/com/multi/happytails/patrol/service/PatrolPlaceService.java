package com.multi.happytails.patrol.service;

import com.multi.happytails.patrol.model.dao.PatrolPlaceDAO;
import com.multi.happytails.patrol.model.dto.PrecordPlaceDTO;
import com.multi.happytails.patrol.model.dto.PrecordPlaceHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.multi.happytails.patrol.service
 * fileName       : PatrolPlaceService
 * author         : wss18
 * date           : 2024-07-29
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-29        wss18       최초 생성
 */
@Service
public class PatrolPlaceService {
    @Autowired
    PatrolPlaceDAO patrolPlaceDAO;

    public int patrolPlaceInsert(PrecordPlaceDTO precordPlaceDTO){

        return patrolPlaceDAO.patrolPlaceInsert(precordPlaceDTO);
    }

    public int findPatrolNo(int userNo){

        return patrolPlaceDAO.findPatrolNo(userNo);
    }

    public int findLastPlaceNo(int patrolNo){

        return patrolPlaceDAO.findLastPlaceNo(patrolNo);
    }

    public int precordPlaceHistoryInsert(PrecordPlaceHistoryDTO precordPlaceHistoryDTO){

        return patrolPlaceDAO.precordPlaceHistoryInsert(precordPlaceHistoryDTO);
    }

    public List<PrecordPlaceDTO> findAllPrecordPlace(int patrolNo){

        return patrolPlaceDAO.findAllPrecordPlace(patrolNo);
    }

    public int deletePlace(PrecordPlaceDTO precordPlaceDTO) {

        return patrolPlaceDAO.deletePlace(precordPlaceDTO);
    }

    public List<PrecordPlaceDTO> findNonSelectedPrecourdPlace(int patrolNo) {

        return patrolPlaceDAO.findNonSelectedPrecourdPlace(patrolNo);
    }

    public int precordPlaceHistoryDelete(int precordPlaceNo){

        return patrolPlaceDAO.precordPlaceHistoryDelete(precordPlaceNo);
    }

    public int updatePrecordNo(int precordNo, int precordPlaceNo){

        return patrolPlaceDAO.updatePrecordNo(precordNo,precordPlaceNo);
    }

    public int updatePrecordNoNULL(int precordPlaceNo){

        return patrolPlaceDAO.updatePrecordNoNULL(precordPlaceNo);
    }

    public int findPrecordPlaceNoByPrecordNo(int precordNo){

        return patrolPlaceDAO.findPrecordPlaceNoByPrecordNo(precordNo);
    }
}
