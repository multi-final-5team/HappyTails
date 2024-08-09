package com.multi.happytails.community.controller;

import com.multi.happytails.community.model.dto.ConferenceDTO;
import com.multi.happytails.community.reply.model.dto.ReplyDTO;
import com.multi.happytails.community.reply.service.ReplyService;
import com.multi.happytails.community.service.ConferenceService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community/conference")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ReplyService replyService;

    final String replyCategoryCode = "O";
    final String IMAGE_CODE = "O";
    final String categoryCode = "CONFERENCE_CODE";

    @GetMapping
    public String conferenceList(
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        List<ConferenceDTO> conferences;
        if ("recommendCount".equals(sort)) {
            conferences = conferenceService.findAllSortedByRecommendation();
        } else {
            conferences = conferenceService.findAllSortedByDate();
        }

        Map<Long, List<UploadDto>> conferenceImages = new HashMap<>();
        for (ConferenceDTO conference : conferences) {
            List<UploadDto> imageFiles = uploadService.uploadSelect(IMAGE_CODE, conference.getConferenceNo());
            conferenceImages.put(conference.getConferenceNo(), imageFiles);
        }


        model.addAttribute("keyword", keyword);
        model.addAttribute("conference", conferences);
        model.addAttribute("conferenceImages", conferenceImages);
        model.addAttribute("sort", sort);

        return "community/conferencelist";
    }

    @GetMapping("/{conferenceNo}")
    public String conferenceDetail(@PathVariable("conferenceNo") Long conferenceNo,
                                   Model model) {

        ConferenceDTO conference = conferenceService.findById(conferenceNo);
        // 댓글 조회
        List<ReplyDTO> reply = replyService.getReplyByForeignNo(replyCategoryCode, Math.toIntExact(conferenceNo));

        List<UploadDto> uploadDtoList = uploadService.uploadSelect(IMAGE_CODE, conferenceNo);
        UploadDto uploadDto = UploadDto.builder().categoryCode(categoryCode).foreignNo(conferenceNo).build();

        // 조회된 데이터가 있을 경우
        if (conference != null) {
            model.addAttribute("conference", conference);
            model.addAttribute("reply", reply);
            model.addAttribute("imageFiles", uploadDtoList);
            model.addAttribute("uploadDto", uploadDto);
            return "community/conferencedetail";
        } else {
            return "redirect:/community/conference";
        }
    }

    @GetMapping("/create")
    public String conferenceCreat(Principal principal) {
        if (principal == null) {
            return "redirect:/member/login";
        }
        return "community/conferencecreate";
    }

    @PostMapping
    public String save(@ModelAttribute ConferenceDTO conferenceDTO,
                       @RequestParam("imageFiles") @Nullable List<MultipartFile> imageFiles,
                       Principal principal, Model model) {

        String userId = principal.getName();
        conferenceDTO.setUserId(userId);

        // 게시판 카테고리 코드
        conferenceDTO.setCategoryCode(categoryCode);
        conferenceDTO.setCreateTime(LocalDateTime.now());

        UploadDto uploadDto = new UploadDto();
        uploadDto.setForeignNo(conferenceService.insert(conferenceDTO));

        if (conferenceDTO.getTitle() == null || conferenceDTO.getTitle().trim().isEmpty() ||
                conferenceDTO.getContent() == null || conferenceDTO.getContent().trim().isEmpty()) {
            model.addAttribute("errorMessage", "제목과 내용은 필수 입력 항목입니다.");
            return "community/conferencecreate"; //
        }

        uploadDto.setCategoryCode(IMAGE_CODE); // 이미지 카테고리 코드
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile file : imageFiles) {
                uploadDto.setFile(file);
                uploadService.uploadInsert(uploadDto);
            }
        }

        return "redirect:/community/conference";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long conferenceNo, Principal principal) {
        if (principal == null) {
            return "redirect:/member/login";
        }
        String userId = principal.getName();

        ConferenceDTO conference = conferenceService.findById(conferenceNo);

        conferenceService.delete(conferenceNo);

        replyService.replyDeleteAll("C", conferenceNo);

        List<UploadDto> uploadDtos = uploadService.uploadSelect(IMAGE_CODE, conferenceNo);

        if (uploadDtos != null && !uploadDtos.isEmpty()) {
            for (UploadDto uploadDto : uploadDtos) {
                uploadService.uploadDelete(uploadDto.getImageNo());
            }
        }
        return "redirect:/community/conference";
    }

    @GetMapping("/update/{conferenceNo}")
    public String updateForm(@PathVariable Long conferenceNo, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/member/login";
        }
        String userId = principal.getName();

        ConferenceDTO conference = conferenceService.findById(conferenceNo);
        if (conference == null || !conference.getUserId().equals(userId)) {
            return "redirect:/community/conference";
        }
        List<UploadDto> uploadDtos = uploadService.uploadSelect(IMAGE_CODE, conferenceNo);

        model.addAttribute("conference", conference);
        model.addAttribute("uploadDtos", uploadDtos);

        return "community/conferenceupdate";
    }

    @PostMapping("/update/{conferenceNo}")
    public String update(@PathVariable Long conferenceNo,
                         @ModelAttribute ConferenceDTO conferenceDTO,
                         @RequestParam("title") String title,
                         @RequestParam("content") String content,
                         @RequestParam(value = "imageFiles") @Nullable List<MultipartFile> imageFiles,
                         @RequestParam(value = "imageUpdateFiles") @Nullable List<MultipartFile> imageUpdateFiles,
                         @RequestParam(value = "imageDeleteImageNo") @Nullable List<Long> imageDeleteImageNo,
                         @RequestParam(value = "imageUpdateImageNo") @Nullable List<Long> imageUpdateImageNo,
                         Principal principal) {

        String userId = principal.getName();

        // 기존 게시글 조회
        ConferenceDTO conference = conferenceService.findById(conferenceNo);
        if (conference == null || !conference.getUserId().equals(userId)) {
            return "redirect:/community/conference";
        }

        int result = conferenceService.update(conferenceDTO);

        if (result == 1) {
            // 이미지 삭제
            if (imageDeleteImageNo != null && !imageDeleteImageNo.isEmpty()) {
                for (Long imageNo : imageDeleteImageNo) {
                    uploadService.uploadDelete(imageNo);
                }
            }

            // 이미지 업데이트
            if (imageUpdateFiles != null && !imageUpdateFiles.isEmpty() && imageUpdateImageNo != null) {
                for (int i = 0; i < imageUpdateFiles.size(); i++) {
                    if (i < imageUpdateImageNo.size()) {
                        Long imageNo = imageUpdateImageNo.get(i);
                        MultipartFile file = imageUpdateFiles.get(i);
                        uploadService.uploadUpdate(imageNo, file);
                    }
                }
            }

            // 새 이미지 추가
            if (imageFiles != null && !imageFiles.isEmpty()) {
                UploadDto uploadDto = new UploadDto();
                uploadDto.setForeignNo(conferenceDTO.getConferenceNo());
                uploadDto.setCategoryCode(IMAGE_CODE);

                for (MultipartFile file : imageFiles) {
                    uploadDto.setFile(file);
                    uploadService.uploadInsert(uploadDto);
                }
            }
        }

        return "redirect:/community/doglove";
    }


    @PostMapping("/conferencerecommend")
    public String conferencerecommend(@RequestParam Long conferenceNo,
                                      Principal principal,
                                      RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/member/login";
        }
        String userId = principal.getName();
        conferenceService.cfcommendCount(conferenceNo, userId);
        redirectAttributes.addAttribute("conferenceNo", conferenceNo);
        return "redirect:/community/conference/{conferenceNo}";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<ConferenceDTO> results;

        if (keyword == null || keyword.trim().isEmpty()) {
            // 공백만 입력된 경우 전체 데이터를 검색
            results = conferenceService.findAllSortedByDate();
        } else {
            // 유효한 검색어가 있는 경우 해당 키워드로 검색
            results = conferenceService.cfsearch(keyword.trim());
        }

        model.addAttribute("conference", results);
        model.addAttribute("keyword", keyword);

        return "community/conferencelist";
    }
}
