package com.multi.happytails.patrol.controller;


/**
 * packageName    : com.multi.happytails.patrol.controller
 * fileName       : PatrolController
 * author         : wss18
 * date           : 2024-07-22
 * 설명    : 순찰대 crud 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        wss18       최초 생성
 */



import com.multi.happytails.authentication.model.dto.CustomUser;
import com.multi.happytails.patrol.model.dto.PatrolDTO;
import com.multi.happytails.patrol.model.dto.PatrolImgDTO;
import com.multi.happytails.patrol.model.dto.PrecordDTO;
import com.multi.happytails.patrol.model.dto.PrecordPlaceDTO;
import com.multi.happytails.patrol.service.PatrolPlaceService;
import com.multi.happytails.patrol.service.PatrolRecordService;
import com.multi.happytails.patrol.service.PatrolService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Patrol controller.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/patrolRecord")
public class PatrolRecordController {

    /**
     * The Patrol service.
     */
    @Autowired
    PatrolRecordService patrolRecordService;

    @Autowired
    PatrolPlaceService patrolPlaceService;

    /**
     * The Upload service.
     */
    @Autowired
    UploadService uploadService;



    @RequestMapping("patrolRecord")
    public String patrolRecord(){
        return "/patrol/patrolRecord";
    }


    @RequestMapping("patrolRecordInsert")
    public String patrolRecordInsert(){
        return "/patrol/patrolRecordInsert";
    }

    @RequestMapping("patrolRecordView")
    public String patrolRecordView(){
        return "/patrol/patrolRecordView";
    }


    @PostMapping("makePrecord")
    public String makePrecord(PrecordDTO precordDTO , @RequestParam("precordPlace") int precordPlace, @RequestParam(value = "imageFiles",required = false) List<MultipartFile> imageFiles , @AuthenticationPrincipal() CustomUser customUser){


        precordDTO.setUserNo((int)customUser.getNo());

        System.out.println("precordDTO >>>> " + precordDTO);

        System.out.println("유저넘버 >>>> " + (int)customUser.getNo());


        patrolRecordService.patrolRecordInsert(precordDTO);

        PrecordDTO target = patrolRecordService.findOnePatrolRecord((int)customUser.getNo());

        patrolPlaceService.updatePrecordNo(target.getPrecordNo(), precordPlace);

        System.out.println("target >>>> " + target);


        if (imageFiles != null) {
            UploadDto uploadDto = new UploadDto();
            uploadDto.setForeignNo(target.getPrecordNo());
            uploadDto.setCategoryCode("Y");

            for (int i = 0; i < imageFiles.size(); i++) {
                uploadDto.setFile(imageFiles.get(i));
                uploadService.uploadInsert(uploadDto);
            }
        }

        return "/patrol/patrolRecord";
    }


    @PostMapping("patrolRecordUpdate")
    public String patrolUpdate(PrecordDTO PrecordDTO, @RequestParam("beforePrecordNo") int beforePrecordNo , @RequestParam("precordPlace") int precordPlace){

        if (beforePrecordNo != precordPlace){
            patrolPlaceService.updatePrecordNo(PrecordDTO.getPrecordNo(), precordPlace);
        }

        patrolRecordService.patrolRecordUpdate(PrecordDTO);

        return "/patrol/patrolRecord";
    }



    @PostMapping("patrolRecordDelete")
    public String patrolDelete(PrecordDTO PrecordDTO, @RequestParam("beforePrecordNo") int beforePrecordNo , @AuthenticationPrincipal CustomUser customUser){

        //patrolPlaceService.updatePrecordNo(, beforePrecordNo);

        patrolRecordService.patrolRecordDelete(PrecordDTO);


        return "/patrol/patrolRecord";
    }


    @GetMapping(value="findAllPatrolRecord", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<PrecordDTO> findAllPatrol(){

        List<PrecordDTO> list = patrolRecordService.findAllPatrolRecord();

        return list;
    }


    @GetMapping(value="findOnePatrolRecordByPrecordNo", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public PrecordDTO findOnePatrolRecordByPrecordNo(@RequestParam(value = "precordNo") int precordNo){

        PrecordDTO precordDTO = patrolRecordService.findOnePatrolRecordByPrecordNo(precordNo);

        return precordDTO;
    }

    @GetMapping(value="findNonSelectedPrecourdPlace", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<PrecordPlaceDTO> findNonSelectedPrecourdPlace(@AuthenticationPrincipal() CustomUser customUser) {
        int patrolNo = patrolPlaceService.findPatrolNo((int) customUser.getNo());

        return patrolPlaceService.findNonSelectedPrecourdPlace(patrolNo);
    }


}
