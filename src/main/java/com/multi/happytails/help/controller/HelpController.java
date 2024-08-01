package com.multi.happytails.help.controller;

import com.multi.happytails.authentication.model.dto.CustomUser;
import com.multi.happytails.help.model.dto.*;
import com.multi.happytails.help.service.HelpService;
import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/help")
public class HelpController {

    @Autowired
    HelpService helpService;

    @Autowired
    UploadService uploadService;

    final String UPLOAD_INQUIRY_CODE = "I";
    final String UPLOAD_ADMIN_INQUIRY_CODE = "IA";

    @GetMapping("/inquiry/write")
    public void inquiryWriteForm() {}

    @GetMapping("/inquiry/detail")
    public void inquiryDetail(@RequestParam("inquiryNo") long inquiryNo
                            , Model model
                            , Principal principal
                            , @AuthenticationPrincipal CustomUser customUser) {

        List<UploadDto> uploadDtos = uploadService.uploadSelect(UPLOAD_INQUIRY_CODE, inquiryNo);
        InquiryResultDto inquiryResultDto = helpService.inquiryResultDetail(inquiryNo);
        InquiryDto inquiryDto = helpService.inquiryDetail(inquiryNo);

        if (inquiryResultDto != null) {
            List<UploadDto> resultUploadDtos = uploadService.uploadSelect(UPLOAD_ADMIN_INQUIRY_CODE, inquiryResultDto.getInquiryResultNo());
            model.addAttribute("resultUploadDtos", resultUploadDtos);
        }

        if (customUser != null) {
            if (inquiryDto.getResult() == 'N' && customUser.getRole().equals("ROLE_ADMIN")) {
                helpService.inquiryResultChange(InquiryDto.builder().inquiryNo(inquiryNo).result('S').build());
            }
        }

        model.addAttribute("inquiryResultDto", inquiryResultDto);
        model.addAttribute("uploadDtos", uploadDtos);
        model.addAttribute("inquiryDto", inquiryDto);
    }

    @GetMapping("/inquiry/list")
    public void inquiryList() {}
    @GetMapping("/main")
    public void helpMain() {}
    @GetMapping("/inquiry/update")
    public void inquiryUpdate(@RequestParam("inquiryNo") long inquiryNo
                            , Model model){
        InquiryDto inquiryDto = helpService.inquiryDetail(inquiryNo);
        List<UploadDto> uploadDtos = uploadService.uploadSelect(UPLOAD_INQUIRY_CODE, inquiryNo);

        model.addAttribute("uploadDtos", uploadDtos);
        model.addAttribute("inquiryDto", inquiryDto);
    }

    @GetMapping("/getCategory")
    @ResponseBody
    public List<HelpCategoryDto> getCategoryList() {
        return helpService.helpCategorySelectList();
    }

    @PostMapping("/inquiry/write")
    @ResponseBody
    public String inquiryWrite(@ModelAttribute InquiryDto inquiryDto, @RequestParam(value = "imageFiles") @Nullable List<MultipartFile> imageFiles, Principal principal) {
        System.out.println(inquiryDto.getContent());
        // login User
            inquiryDto.setWriterId(principal.getName());
        // login User

        long inquiryNo = helpService.inquiryInsert(inquiryDto);

        if (imageFiles != null && !imageFiles.isEmpty()) {
            UploadDto uploadDto = new UploadDto();
            uploadDto.setForeignNo(inquiryNo);
            uploadDto.setCategoryCode(UPLOAD_INQUIRY_CODE);

            for (int i = 0; i < imageFiles.size(); i++) {
                uploadDto.setFile(imageFiles.get(i));
                uploadService.uploadInsert(uploadDto);
            }
        }
        
        return "문의 작성이 완료 되었습니다.";
    }

    @GetMapping("/question/list")
    @ResponseBody
    public List<QuestionDto> questionList(@RequestParam("categoryCode") @Nullable String categoryCode) {
        if (categoryCode == null) {
            categoryCode = "HEL-001";
        }
        List<QuestionDto> list = helpService.questionList(categoryCode);
        return list;
    }

    @GetMapping("/inquiry/getList")
    @ResponseBody
    public ResponseEntity<?> getInquiryList(PageDto pageDto
        ,@RequestParam Map<String, Object> params
        ,@AuthenticationPrincipal CustomUser customUser) {
        System.out.println(pageDto);
        System.out.println(params);
        String nowPage = (String) params.get("nowPage");

        List<InquiryDto> list = null;

        if (nowPage == null) {
            nowPage = "1";
        }

        System.out.println(customUser.getId());
        if (!customUser.getRole().equals("ROLE_ADMIN")) {
            params.put("writer", customUser.getId());
        }

        int total = helpService.inquiryListCount(pageDto, params);


        pageDto = new PageDto(total, Integer.parseInt(nowPage),10 ,pageDto.getKeyword(),pageDto.getSearchValue());

        Map<String, Object> response = new HashMap<>();
        response.put("paging", pageDto);
        response.put("viewAll", helpService.getInquiryList(pageDto, params));

        return ResponseEntity.ok(response);

    }

    @GetMapping("/question/getList")
    @ResponseBody
    public ResponseEntity<?> getQuestionList(PageDto pageDto, @RequestParam Map<String, Object> params) {
        System.out.println(pageDto);
        System.out.println(params);
        String nowPage = (String) params.get("nowPage");

        List<InquiryDto> list = null;

        if (nowPage == null) {
            nowPage = "1";
        }

        int total = helpService.questionListCount(pageDto, params);


        pageDto = new PageDto(total, Integer.parseInt(nowPage),10 ,pageDto.getKeyword(),pageDto.getSearchValue());

        Map<String, Object> response = new HashMap<>();
        response.put("paging", pageDto);
        response.put("viewAll", helpService.getQuestionList(pageDto, params));

        return ResponseEntity.ok(response);

    }

    @PostMapping("/inquiry/delete")
    @ResponseBody
    public String inquiryDelete(@RequestParam("inquiryNo") long inquiryNo) {

        helpService.inquiryDelete(inquiryNo);
        List<UploadDto> uploadDtos = uploadService.uploadSelect(UPLOAD_INQUIRY_CODE, inquiryNo);
        if (uploadDtos != null && !uploadDtos.isEmpty()) {
            for (UploadDto uploadDto : uploadDtos) {
                uploadService.uploadDelete(uploadDto.getImageNo());
            }
        }

        return "삭제 되었습니다.";
    }


}
