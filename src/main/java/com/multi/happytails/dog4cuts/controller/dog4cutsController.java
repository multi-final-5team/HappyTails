package com.multi.happytails.dog4cuts.controller;

import com.multi.happytails.authentication.model.dto.CustomUser;
import com.multi.happytails.dog4cuts.model.dto.Dog4CutsDTO;
import com.multi.happytails.dog4cuts.model.dto.Dog4CutsImgDTO;
import com.multi.happytails.dog4cuts.model.dto.Dog4CutsImgPagingDTO;
import com.multi.happytails.dog4cuts.service.Dog4CutsService;
import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.member.service.MemberService;
import com.multi.happytails.patrol.model.dto.PatrolDTO;
import com.multi.happytails.patrol.model.dto.PatrolImgDTO;
import com.multi.happytails.patrol.model.dto.PrecordDTO;
import com.multi.happytails.patrol.pageable.service.PageService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import org.apache.catalina.util.StringUtil;
import org.apache.commons.codec.binary.StringUtils;
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
 * packageName    : com.multi.happytails.dog4cuts
 * fileName       : dog4cutsController
 * author         : wss18
 * date           : 2024-08-05
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-05        wss18       최초 생성
 */

@Controller
@RequestMapping("/dog4cuts")
public class dog4cutsController {

    @Autowired
    Dog4CutsService dog4CutsService;

    @Autowired
    UploadService uploadService;

    @Autowired
    MemberService memberService;

    @Autowired
    PageService pageService;

    @RequestMapping("dog4cutsview")
    public void dog4cutsview(){

    }

    @RequestMapping("dog4cutsinsert")
    public void dog4cutsinsert(){

    }

    @PostMapping("dog4CutsInsert")
    public String dog4CutsInsert(@RequestParam(value="file") MultipartFile[] file, @AuthenticationPrincipal CustomUser customUser, @RequestParam(value = "publicstate",required = false) char publicstate) {
        System.out.println(file[0]);

        Dog4CutsDTO dog4CutsDTO = new Dog4CutsDTO();
        dog4CutsDTO.setUserNo((int) customUser.getNo());

        if(publicstate == 'F'){
            dog4CutsDTO.setPublicstate('F');
        }
        else {
            dog4CutsDTO.setPublicstate('N');
        }

        dog4CutsService.dog4CutsInsert(dog4CutsDTO);

        Dog4CutsDTO target = dog4CutsService.findOneDog4CutsNum((int) customUser.getNo());

        if (file != null) {
            UploadDto uploadDto = new UploadDto();
            uploadDto.setForeignNo(target.getDog4cutsNo());
            uploadDto.setCategoryCode("X");

            for (int i = 0; i < file.length; i++) {
                uploadDto.setFile(file[i]);
                uploadService.uploadInsert(uploadDto);
            }
        }

        return "/dog4cuts/dog4cutsinsert";
    }

    @GetMapping(value="findAllDog4Cuts", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Dog4CutsImgDTO findAllDog4Cuts(Model model){

        List<UploadDto> uploadDtos = new ArrayList<>();


        List<Dog4CutsDTO> list = dog4CutsService.findAllDog4Cuts();

        List<Integer> dog4cutsNoList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            dog4cutsNoList.add(list.get(i).getDog4cutsNo());
            list.get(i).setUserId(memberService.findMemberByUserNo(list.get(i).getUserNo()).getId());
        }



        for (int dog4CutsNo : dog4cutsNoList){
            List<UploadDto> pageIngs = uploadService.uploadSelect("X",dog4CutsNo);

            if (!pageIngs.isEmpty()) {
                uploadDtos.addAll(pageIngs);
            }
        }

        Dog4CutsImgDTO dto = new Dog4CutsImgDTO();

        dto.setUploadDtos(uploadDtos);
        dto.setList(list);


        return dto;
    }

    @GetMapping(value="findPublicDog4Cuts")
    @ResponseBody
    public Dog4CutsImgDTO findPublicDog4Cuts(Model model){

        List<UploadDto> uploadDtos = new ArrayList<>();


        List<Dog4CutsDTO> list = dog4CutsService.findPublicDog4Cuts();

        List<Integer> dog4cutsNoList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            dog4cutsNoList.add(list.get(i).getDog4cutsNo());
            list.get(i).setUserId(memberService.findMemberByUserNo(list.get(i).getUserNo()).getId());
        }



