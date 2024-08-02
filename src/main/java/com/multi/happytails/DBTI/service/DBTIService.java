package com.multi.happytails.DBTI.service;

import com.multi.happytails.DBTI.model.dao.DBTISaveDAO;
import com.multi.happytails.DBTI.model.dto.DBTISaveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBTIService {

    @Autowired
    private DBTISaveDAO dbtiSaveDAO;

    public void saveDog(DBTISaveDTO dog) {
        dbtiSaveDAO.insertDog(dog);
    }

}
