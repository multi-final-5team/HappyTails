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
import com.multi.happytails.patrol.pageable.service.PageService;
import com.multi.happytails.patrol.service.PatrolPlaceService;
import com.multi.happytails.patrol.service.PatrolRecordReplyService;
import com.multi.happytails.patrol.service.PatrolRecordService;
import com.multi.happytails.patrol.service.PatrolService;
import com.multi.happytails.score.model.dto.ScoreDTO;
import com.multi.happytails.score.service.ScoreService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Patrol controller.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/patrol")
public class PatrolController {

    /**
     * The Patrol service.
     */
    @Autowired
    PatrolService patrolService;

    @Autowired
    PatrolRecordService patrolRecordService;

    @Autowired
    PatrolPlaceService patrolPlaceService;

    @Autowired
    PatrolRecordReplyService patrolRecordReplyService;

    /**
     * The Upload service.
     */
    @Autowired
    UploadService uploadService;

    @Autowired
    MemberService memberService;

    @Autowired
    ScoreService scoreService;

    @Autowired
    PageService pageService;

    /**
     * methodName : patrol
     * author : 우재협
     * 설명 : 순찰대 조회 화면으로 이동
     */
    @RequestMapping("patrol")
    public void patrol(){

    }

    /**
     * methodName : patrolinsert
     * author : 우재협
     * 설명 : 순찰대 생성 화면으로 이동
     */
    @RequestMapping("patrolinsert")
    public void patrolinsert(){

    }

    /**
     * methodName : patrolview
     * author : 우재협
     * 설명 : 순찰대 상세 화면으로 이동
     */
    @RequestMapping("patrolview")
    public void patrolview(){

    }

    @RequestMapping("patrolAdmin")
    public void patrolAdmin(){

    }

    @RequestMapping("patrolRank")
    public void patrolRank(){

    }

    /**
     * methodName : makepatrol
     * author : 우재협
     * 설명 : 순찰대 생성
     *
     * @param patrol dto
     * @param image  files
     * @param custom user
     * @return string
     */
    @PostMapping("makepatrol")
    public String makepatrol(PatrolDTO patrolDTO , @RequestParam(value = "imageFiles",required = false) List<MultipartFile> imageFiles, @AuthenticationPrincipal() CustomUser customUser
    ){

        patrolDTO.setUserNo((int)customUser.getNo());

        System.out.println("patrolDTO >>>> " + patrolDTO);

        System.out.println("유저넘버 >>>> " + (int)customUser.getNo());

        PatrolDTO check = patrolService.findOnePatrol((int)customUser.getNo());

        System.out.println("check >>>> " + check);

        if(check == null){
            patrolService.patrolInsert(patrolDTO);
            PatrolDTO target = patrolService.findOnePatrol(patrolDTO.getUserNo());

            System.out.println("target >>>> " + target);

            if (imageFiles != null) {
                UploadDto uploadDto = new UploadDto();
                uploadDto.setForeignNo(target.getPatrolNo());
                uploadDto.setCategoryCode("Z");

                for (int i = 0; i < imageFiles.size(); i++) {
                    uploadDto.setFile(imageFiles.get(i));
                    uploadService.uploadInsert(uploadDto);
                }
            }
        }

        return "patrol/patrol";
    }

