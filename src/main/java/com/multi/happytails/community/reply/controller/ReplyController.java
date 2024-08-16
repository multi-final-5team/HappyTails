package com.multi.happytails.community.reply.controller;

import com.multi.happytails.community.reply.model.dto.ReplyDTO;
import com.multi.happytails.community.reply.service.ReplyCode;
import com.multi.happytails.community.reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/community")
public class ReplyController {

    @Autowired
    private ReplyService replyService;


    @PostMapping("/{communityCategoryCode}/{foreignNo}/reply/add")
    public String addReply(
            @PathVariable int foreignNo,
            @PathVariable String communityCategoryCode,
            @RequestParam String content,
            Principal principal) {

        if (principal == null) {
            return "redirect:/member/login";
        }

        String writerId = principal.getName();
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setCommunityCategoryCode(communityCategoryCode);
        replyDTO.setForeignNo(foreignNo);
        replyDTO.setContent(content);
        replyDTO.setWriterId(writerId);
        replyService.addReply(replyDTO);

        return "redirect:/community/" + getRedirectUrl(communityCategoryCode) + "/" + foreignNo;
    }


    @PostMapping("/{communityCategoryCode}/{foreignNo}/reply/delete/{communityReplyNo}")
    public String deleteReply(@PathVariable String communityCategoryCode,
                              @PathVariable int foreignNo,
                              @PathVariable int communityReplyNo,
                              Principal principal) {

        String writerId;
        if (principal instanceof UserDetails) {
            writerId = ((UserDetails) principal).getUsername();
        } else {
            writerId = principal.getName();
        }

        if (replyService.replyWriter(communityReplyNo, writerId)) {
            replyService.deleteReply(communityReplyNo);
        }

        return "redirect:/community/" + getRedirectUrl(communityCategoryCode) + "/" + foreignNo;
    }

   /* // 댓글 수정 처리
    @PostMapping("/{communityCategoryCode}/{foreignNo}/reply/update/{communityReplyNo}")
    public String updateReply(
            @PathVariable String communityCategoryCode,
            @PathVariable Long foreignNo,
            @PathVariable int communityReplyNo,
            @RequestParam String content) {

        replyService.updateReply(communityReplyNo, content);
        return "redirect:/community/" + getRedirectUrl(communityCategoryCode) + "/" + foreignNo;
    }*/

    @PostMapping("/{communityCategoryCode}/{foreignNo}/reply/update/{communityReplyNo}")
    @ResponseBody
    public ResponseEntity<String> updateReplyAjax(
            @PathVariable("communityReplyNo") int communityReplyNo,
            @RequestParam("content") String content) {
        try {
            replyService.updateReply(communityReplyNo, content);
            return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정 중 오류가 발생했습니다.");
        }
    }

    private String getRedirectUrl(String communityCategoryCode) {
        switch (ReplyCode.valueOf(communityCategoryCode)) {
            case L:
                return "doglove";
            case C:
                return "chatdog";
            case O:
                return "conference";
            case R:
                return "reels";
            default:
                throw new IllegalArgumentException("Invalid community category code: " + communityCategoryCode);
        }
    }
}