        for (int dog4CutsNo : dog4cutsNoList){
            List<UploadDto> pageIngs = uploadService.uploadSelect("X",dog4CutsNo);

            if (!pageIngs.isEmpty()) {
                uploadDtos.addAll(pageIngs);
            }
        }

        Dog4CutsImgDTO dto = new Dog4CutsImgDTO();

        dto.setUploadDtos(uploadDtos);
        dto.setList(list);


        return dto;
    }

    @GetMapping(value="findAllDog4CutsByUserNo")
    @ResponseBody
    public Dog4CutsImgPagingDTO findAllDog4CutsByUserNo(@AuthenticationPrincipal CustomUser customUser, @PageableDefault(size = 10) Pageable pageable){

        List<UploadDto> uploadDtos = new ArrayList<>();

        Dog4CutsDTO dog4CutsDTO = new Dog4CutsDTO();
        dog4CutsDTO.setUserNo((int) customUser.getNo());

        Page<Dog4CutsDTO> list = pageService.getListDog4Cuts(dog4CutsDTO,pageable);

        List<Integer> dog4cutsNoList = new ArrayList<>();

        for (int i = 0; i < list.getContent().size(); i++) {
            dog4cutsNoList.add(list.getContent().get(i).getDog4cutsNo());
            list.getContent().get(i).setUserId(memberService.findMemberByUserNo(list.getContent().get(i).getUserNo()).getId());
        }



        for (int dog4CutsNo : dog4cutsNoList){
            List<UploadDto> pageIngs = uploadService.uploadSelect("X",dog4CutsNo);

            if (!pageIngs.isEmpty()) {
                uploadDtos.addAll(pageIngs);
            }
        }

        Dog4CutsImgPagingDTO dto = new Dog4CutsImgPagingDTO();

        dto.setUploadDtos(uploadDtos);
        dto.setList(list);


        return dto;
    }

    @GetMapping(value="findDog4CutsBySearch", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Dog4CutsImgDTO findDog4CutsBySearch(@RequestParam("searchword") String searchword){

        List<UploadDto> uploadDtos = new ArrayList<>();

        MemberDTO memberDTO = memberService.findMemberById(searchword);


        List<Dog4CutsDTO> list = dog4CutsService.findDog4CutsBySearch((int) memberDTO.getNo());

        List<Integer> dog4cutsNoList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            dog4cutsNoList.add(list.get(i).getDog4cutsNo());
            list.get(i).setUserId(memberService.findMemberByUserNo(list.get(i).getUserNo()).getId());
        }



        for (int dog4CutsNo : dog4cutsNoList){
            List<UploadDto> pageIngs = uploadService.uploadSelect("X",dog4CutsNo);

            if (!pageIngs.isEmpty()) {
                uploadDtos.addAll(pageIngs);
            }
        }

        Dog4CutsImgDTO dto = new Dog4CutsImgDTO();

        dto.setUploadDtos(uploadDtos);
        dto.setList(list);


        return dto;
    }

    @PostMapping("dog4CutsDelete")
    public String dog4CutsDelete(Dog4CutsDTO dog4CutsDTO, @AuthenticationPrincipal CustomUser customUser){

        dog4CutsDTO.setUserNo((int)customUser.getNo());

        List<UploadDto> pageIngs = uploadService.uploadSelect("X",dog4CutsDTO.getDog4cutsNo());

        if (!pageIngs.isEmpty()) {
            for(int i = 0; i < pageIngs.size(); i++) {
                uploadService.uploadDelete(pageIngs.get(i).getImageNo());
            }
        }

        int result = dog4CutsService.dog4CutsDelete(dog4CutsDTO);

        return "dog4cuts/dog4cutsview";
    }

    @PostMapping("changePublicDog4Cuts")
    public String changePublicDog4Cuts(Dog4CutsDTO dog4CutsDTO){

        int result = dog4CutsService.changePublicDog4Cuts(dog4CutsDTO);

        return "dog4cuts/dog4cutsview";
    }

    public static boolean isInteger(String strValue) {
        try {
            Integer.parseInt(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
