package com.multi.happytails.community.controller;

import com.multi.happytails.community.model.dto.ChatDogDTO;
import com.multi.happytails.community.reply.model.dto.ReplyDTO;
import com.multi.happytails.community.reply.service.ReplyService;
import com.multi.happytails.community.service.ChatDogService;
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
@RequestMapping("/community/chatdog")
public class ChatDogController {

    @Autowired
    private ChatDogService chatDogService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ReplyService replyService;

    final String replyCategoryCode = "C";


    final String IMAGE_CODE = "C";

    final String categoryCode = "CHATDOG_CODE";

    @GetMapping
    public String chatDogList(
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        List<ChatDogDTO> chatdogs;
        if ("recommendCount".equals(sort)) {
            chatdogs = chatDogService.findAllSortedByRecommendation();
        } else {
            chatdogs = chatDogService.findAllSortedByDate();
        }

        Map<Long, List<UploadDto>> chatdogImages = new HashMap<>();
        for (ChatDogDTO chatdog : chatdogs) {
            List<UploadDto> imageFiles = uploadService.uploadSelect(IMAGE_CODE, chatdog.getChatdogNo());
            chatdogImages.put(chatdog.getChatdogNo(), imageFiles);
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("chatdog", chatdogs);
        model.addAttribute("chatdogImages", chatdogImages);
        model.addAttribute("sort", sort);

        return "community/chatdoglist";
    }

    @GetMapping("/{chatdogNo}")
    public String chatdogDetail(@PathVariable("chatdogNo") Long chatdogNo,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                Principal principal) {

        ChatDogDTO chatdog = chatDogService.findById(chatdogNo);
        //댓글 조회
        List<ReplyDTO> reply = replyService.getReplyByForeignNo(replyCategoryCode, Math.toIntExact(chatdogNo));

        List<UploadDto> uploadDtoList = uploadService.uploadSelect(IMAGE_CODE, chatdogNo);
        UploadDto uploadDto = UploadDto.builder().categoryCode(categoryCode).foreignNo(chatdogNo).build();


        // 조회된 데이터가 있을 경우
        if (chatdog != null) {
            model.addAttribute("chatdog", chatdog);
            model.addAttribute("reply", reply);
            model.addAttribute("imageFiles", uploadDtoList);
            model.addAttribute("uploadDto", uploadDto);
            return "community/chatdogdetail";
        } else {
            return "redirect:/community/chatdog";
        }

    }

    @GetMapping("/create")
    public String chatdogCreate(Principal principal) {
        if (principal == null) {
            return "redirect:/member/login";
        }

        return "community/chatdogcreate";
    }

    @PostMapping
    public String save(@ModelAttribute ChatDogDTO chatDogDTO,
                       @RequestParam("imageFiles") @Nullable List<MultipartFile> imageFiles,
                       Principal principal) {

        String userId = principal.getName();
        chatDogDTO.setUserId(userId);

        //게시판 카테고리 코드
        chatDogDTO.setCategoryCode(categoryCode);
        chatDogDTO.setCreateTime(LocalDateTime.now());

        UploadDto uploadDto = new UploadDto();
        uploadDto.setForeignNo(chatDogService.insert(chatDogDTO));

        uploadDto.setCategoryCode(IMAGE_CODE); // 이미지 카테고리 코드
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (int i = 0; i < imageFiles.size(); i++) {
                uploadDto.setFile(imageFiles.get(i));
                uploadService.uploadInsert(uploadDto);
            }
        }

        return "redirect:/community/chatdog";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long chatdogNo, Principal principal) {
        if (principal == null) {
            return "redirect:/member/login";
        }
        String userId = principal.getName();


        ChatDogDTO chatDog = chatDogService.findById(chatdogNo);

        chatDogService.delete(chatdogNo);

        replyService.replyDeleteAll("C", chatdogNo);

        List<UploadDto> uploadDtos = uploadService.uploadSelect(IMAGE_CODE, chatdogNo);

        if (uploadDtos != null && !uploadDtos.isEmpty()) {
            for (UploadDto uploadDto : uploadDtos) {
                uploadService.uploadDelete(uploadDto.getImageNo());
            }
        }
        return "redirect:/community/chatdog";
    }

    @GetMapping("/update/{chatdogNo}")
    public String updateForm(@PathVariable Long chatdogNo, Model model, Principal principal ) {
        if (principal == null) {
            return "redirect:/member/login";
        }
        String userId = principal.getName();

        ChatDogDTO chatdog = chatDogService.findById(chatdogNo);
        if (chatdog == null || !chatdog.getUserId().equals(userId)) {
            return "redirect:/community/chatdog";
        }

        model.addAttribute("chatdog", chatdog);

        return "community/chatdogupdate";
    }

    @PostMapping("/update/{chatdogNo}")
    public String update(@PathVariable  Long chatdogNo,
                         @RequestParam String title,
                         @RequestParam String content,
                         @RequestParam("imageFiles") @Nullable List<MultipartFile> imageFiles,
                         Principal principal) {

        String userId = principal.getName();

        // 기존 게시글 조회
        ChatDogDTO chatdog = chatDogService.findById(chatdogNo);
        if (chatdog == null || !chatdog.getUserId().equals(userId)) {
            return "redirect:/community/chatdog";
        }

        chatdog.setTitle(title);
        chatdog.setContent(content);
        chatDogService.update(chatdog);

        // 이미지 처리
        if (imageFiles != null && !imageFiles.isEmpty()) {
            UploadDto uploadDto = new UploadDto();
            uploadDto.setForeignNo(chatdogNo);
            uploadDto.setCategoryCode(IMAGE_CODE); // 이미지 카테고리 코드

            for (MultipartFile file : imageFiles) {
                uploadDto.setFile(file);
                uploadService.uploadInsert(uploadDto); // 이미지 업로드 서비스
            }
        }

        return "redirect:/community/chatdog";
    }

    @PostMapping("/chatdogrecommend")
    public String chatdogrecommend(@RequestParam Long chatdogNo,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes
    ) {
        if (principal == null) {
            return "redirect:/member/login";
        }
        String userId = principal.getName();
        chatDogService.cdcommendCount(chatdogNo, userId);
        redirectAttributes.addAttribute("chatdogNo", chatdogNo);
        return "redirect:/community/chatdog/{chatdogNo}";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<ChatDogDTO> results = chatDogService.search(keyword);
        model.addAttribute("chatdog", results);
        return "community/chatdoglist";
    }
}
