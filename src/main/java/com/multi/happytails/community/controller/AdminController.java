package com.multi.happytails.community.controller;

import com.multi.happytails.community.model.dto.DogloveDTO;
import com.multi.happytails.community.reply.model.dto.ReplyDTO;
import com.multi.happytails.community.service.DogloveService;
import com.multi.happytails.community.reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private DogloveService dogloveService;

    @Autowired
    private ReplyService replyService;

    @GetMapping("/doglove")
    public String lookDoglove(Model model) {
        List<DogloveDTO> dogloves = dogloveService.findAllSortedByDate();
        model.addAttribute("dogloves", dogloves);
        return "/dogloveList";
    }

    @GetMapping("/doglove/delete/{dogloveNo}")
    public String deleteDoglove(@PathVariable Long dogloveNo) {
        dogloveService.delete(dogloveNo);
        replyService.replyDeleteAll("L", dogloveNo);
        return "redirect:/community/doglove";
    }

    @GetMapping("/doglove/{dogloveNo}/reply")
    public String allReply(@PathVariable Long dogloveNo, Model model) {
        List<ReplyDTO> reply = replyService.getReplyByForeignNo("L", Math.toIntExact(dogloveNo));
        model.addAttribute("reply", reply);
        model.addAttribute("dogloveNo", dogloveNo);
        return "admin/replyList";
    }

    @GetMapping("/reply/delete/{replyNo}")
    public String deleteReply(@PathVariable Long replyNo, @RequestParam Long dogloveNo) {
        replyService.deleteReply(Math.toIntExact(replyNo));
        return "redirect:/admin/doglove/" + dogloveNo + "/reply";
    }
}
