package com.multi.happytails.community.reply.controller;

import com.multi.happytails.community.reply.model.dto.ReplyDTO;
import com.multi.happytails.community.reply.service.ReplyCode;
import com.multi.happytails.community.reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            Principal principal,
            Model model) {

        String writerId = principal.getName();
        // String userId = (String) session.getAttribute("USER_ID");
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setCommunityCategoryCode(communityCategoryCode);
        replyDTO.setForeignNo(foreignNo);
        replyDTO.setContent(content);
        replyDTO.setWriterId(writerId);
        replyService.addReply(replyDTO);

        model.addAttribute("message", "댓글이 성공적으로 추가되었습니다.");

        return "redirect:/community/" + getRedirectUrl(communityCategoryCode) + "/" + foreignNo;
    }

    @PostMapping("/community/{communityCategoryCode}/{foreignNo}/reply/delete")
    public String deleteReply(@PathVariable String communityCategoryCode,
                              @PathVariable int foreignNo,
                              @RequestParam int replyNo,
                              Principal principal) {
        String writerId = principal.getName();
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setCommunityCategoryCode(communityCategoryCode);
        replyDTO.setForeignNo(foreignNo);

        if (principal instanceof UserDetails) {
            writerId = ((UserDetails) principal).getUsername();
        } else {
            writerId = principal.toString();
        }

        if (replyService.isReplyWriter(replyNo, writerId)) {
            replyService.deleteReply(replyNo);
        } else {
        }
        return "redirect:/community/" + getRedirectUrl(communityCategoryCode) + "/" + foreignNo;
    }



    private String getRedirectUrl(String communityCategoryCode) {
        switch (ReplyCode.valueOf(communityCategoryCode)) {
            case L:
                return "doglove";
            case C:
                return "chatter";
            case O:
                return "owners";
            case R:
                return "reels";
            default:
                throw new IllegalArgumentException("Invalid community category code: " + communityCategoryCode);
        }
    }
}