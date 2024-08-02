package com.multi.happytails.dogNum.dao;

import com.multi.happytails.dogNum.dto.DogNumDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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
@Repository
public class DogNumDAO {

    // 강아지 정보를 저장할 Map. 키는 등록번호, 값은 DogNumDTO 객체
    private final Map<String, DogNumDTO> database = new HashMap<>();

    /**
     * 강아지 정보를 저장합니다.
     * @param dog 강아지 정보
     * @return 저장된 강아지 정보
     */
    public DogNumDTO save(DogNumDTO dog) {
        database.put(dog.getDogNum(), dog);
        return dog;
    }

    /**
     * 등록번호로 강아지 정보를 찾습니다.
     * @param dogNum 등록번호
     * @return 해당 강아지 정보
     */
    public DogNumDTO find(String dogNum) {
        return database.get(dogNum);
    }
}
