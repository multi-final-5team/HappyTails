package com.multi.happytails.dog4cuts.model.dao;

import com.multi.happytails.dog4cuts.model.dto.Dog4CutsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : com.multi.happytails.dog4cuts.model.dao
 * fileName       : Dog4CutsDAO
 * author         : wss18
 * date           : 2024-08-06
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-06        wss18       최초 생성
 */
@Mapper
public interface Dog4CutsDAO {

    int dog4CutsInsert(int userNo);

    Dog4CutsDTO findOneDog4CutsNum(int userNo);

    List<Dog4CutsDTO> findAllDog4Cuts();

    int dog4CutsDelete(Dog4CutsDTO dog4CutsDTO);

    List<Dog4CutsDTO> findDog4CutsBySearch(int searchNo);
}
