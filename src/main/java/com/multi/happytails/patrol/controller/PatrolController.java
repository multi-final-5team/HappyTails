package com.multi.happytails.patrol.controller;






import com.multi.happytails.authentication.model.dto.CustomUser;
import com.multi.happytails.patrol.model.dto.PatrolDTO;
import com.multi.happytails.patrol.model.dto.PatrolImgDTO;
import com.multi.happytails.patrol.service.PatrolService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * The Upload service.
     */
    @Autowired
    UploadService uploadService;

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
    public String makepatrol(PatrolDTO patrolDTO , @RequestParam(value = "imageFiles") List<MultipartFile> imageFiles, @AuthenticationPrincipal CustomUser customUser){


        patrolDTO.setUserNo((int)customUser.getNo());

        System.out.println("patrolDTO >>>> " + patrolDTO);


        patrolService.patrolInsert(patrolDTO);
        PatrolDTO target = patrolService.findOnePatrol(patrolDTO.getUserNo() , patrolDTO.getName());

        System.out.println("target >>>> " + target);

        UploadDto uploadDto = new UploadDto();
        uploadDto.setForeignNo(target.getPatrolNo());
        uploadDto.setCategoryCode("Z");

        for (int i = 0; i < imageFiles.size(); i++) {
            uploadDto.setFile(imageFiles.get(i));
            uploadService.uploadInsert(uploadDto);
        }


        return "patrol/patrol";
    }

    /**
     * methodName : findAllPatrol
     * author : 우재협
     * 설명 : 모든 순찰대 조회
     *
     * @param model
     * @return patrol img dto
     */
    @GetMapping(value="findAllPatrol", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public PatrolImgDTO findAllPatrol(Model model){

        List<UploadDto> uploadDtos = new ArrayList<>();


        List<PatrolDTO> list = patrolService.findAllPatrol();

        List<Integer> patrolNoList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            patrolNoList.add(list.get(i).getPatrolNo());
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
    public PatrolDTO findOnePatrolByPatrolNo(@RequestParam(value = "parolNo") int parolNo){

        PatrolDTO patrolDTO = patrolService.findOnePatrolByPatrolNo(parolNo);

        return patrolDTO;
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
    public String patrolUpdate(PatrolDTO patrolDTO, @AuthenticationPrincipal CustomUser customUser){

        patrolDTO.setUserNo((int)customUser.getNo());

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
    public String patrolDelete(PatrolDTO patrolDTO, @AuthenticationPrincipal CustomUser customUser){

        patrolDTO.setUserNo((int)customUser.getNo());

        int result = patrolService.patrolDelete(patrolDTO.getPatrolNo());

        return "patrol/patrol";
    }
}
