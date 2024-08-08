package com.multi.happytails.patrol.controller;

import com.multi.happytails.authentication.model.dto.CustomUser;
import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.member.service.MemberService;
import com.multi.happytails.patrol.model.dto.PrecordReplyDTO;
import com.multi.happytails.patrol.service.PatrolRecordReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.multi.happytails.patrol.controller
 * fileName       : PatrolReplyController
 * author         : wss18
 * date           : 2024-08-01
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-01        wss18       최초 생성
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/patrolRecord/reply")
public class PatrolReplyController {

    @Autowired
    PatrolRecordReplyService patrolRecordReplyService;

    @Autowired
    MemberService memberService;

    @PostMapping("repleyInsert")
    public String repleyInsert(@RequestParam("replyPrecordNo") int replyPrecordNo, @RequestParam("replyContent") String replyContent, @AuthenticationPrincipal() CustomUser customUser){

        PrecordReplyDTO precordReplyDTO = new PrecordReplyDTO();

        precordReplyDTO.setPrecordNo(replyPrecordNo);

        precordReplyDTO.setUserNo((int) customUser.getNo());

        precordReplyDTO.setContent(replyContent);

        patrolRecordReplyService.repleyInsert(precordReplyDTO);

        return "redirect:/patrolRecord/patrolRecordView?precordNo=" + replyPrecordNo;
    }

    @PostMapping("repleyUpdate")
    public String repleyUpdate(@RequestParam("replyPrecordNo") int replyPrecordNo , @RequestParam("replyNo") int replyNo , @RequestParam("replyContent") String replyContent, @AuthenticationPrincipal() CustomUser customUser){

        PrecordReplyDTO precordReplyDTO = new PrecordReplyDTO();

        precordReplyDTO.setPrecordNo(replyPrecordNo);

        precordReplyDTO.setUserNo((int) customUser.getNo());

        precordReplyDTO.setContent(replyContent);

        precordReplyDTO.setPrecordReplyNo(replyNo);

        patrolRecordReplyService.repleyUpdate(precordReplyDTO);

        return "redirect:/patrolRecord/patrolRecordView?precordNo=" + replyPrecordNo;
    }

    @PostMapping("repleyDelete")
    public String repleyDelete(@RequestParam("replyPrecordNo") int replyPrecordNo , @RequestParam("replyNo") int replyNo , @RequestParam("replyContent") String replyContent, @AuthenticationPrincipal() CustomUser customUser){
        System.out.println("댓글삭제 호출");

        PrecordReplyDTO precordReplyDTO = new PrecordReplyDTO();

        precordReplyDTO.setPrecordReplyNo(replyNo);

        precordReplyDTO.setUserNo((int) customUser.getNo());

        patrolRecordReplyService.repleyDelete(precordReplyDTO);

        return "redirect:/patrolRecord/patrolRecordView?precordNo=" + replyPrecordNo;
    }






    @GetMapping("findRepleyAllByPrecordNo")
    @ResponseBody
    public List<PrecordReplyDTO> findRepleyAllByPrecordNo(@RequestParam("precordNo") int precordNo){

        List<PrecordReplyDTO> list = patrolRecordReplyService.findRepleyAllByPrecordNo(precordNo);

        for(PrecordReplyDTO dto : list){
            MemberDTO memberDTO = memberService.findMemberByUserNo(dto.getUserNo());

            String userName = memberDTO.getName();

            dto.setUserId(memberDTO.getId());
            dto.setUserName(userName);

        }

        return list;
    }




}
