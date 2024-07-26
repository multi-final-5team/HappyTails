package com.multi.happytails.community.reply.model.dao;

import com.multi.happytails.community.reply.model.dto.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyDAO {

    void insertReply(ReplyDTO replyDTO);
    ReplyDTO selectReplyById(int replyNo);
    void updateReply(ReplyDTO replyDTO);
    void deleteReply(int communityReplyNo);

    List<ReplyDTO> selectReplyByForeignNo(String communityCategoryCode, int foreignNo);

}
