package com.multi.happytails.community.service;

import com.multi.happytails.community.model.dao.ChatDogDAO;
import com.multi.happytails.community.model.dto.ChatDogDTO;
import com.multi.happytails.community.reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatDogService {

    private static final String CATEGORY_CODE = "CHATDOG_CODE";

    @Autowired
    private ReplyService replyService;


    @Autowired
    private static ChatDogDAO chatdogDAO;

    public ChatDogService(ChatDogDAO chatdogDAO) { this.chatdogDAO = chatdogDAO; }

    public static List<ChatDogDTO> findAllSortedByDate() {
        return chatdogDAO.findAll("date");
    }

    public List<ChatDogDTO> findAllSortedByRecommendation() {
        return chatdogDAO.findAll("recommendCount");
    }

    public ChatDogDTO findById(Long chatdogNo) {
        return chatdogDAO.findById(chatdogNo);
    }

    public long insert(ChatDogDTO chatdogDTO) {
        chatdogDTO.setCategoryCode(CATEGORY_CODE);
        chatdogDAO.insert(chatdogDTO);
        return chatdogDAO.getCurrentChatDogNo();
    }

    public void cdcommendCount(Long chatdogNo, String userId) {
        chatdogDAO.cdcommendCount(chatdogNo);
    }

    public List<ChatDogDTO>search(String keyword) {
        return chatdogDAO.search(keyword);
    }

    public void delete(Long chatdogNo) {
        chatdogDAO.delete(chatdogNo);
    }


    public int update(ChatDogDTO chatDogDTO) {
        return chatdogDAO.update(chatDogDTO);

    }
}
