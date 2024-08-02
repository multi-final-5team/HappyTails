package com.multi.happytails.patrol.model.dao;

import com.multi.happytails.patrol.model.dto.PrecordReplyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : com.multi.happytails.patrol.model.dao
 * fileName       : PatrolReplyDAO
 * author         : wss18
 * date           : 2024-08-01
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-01        wss18       최초 생성
 */
@Mapper
public interface PrecordReplyDAO {

    List<PrecordReplyDTO> findRepleyAllByPrecordNo(int precordNo);

    int repleyInsert(PrecordReplyDTO precordReplyDTO);

    int repleyUpdate(PrecordReplyDTO precordReplyDTO);

    int repleyDelete(PrecordReplyDTO precordReplyDTO);
}
