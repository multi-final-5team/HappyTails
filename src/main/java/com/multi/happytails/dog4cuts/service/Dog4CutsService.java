package com.multi.happytails.dog4cuts.service;

import com.multi.happytails.dog4cuts.model.dao.Dog4CutsDAO;
import com.multi.happytails.dog4cuts.model.dto.Dog4CutsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Dog4CutsService {

    @Autowired
    Dog4CutsDAO dog4CutsDAO;

    public int dog4CutsInsert(Dog4CutsDTO dog4CutsDTO) {

        return dog4CutsDAO.dog4CutsInsert(dog4CutsDTO);
    }

    public Dog4CutsDTO findOneDog4CutsNum(int userNo){

        return dog4CutsDAO.findOneDog4CutsNum(userNo);
    }

    public List<Dog4CutsDTO> findPublicDog4Cuts() {

        return dog4CutsDAO.findPublicDog4Cuts();
    }

    public List<Dog4CutsDTO> findAllDog4Cuts() {

        return dog4CutsDAO.findAllDog4Cuts();
    }

    public List<Dog4CutsDTO> findAllDog4CutsByUserNo(int userNo) {

        return dog4CutsDAO.findAllDog4CutsByUserNo(userNo);
    }

    public int dog4CutsDelete(Dog4CutsDTO dog4CutsDTO) {

        return dog4CutsDAO.dog4CutsDelete(dog4CutsDTO);
    }

    public int changePublicDog4Cuts(Dog4CutsDTO dog4CutsDTO){

        return dog4CutsDAO.changePublicDog4Cuts(dog4CutsDTO);
    }

    public List<Dog4CutsDTO> findDog4CutsBySearch(int searchNo) {

        return dog4CutsDAO.findDog4CutsBySearch(searchNo);
    }

}
