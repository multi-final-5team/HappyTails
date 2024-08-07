package com.multi.happytails.patrol.service;

import com.multi.happytails.patrol.model.dao.PatrolRecordDAO;
import com.multi.happytails.patrol.model.dao.PrecordReplyDAO;
import com.multi.happytails.patrol.model.dto.PrecordDTO;
import com.multi.happytails.patrol.model.dto.PrecordReplyDTO;
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
public class PatrolRecordReplyService {

    @Autowired
    PrecordReplyDAO precordReplyDAO;

    public List<PrecordReplyDTO> findRepleyAllByPrecordNo(int precordNo){

        return precordReplyDAO.findRepleyAllByPrecordNo(precordNo);
    }

    public int repleyInsert(PrecordReplyDTO precordReplyDTO){

        return precordReplyDAO.repleyInsert(precordReplyDTO);
    }

    public int repleyUpdate(PrecordReplyDTO precordReplyDTO){

        return precordReplyDAO.repleyUpdate(precordReplyDTO);
    }


    public int repleyDelete(PrecordReplyDTO precordReplyDTO) {

        return precordReplyDAO.repleyDelete(precordReplyDTO);
    }

    public int repleyDeleteByPrecordNo(int precordNo) {

        return precordReplyDAO.repleyDeleteByPrecordNo(precordNo);
    }
}
