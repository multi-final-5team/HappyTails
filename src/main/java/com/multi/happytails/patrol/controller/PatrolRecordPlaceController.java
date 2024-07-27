package com.multi.happytails.patrol.controller;

import com.multi.happytails.patrol.model.dto.PlacePointDTO;
import com.multi.happytails.patrol.model.dto.PlacePoointListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * packageName    : com.multi.happytails.patrol.controller
 * fileName       : PatrolRecordPlaceController
 * author         : wss18
 * date           : 2024-07-25
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-25        wss18       최초 생성
 */


@Controller
@RequiredArgsConstructor
@RequestMapping("/patrolRecordPlace")
public class PatrolRecordPlaceController {

    @RequestMapping("patrolRecordPlace")
    public String patrolRecordPlace(){
        return "/patrol/patrolRecordPlace";
    }

    @RequestMapping("patrolRecordPlaceInsert")
    public String  patrolRecordPlaceInsert(){
        return "/patrol/patrolRecordPlaceInsert";
    }

    @PostMapping("placeInsert")
    public void  placeInsert(@RequestParam(value = "poointList",required = false) PlacePoointListDTO placePoointListDTO){
        System.out.println(placePoointListDTO);
    }

}
