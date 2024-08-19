package com.multi.happytails.community.controller;

import com.multi.happytails.community.reply.service.ReplyService;
import com.multi.happytails.community.service.ChatDogService;
import com.multi.happytails.community.service.ConferenceService;
import com.multi.happytails.community.service.DogloveService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommunityAdminController {

    @Autowired
    private DogloveService dogloveService;

    @Autowired
    private ChatDogService chatDogService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ReplyService replyService;

    final String IMAGE_DOGLOVE = "L";
    final String IMAGE_CHATDOG = "C";
    final String IMAGE_CONFERENCE = "O";

    final String replyDoglove = "L";
    final String replyChatdog = "O";
    final String replyConference = "C";


    //내새꾸자랑 게시글 삭제 + 댓글 삭제 + 이미지 삭제
    @GetMapping("/doglove/delete/{dogloveNo}")
    public String deleteDoglove(@PathVariable Long dogloveNo) {
        dogloveService.delete(dogloveNo);

        replyService.replyDeleteAll("L", Math.toIntExact(dogloveNo));

        List<UploadDto> uploadDtos = uploadService.uploadSelect(IMAGE_DOGLOVE, dogloveNo);

        if (uploadDtos != null && !uploadDtos.isEmpty()) {
            for (UploadDto uploadDto : uploadDtos) {
                uploadService.uploadDelete(uploadDto.getImageNo());
            }
        }
        return "redirect:/community/doglove";
    }


    //떠들개 게시글 삭제 + 댓글 삭제 + 이미지 삭제
    @GetMapping("/chatdog/delete/{chatdogNo}")
    public String deleteChatdog(@PathVariable Long chatdogNo) {
        chatDogService.delete(chatdogNo);

        replyService.replyDeleteAll("C", Math.toIntExact(chatdogNo));


        List<UploadDto> uploadDtos = uploadService.uploadSelect(IMAGE_CHATDOG, chatdogNo);

        if (uploadDtos != null && !uploadDtos.isEmpty()) {
            for (UploadDto uploadDto : uploadDtos) {
                uploadService.uploadDelete(uploadDto.getImageNo());
            }
        }
        return "redirect:/community/chatdog";
    }


    //집사 회의 게시글 삭제 + 댓글 삭제 + 이미지 삭제
    @GetMapping("/conference/delete/{conferenceNo}")
    public String deleteConference(@PathVariable Long conferenceNo) {
        conferenceService.delete(conferenceNo);

        replyService.replyDeleteAll("O", Math.toIntExact(conferenceNo));


        List<UploadDto> uploadDtos = uploadService.uploadSelect(IMAGE_CONFERENCE, conferenceNo);

        if (uploadDtos != null && !uploadDtos.isEmpty()) {
            for (UploadDto uploadDto : uploadDtos) {
                uploadService.uploadDelete(uploadDto.getImageNo());
            }
        }
        return "redirect:/community/conference";
    }

    //내 새꾸 자랑 댓글 하나 삭제
    @GetMapping("/doglove/{dogloveNo}/reply/delete/{communityReplyNo}")
    public String deleteDogLoveReply(@PathVariable Long dogloveNo, @PathVariable int communityReplyNo) {
        replyService.deleteReply(communityReplyNo);
        System.out.println("deleteDogLoveReply ----------------------------");
        System.out.println("dogloveNo: " + dogloveNo);
        System.out.println("communityReplyNo: " + communityReplyNo);
        return "redirect:/doglove/" + dogloveNo;
    }

    @PostMapping("/community/C/{chatdogNo}/reply/delete/{communityReplyNo}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteAdminChatdogReply(
            @PathVariable Long chatdogNo,
            @PathVariable int communityReplyNo,
            Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        // 관리자 권한 확인
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            try {
                replyService.deleteReply(communityReplyNo);
                response.put("success", true);
                response.put("message", "관리자 권한으로 댓글이 성공적으로 삭제되었습니다.");
            } catch (Exception e) {
                response.put("success", false);
                response.put("message", "댓글 삭제 중 오류가 발생했습니다.");
            }
        } else {
            response.put("success", false);
            response.put("message", "관리자 권한이 없습니다.");
        }

        return ResponseEntity.ok(response);
    }

    //집사회의 댓글 하나 삭제
    @GetMapping("/conference/{conferenceNo}/reply/delete/{communityReplyNo}")
    public String deleteConferenceReply(@PathVariable Long conferenceNo, @PathVariable int communityReplyNo) {
        replyService.deleteReply(communityReplyNo);

        return "redirect:/conference/" + conferenceNo + "/reply";
    }

}