    /**
     * methodName : findAllPatrol
     * author : 우재협
     * 설명 : 모든 순찰대 조회
     *
     *
     * @return patrol img dto
     */
    @GetMapping(value="findAllPatrol", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public PatrolImgDTO findAllPatrol(PatrolDTO patrolDTO2 ,@PageableDefault(size = 10) Pageable pageable){

        if((patrolDTO2.getUserId() != null)&&(patrolDTO2.getUserId() != "")){
            patrolDTO2.setUserNo((int)memberService.findMemberById(patrolDTO2.getUserId()).getNo());
        }

        List<UploadDto> uploadDtos = new ArrayList<>();

        PatrolDTO patrolDTO = new PatrolDTO();

        Page<PatrolDTO> list = pageService.getListPatrol(patrolDTO,pageable);

        List<Integer> patrolNoList = new ArrayList<>();

        for (int i = 0; i < list.getContent().size(); i++) {
            patrolNoList.add(list.getContent().get(i).getPatrolNo());
        }



        for (int patrolNo : patrolNoList){
            List<UploadDto> pageIngs = uploadService.uploadSelect("Z",patrolNo);

            if (!pageIngs.isEmpty()) {
                uploadDtos.addAll(pageIngs);
            }
        }

        PatrolImgDTO dto = new PatrolImgDTO();

        dto.setUploadDtos(uploadDtos);
        dto.setList(list);


        return dto;
    }

    /**
     * methodName : findOnePatrolByPatrolNo
     * author : 우재협
     * 설명 : 순찰대 번호로 순찰대 하나 조회
     *
     * @param parol no
     * @return patrol dto
     */
    @GetMapping(value="findOnePatrolByPatrolNo", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public OnePatrolImgDTO findOnePatrolByPatrolNo(@RequestParam(value = "parolNo") int parolNo){

        OnePatrolImgDTO onePatrolImgDTO = new OnePatrolImgDTO();

        PatrolDTO patrolDTO = patrolService.findOnePatrolByPatrolNo(parolNo);

        MemberDTO memberDTO = memberService.findMemberByUserNo(patrolDTO.getUserNo());

        patrolDTO.setUserId(memberDTO.getId());


        onePatrolImgDTO.setPatrolDTO(patrolDTO);

        List<UploadDto> pageIngs = uploadService.uploadSelect("Z",parolNo);

        if (!pageIngs.isEmpty()) {
           onePatrolImgDTO.setUploadDtos(pageIngs);
        }

        System.out.println(onePatrolImgDTO);

        return onePatrolImgDTO;
    }

    /**
     * methodName : patrolUpdate
     * author : 우재협
     * 설명 : 유저번호에 해당하는 순찰대 정보 수정
     *
     * @param patrol dto
     * @param custom user
     * @return string
     */
    @PostMapping("patrolUpdate")
    public String patrolUpdate(PatrolDTO patrolDTO, @RequestParam(value = "beforeImgNo",required = false) int beforeImgNo, @RequestParam(value = "imageFiles",required = false) List<MultipartFile> imageFiles){

        List<UploadDto> pageIngs = uploadService.uploadSelect("Z",patrolDTO.getPatrolNo());

        System.out.println("beforeImgNo>>>>" + beforeImgNo);

        if (imageFiles != null) {
            if (beforeImgNo != 0){
                uploadService.uploadDelete(beforeImgNo);
            }
        }

        System.out.println("imageFiles>>>>" + imageFiles);

        if (imageFiles != null) {
            System.out.println("이미지 업데이트 생성");

            UploadDto uploadDto = new UploadDto();
            uploadDto.setForeignNo(patrolDTO.getPatrolNo());
            uploadDto.setCategoryCode("Z");

            for (int i = 0; i < imageFiles.size(); i++) {
                uploadDto.setFile(imageFiles.get(i));

                System.out.println("uploadDto >>>>" + uploadDto);

                uploadService.uploadInsert(uploadDto);
            }
        }

        System.out.println("patrolDTO >>>> " + patrolDTO);

        int result = patrolService.patrolUpdate(patrolDTO);

        return "patrol/patrol";
    }

    /**
     * methodName : patrolDelete
     * author : 우재협
     * 설명 : 유저번호에 해당하는 순찰대 정보 삭제
     *
     * @param patrol dto
     * @param custom user
     * @return string
     */
    @PostMapping("patrolDelete")
    @ResponseBody
    public String patrolDelete(PatrolDTO patrolDTO , @AuthenticationPrincipal CustomUser customUser){

        patrolDTO.setUserNo((int)customUser.getNo());

        ScoreDTO scoreDTO = scoreService.findOneScoreByUserNo((int)customUser.getNo());

        List<PrecordDTO> list = patrolRecordService.findAllPatrolRecordByUserNo((int)customUser.getNo());

        List<PrecordPlaceDTO> placeDTOList = patrolPlaceService.findAllPrecordPlace(patrolDTO.getPatrolNo());

        //기존 순찰기록 삭제
        for (int i = 0; i < placeDTOList.size(); i++) {
            patrolPlaceService.precordPlaceHistoryDelete(placeDTOList.get(i).getPrecordPlaceNo());

            patrolPlaceService.deletePlace(placeDTOList.get(i));
        }
        
        //기존 순찰일지 삭제
        for (int i = 0; i < list.size(); i++) {
            //순찰일지 댓글 삭제
            patrolRecordReplyService.repleyDeleteByPrecordNo(list.get(i).getPrecordNo());

            patrolRecordService.patrolRecordDelete(list.get(i));
        }

        //이미지 삭제
        List<UploadDto> pageIngs = uploadService.uploadSelect("Z",patrolDTO.getPatrolNo());

        if (!pageIngs.isEmpty()) {
            for(int i = 0; i < pageIngs.size(); i++) {
                uploadService.uploadDelete(pageIngs.get(i).getImageNo());
            }
        }

        //포인트 0점
        scoreDTO.setPoliceScore(0);

        scoreService.scoreUpdate(scoreDTO);



        int result = patrolService.patrolDelete(patrolDTO.getPatrolNo());

        return "순찰대가 삭제 되었습니다.";
    }

    @PostMapping("patrolDeleteAdmin")
    public String patrolDeleteAdmin(PatrolDTO patrolDTO , @AuthenticationPrincipal CustomUser customUser){

        PatrolDTO target = patrolService.findOnePatrolByPatrolNo(patrolDTO.getPatrolNo());

        ScoreDTO scoreDTO = scoreService.findOneScoreByUserNo(target.getUserNo());

        List<PrecordDTO> list = patrolRecordService.findAllPatrolRecordByUserNo(target.getUserNo());

        List<PrecordPlaceDTO> placeDTOList = patrolPlaceService.findAllPrecordPlace(patrolDTO.getPatrolNo());

        //기존 순찰기록 삭제
        for (int i = 0; i < placeDTOList.size(); i++) {
            patrolPlaceService.precordPlaceHistoryDelete(placeDTOList.get(i).getPrecordPlaceNo());

            patrolPlaceService.deletePlace(placeDTOList.get(i));
        }

        //기존 순찰일지 삭제
        for (int i = 0; i < list.size(); i++) {
            //순찰일지 댓글 삭제
            patrolRecordReplyService.repleyDeleteByPrecordNo(list.get(i).getPrecordNo());

            patrolRecordService.patrolRecordDelete(list.get(i));
        }

        //이미지 삭제
        List<UploadDto> pageIngs = uploadService.uploadSelect("Z",patrolDTO.getPatrolNo());

        if (!pageIngs.isEmpty()) {
            for(int i = 0; i < pageIngs.size(); i++) {
                uploadService.uploadDelete(pageIngs.get(i).getImageNo());
            }
        }

        //포인트 0점
        scoreDTO.setPoliceScore(0);

        scoreService.scoreUpdate(scoreDTO);



        int result = patrolService.patrolDelete(patrolDTO.getPatrolNo());

        return "patrol/patrol";
    }


    @GetMapping(value="findPatrolRank")
    public String findPatrolRank(Model model){

        List<ScoreDTO> list = scoreService.findPatrolRank();
        model.addAttribute("patrolRank", list);

        return "patrol/patrolRank::#rankTbody";
    }

    @GetMapping("patrolCheck")
    @ResponseBody
    public String patrolCheck(@AuthenticationPrincipal CustomUser customUser){
        PatrolDTO check = patrolService.findOnePatrol((int)customUser.getNo());

        String result = "0";

        if(check != null){
            result = "1";
        }

        return result;
    }

}
