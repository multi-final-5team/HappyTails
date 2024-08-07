package com.multi.happytails.dogNum.dao;

import com.multi.happytails.dogNum.dto.DogNumDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : com.multi.happytails.dogNum.dao
 * fileName       : DogNumDAO
 * author         : User
 * date           : 2024-08-02
 * description    : 강아지 정보를 저장하고 검색하는 DAO 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        User       최초 생성
 */
@Mapper
public interface DogNumDAO {
    void insertDogNum(DogNumDTO dogNumDTO);

    public int getCurrentDogNumNo();

    void insert(DogNumDTO dogNumDTO);

    DogNumDTO getDogInfoByDognum(String dognum);

}
