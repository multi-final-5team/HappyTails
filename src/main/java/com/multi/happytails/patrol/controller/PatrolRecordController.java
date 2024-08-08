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
import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.member.service.MemberService;
import com.multi.happytails.patrol.model.dto.*;
import com.multi.happytails.patrol.service.PatrolPlaceService;
import com.multi.happytails.patrol.service.PatrolRecordReplyService;
import com.multi.happytails.patrol.service.PatrolRecordService;
import com.multi.happytails.patrol.service.PatrolService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.annotation.Nullable;
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

    @Autowired
    PatrolRecordReplyService patrolRecordReplyService;

    @Autowired
    MemberService memberService;

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

    @RequestMapping("patrolRecordAdmin")
    public String patrolRecordAdmin(){
        return "/patrol/patrolRecordAdmin";
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
    public String patrolUpdate(PrecordDTO PrecordDTO, @RequestParam("beforePrecordNo") int beforePrecordNo , @RequestParam("precordPlace") int precordPlace,
                               @RequestParam(value = "imageFiles",required = false)List<MultipartFile> imageFiles,
                               @RequestParam(value = "imageUpdateFiles",required = false) List<MultipartFile> imageUpdateFiles,
                               @RequestParam(value = "imageDeleteImageNo",required = false) List<Long> imageDeleteImageNo,
                               @RequestParam(value = "imageUpdateImageNo",required = false) List<Long> imageUpdateImageNo){

        System.out.println("Update beforePrecordNo >> " + beforePrecordNo);
        System.out.println("Update precordPlace >> " + precordPlace);


        if (beforePrecordNo != precordPlace){
            patrolPlaceService.updatePrecordNo(PrecordDTO.getPrecordNo(), precordPlace);

            patrolPlaceService.updatePrecordNoNULL(beforePrecordNo);
        }

        int result = patrolRecordService.patrolRecordUpdate(PrecordDTO);

        System.out.println("result >> " + result);

        if (result == 1) {

            if (imageDeleteImageNo != null && !imageDeleteImageNo.isEmpty()) {
                for (int i = 0; i < imageDeleteImageNo.size(); i++) {
                    uploadService.uploadDelete(imageDeleteImageNo.get(i));
                }
            }

            if (imageUpdateFiles != null && !imageUpdateFiles.isEmpty()) {
                for (int i = 0; i < imageUpdateFiles.size(); i++) {
                    uploadService.uploadUpdate(imageUpdateImageNo.get(i), imageUpdateFiles.get(i));
                }
            }

            if (imageFiles != null && !imageFiles.isEmpty()) {
                UploadDto uploadDto = new UploadDto();
                uploadDto.setForeignNo(PrecordDTO.getPrecordNo());
                uploadDto.setCategoryCode("Y");

                for (int i = 0; i < imageFiles.size(); i++) {
                    uploadDto.setFile(imageFiles.get(i));
                    uploadService.uploadInsert(uploadDto);
                }
            }
        }

        return "/patrol/patrolRecord";
    }



    @PostMapping("patrolRecordDelete")
    public String patrolDelete(PrecordDTO PrecordDTO, @RequestParam("beforePrecordNo") int beforePrecordNo , @AuthenticationPrincipal CustomUser customUser){

        //순찰경로삭제
        patrolPlaceService.updatePrecordNoNULL(beforePrecordNo);

        //이미지 삭제
        List<UploadDto> pageIngs = uploadService.uploadSelect("Y",PrecordDTO.getPrecordNo());
        if (!pageIngs.isEmpty()) {
            for(int i = 0; i < pageIngs.size(); i++) {
                uploadService.uploadDelete(pageIngs.get(i).getImageNo());
            }
        }

        //순찰일지 댓글 삭제
        patrolRecordReplyService.repleyDeleteByPrecordNo(PrecordDTO.getPrecordNo());


        patrolRecordService.patrolRecordDelete(PrecordDTO);

        return "/patrol/patrolRecord";
    }

    @PostMapping("patrolRecordDeleteAdmin")
    public String patrolRecordDeleteAdmin(PrecordDTO PrecordDTO , @AuthenticationPrincipal CustomUser customUser){

        //순찰경로삭제
        int beforePrecordNo = patrolPlaceService.findPrecordPlaceNoByPrecordNo(PrecordDTO.getPrecordNo());
        patrolPlaceService.updatePrecordNoNULL(beforePrecordNo);

        //이미지 삭제
        List<UploadDto> pageIngs = uploadService.uploadSelect("Y",PrecordDTO.getPrecordNo());
        if (!pageIngs.isEmpty()) {
            for(int i = 0; i < pageIngs.size(); i++) {
                uploadService.uploadDelete(pageIngs.get(i).getImageNo());
            }
        }

        //순찰일지 댓글 삭제
        patrolRecordReplyService.repleyDeleteByPrecordNo(PrecordDTO.getPrecordNo());

        patrolRecordService.patrolRecordDelete(PrecordDTO);

        return "/patrol/patrolRecord";
    }


    @GetMapping(value="findAllPatrolRecord", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<PrecordDTO> findAllPatrol(){

        List<PrecordDTO> list = patrolRecordService.findAllPatrolRecord();

        for(PrecordDTO dto : list){
            MemberDTO memberDTO = memberService.findMemberByUserNo(dto.getUserNo());

            String userName = memberDTO.getName();

            dto.setUserId(userName);
        }

        return list;
    }


    @GetMapping(value="findOnePatrolRecordByPrecordNo", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public OnePatrolRecordImgDTO findOnePatrolRecordByPrecordNo(
            @RequestParam(value = "precordNo") int precordNo, @AuthenticationPrincipal CustomUser customUser){
        OnePatrolRecordImgDTO onePatrolRecordImgDTO = new OnePatrolRecordImgDTO();

        PrecordDTO precordDTO = patrolRecordService.findOnePatrolRecordByPrecordNo(precordNo);

        if (customUser != null){
            precordDTO.setUserId(customUser.getId());
        }

        onePatrolRecordImgDTO.setPrecordDTO(precordDTO);

        patrolRecordService.patrolRecordViewcountUpdate(precordNo, precordDTO.getViewcount() + 1);

        List<UploadDto> pageIngs = uploadService.uploadSelect("Y",precordNo);

        if (!pageIngs.isEmpty()) {
            onePatrolRecordImgDTO.setUploadDtos(pageIngs);
        }

        return onePatrolRecordImgDTO;
    }

    @GetMapping(value="findNonSelectedPrecourdPlace", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<PrecordPlaceDTO> findNonSelectedPrecourdPlace(@AuthenticationPrincipal() CustomUser customUser) {
        int patrolNo = patrolPlaceService.findPatrolNo((int) customUser.getNo());

        return patrolPlaceService.findNonSelectedPrecourdPlace(patrolNo);
    }


    @GetMapping(value="findPrecordPlaceNoByPrecordNo")
    @ResponseBody
    public int findPrecordPlaceNoByPrecordNo(@RequestParam(value = "precordNo") int precordNo) {
        int precordPlaceNo = patrolPlaceService.findPrecordPlaceNoByPrecordNo(precordNo);

        return precordPlaceNo;
    }

    @GetMapping("findRecordBySearch")
    @ResponseBody
    public List<PrecordDTO> findRecordBySearch(@RequestParam("searchword") String searchword) throws Exception {

        List<PrecordDTO> list = patrolRecordService.findPrecordBySearch( "%"+searchword+"%");

        return list;
    }

}
