package com.multi.happytails.community.controller;

import com.multi.happytails.community.reply.service.ReplyService;
import com.multi.happytails.community.service.ChatDogService;
import com.multi.happytails.community.service.ConferenceService;
import com.multi.happytails.community.service.DogloveService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminController {

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
        replyService.replyDeleteAll("L", dogloveNo);
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
        replyService.replyDeleteAll("C", chatdogNo);

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
        replyService.replyDeleteAll("O", conferenceNo);

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

    //집사회의 댓글 하나 삭제
    @GetMapping("/conference/{conferenceNo}/reply/delete/{communityReplyNo}")
    public String deleteConferenceReply(@PathVariable Long conferenceNo, @PathVariable int communityReplyNo) {
        replyService.deleteReply(communityReplyNo);

        return "redirect:/conference/" + conferenceNo + "/reply";
    }

}