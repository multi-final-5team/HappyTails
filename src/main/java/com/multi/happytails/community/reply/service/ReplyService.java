package com.multi.happytails.community.reply.service;

import com.multi.happytails.community.reply.model.dao.ReplyDAO;
import com.multi.happytails.community.reply.model.dto.ReplyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReplyService {

    @Autowired
    private ReplyDAO replyDAO;

    public void addReply(ReplyDTO replyDTO) {
        LocalDateTime now = LocalDateTime.now();
        replyDTO.setCreatedDate(now);
        replyDTO.setUpdatedDate(now);
        replyDAO.insertReply(replyDTO);
    }

    public List<ReplyDTO> getReplyByForeignNo(String communityCategoryCode, int foreignNo) {
        return replyDAO.selectReplyByForeignNo(communityCategoryCode, foreignNo);
    }
}


