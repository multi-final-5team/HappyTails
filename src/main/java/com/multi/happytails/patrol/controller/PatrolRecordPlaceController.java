package com.multi.happytails.patrol.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public void patrolRecordPlace(){

    }

    @RequestMapping("patrolRecordPlaceInsert")
    public void patrolRecordPlaceInsert(){

    }

}
