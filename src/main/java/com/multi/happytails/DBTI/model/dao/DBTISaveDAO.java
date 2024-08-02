package com.multi.happytails.DBTI.model.dao;

import com.multi.happytails.DBTI.model.dto.DBTISaveDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : com.multi.happytails.DBTI.dao
 * fileName       : DBTIdao
 * author         : User
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        User       최초 생성
 */
@Mapper
public interface DBTISaveDAO {
    void insertDog(DBTISaveDTO dbtiSaveDTO);
}
