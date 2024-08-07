package com.multi.happytails.dogNum.service;

import com.multi.happytails.dogNum.dao.DogNumDAO;
import com.multi.happytails.dogNum.dto.DogNumDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.multi.happytails.dogNum.service
 * fileName       : DogNumService
 * author         : User
 * date           : 2024-08-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-05        User       최초 생성
 */
@Service
public class DogNumService {
    private final DogNumDAO dogNumDAO;

    @Autowired
    public DogNumService(DogNumDAO dogNumDAO) {
        this.dogNumDAO = dogNumDAO;
    }

    public long insert(DogNumDTO dogNumDTO) {
        dogNumDAO.insert(dogNumDTO);
        return dogNumDAO.getCurrentDogNumNo();
    }

    public DogNumDTO getDogInfoByDognum(String dognum) {
        return dogNumDAO.getDogInfoByDognum(dognum);

    }

    public void saveDogNum(DogNumDTO dogNumDTO) {
    }
}
