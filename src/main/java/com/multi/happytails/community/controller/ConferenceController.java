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
import java.util.List;

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
        model.addAttribute("keyword", keyword);
        model.addAttribute("conferences", conferences);
        model.addAttribute("sort", sort);
        return "community/conferencelist";
    }

    @GetMapping("/{conferenceNo}")
    public String conferenceDetail(@PathVariable("conferenceNo") Long conferenceNo,
                                   Model model,
                                   RedirectAttributes redirectAttributes,
                                   Principal principal) {

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
    public String conferenceCreate() {
        return "community/conferencecreate";
    }

    @PostMapping
    public String save(@ModelAttribute ConferenceDTO conferenceDTO,
                       @RequestParam("imageFiles") @Nullable List<MultipartFile> imageFiles,
                       Principal principal) {
        if (principal == null) {
            return "redirect:/member/login";
        }
        String userId = principal.getName();
        conferenceDTO.setUserId(userId);

        // 게시판 카테고리 코드
        conferenceDTO.setCategoryCode(categoryCode);
        conferenceDTO.setCreateTime(LocalDateTime.now());

        UploadDto uploadDto = new UploadDto();
        uploadDto.setForeignNo(conferenceService.insert(conferenceDTO));


        System.out.println("Inserting conferenceDTO: " + conferenceDTO);


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

        UploadDto uploadDto = new UploadDto();
        uploadDto.setCategoryCode(IMAGE_CODE);
        // uploadService.uploadDelete(uploadDto);

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

        model.addAttribute("conference", conference);

        return "community/conferenceupdate";
    }

    @PostMapping("/update/{conferenceNo}")
    public String update(@PathVariable Long conferenceNo,
                         @RequestParam String title,
                         @RequestParam String content,
                         @RequestParam("imageFiles") @Nullable List<MultipartFile> imageFiles,
                         Principal principal) {
        if (principal == null) {
            return "redirect:/member/login";
        }

        String userId = principal.getName();

        // 기존 게시글 조회
        ConferenceDTO conference = conferenceService.findById(conferenceNo);
        if (conference == null || !conference.getUserId().equals(userId)) {
            return "redirect:/community/conference";
        }

        conference.setTitle(title);
        conference.setContent(content);
        conferenceService.update(conference);

        // 이미지 처리
        if (imageFiles != null && !imageFiles.isEmpty()) {
            UploadDto uploadDto = new UploadDto();
            uploadDto.setForeignNo(conferenceNo);
            uploadDto.setCategoryCode(IMAGE_CODE); // 이미지 카테고리 코드

            for (MultipartFile file : imageFiles) {
                uploadDto.setFile(file);
                uploadService.uploadInsert(uploadDto); // 이미지 업로드 서비스
            }
        }

        return "redirect:/community/conference";
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
        List<ConferenceDTO> results = conferenceService.search(keyword);
        model.addAttribute("conferences", results);
        return "community/conferencelist";
    }
}
