package com.multi.happytails.finddog.controller;

import com.multi.happytails.finddog.model.dto.FindDogDto;
import com.multi.happytails.finddog.model.dto.FindDogReplyDto;
import com.multi.happytails.finddog.service.FindDogService;
import com.multi.happytails.help.model.dto.InquiryDto;
import com.multi.happytails.help.model.dto.PageDto;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.multi.happytails.finddog.controller
 * fileName       : FindDogController
 * author         : ehdtka
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        ehdtka       최초 생성
 */
@Controller
@RequestMapping("/finddog")
public class FindDogController {

    @Autowired
    FindDogService findDogService;

    @Autowired
    UploadService uploadService;

    final String UPLOAD_FINDDOG_CODE = "F";


    @GetMapping("/main")
    public void main() {}

    @GetMapping("/detail")
    public void detail(@RequestParam("findDogNo") long findDogNo
            , Model model) {

        List<UploadDto> uploadDtos = uploadService.uploadSelect(UPLOAD_FINDDOG_CODE, findDogNo);
        FindDogDto findDogDto = findDogService.findDogDetail(findDogNo);

        model.addAttribute("uploadDtos", uploadDtos);
        model.addAttribute("findDogDto", findDogDto);

    }
    @GetMapping("/update")
    public void fingDogUpdate(@RequestParam("findDogNo") long findDogNo
            , Model model){
        FindDogDto findDogDto = findDogService.findDogDetail(findDogNo);
        List<UploadDto> uploadDtos = uploadService.uploadSelect(UPLOAD_FINDDOG_CODE, findDogNo);

        model.addAttribute("uploadDtos", uploadDtos);
        model.addAttribute("findDogDto", findDogDto);
    }

    @GetMapping("/write")
    public void write() {}

    @PostMapping("/write")
    @ResponseBody
    public String findDogWrite(@ModelAttribute FindDogDto findDogDto, @RequestParam(value = "imageFiles") @Nullable List<MultipartFile> imageFiles, Principal principal) {

        findDogDto.setWriterId(principal.getName());

        System.out.println(findDogDto);

        long inquiryNo = findDogService.findDogInsert(findDogDto);

        if (imageFiles != null && !imageFiles.isEmpty()) {
            UploadDto uploadDto = new UploadDto();
            uploadDto.setForeignNo(inquiryNo);
            uploadDto.setCategoryCode(UPLOAD_FINDDOG_CODE);

            for (int i = 0; i < imageFiles.size(); i++) {
                uploadDto.setFile(imageFiles.get(i));
                uploadService.uploadInsert(uploadDto);
            }
        }

        return "글이 작성 되었습니다.";
    }

    @GetMapping("/getList")
    @ResponseBody
    public ResponseEntity<?> getFindDogList(PageDto pageDto
            , @RequestParam Map<String, Object> params) {
        System.out.println(pageDto);
        System.out.println(params);
        String nowPage = (String) params.get("nowPage");

        List<InquiryDto> list = null;

        if (nowPage == null) {
            nowPage = "1";
        }

        int total = findDogService.findDogListCount(pageDto, params);

        pageDto = new PageDto(total, Integer.parseInt(nowPage),9 ,pageDto.getKeyword(),pageDto.getSearchValue());

        List<FindDogDto> findDogDtos = findDogService.getFindDogList(pageDto, params);

        Map<Long, String> imageMap = new HashMap<>();

        for (FindDogDto findDogDto : findDogDtos) {


            long findDogNo = findDogDto.getFindDogNo();
            List<UploadDto> uploadDtos = uploadService.uploadSelect(UPLOAD_FINDDOG_CODE, findDogNo);
            if (!uploadDtos.isEmpty()) {
                imageMap.put(findDogNo, uploadDtos.get(0).getStoredFileName());
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("paging", pageDto);
        response.put("viewAll", findDogDtos);
        response.put("imageMap", imageMap);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/delete")
    @ResponseBody
    public String findDogDelete(@RequestParam("findDogNo") long findDogNo) {

        findDogService.findDogDelete(findDogNo);
        List<UploadDto> uploadDtos = uploadService.uploadSelect(UPLOAD_FINDDOG_CODE, findDogNo);
        if (uploadDtos != null && !uploadDtos.isEmpty()) {
            for (UploadDto uploadDto : uploadDtos) {
                uploadService.uploadDelete(uploadDto.getImageNo());
            }
        }

        return "삭제 되었습니다.";
    }

    @PostMapping("/stateUpdate")
    @ResponseBody
    public String findDogStateUpdate(@RequestParam("findDogNo") long findDogNo) {

        findDogService.findDogStateUpdate(findDogNo);

        return "해결 상태로 변경 되었습니다.";
    }

    @PostMapping("/update")
    @ResponseBody
    public String findDogUpdate(@ModelAttribute FindDogDto findDogDto,
                                @RequestParam(value = "imageFiles") @Nullable List<MultipartFile> imageFiles,
                                @RequestParam(value = "imageUpdateFiles") @Nullable List<MultipartFile> imageUpdateFiles,
                                @RequestParam(value = "imageDeleteImageNo") @Nullable List<Long> imageDeleteImageNo,
                                @RequestParam(value = "imageUpdateImageNo") @Nullable List<Long> imageUpdateImageNo) {

        int result = findDogService.findDogUpdate(findDogDto);

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
                uploadDto.setForeignNo(findDogDto.getFindDogNo());
                uploadDto.setCategoryCode(UPLOAD_FINDDOG_CODE);

                for (int i = 0; i < imageFiles.size(); i++) {
                    uploadDto.setFile(imageFiles.get(i));
                    uploadService.uploadInsert(uploadDto);
                }
            }
        }

        return "글이 수정 되었습니다.";
    }

    @PostMapping("/replyWrite")
    @ResponseBody
    public String replyWrite(@ModelAttribute FindDogReplyDto findDogReplyDto,
                             Principal principal) {

        findDogReplyDto.setWriterId(principal.getName());

        findDogService.findDogReplyWrite(findDogReplyDto);

        return "댓글이 작성 되었습니다.";
    }

    @PostMapping("/replyDelte")
    @ResponseBody
    public String replyDelte(@RequestParam("findDogReplyNo") long findDogReplyNo) {

        findDogService.findDogReplyDelete(findDogReplyNo);

        return "댓글이 삭제 되었습니다.";
    }

    @PostMapping("/replyUpdate")
    @ResponseBody
    public String replyUpdate(@ModelAttribute FindDogReplyDto findDogReplyDto) {

        findDogService.findDogReplyUpdate(findDogReplyDto);

        return "댓글이 수정 되었습니다.";
    }

    @GetMapping("/getReplyList")
    @ResponseBody
    public List<FindDogReplyDto> getReplyList(@RequestParam("findDogNo") long findDogNo) {
        return findDogService.findDogReplyList(findDogNo);
    }
}
