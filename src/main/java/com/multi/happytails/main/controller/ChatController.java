package com.multi.happytails.main.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.multi.happytails.main.service.ClovaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * packageName    : com.multi.happytails.main.controller
 * fileName       : ChatController
 * author         : ehdtka
 * date           : 2024-08-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-14        ehdtka       최초 생성
 */
@Controller
public class ChatController {

    @Autowired
    private ClovaService clovaService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/dog")
    public JsonNode sendMessage(@Payload String chatMessage) throws Exception {
        try {
            return clovaService.processMessage(chatMessage);
        } catch (Exception e) {
            // 로깅 추가
            e.printStackTrace();
            return clovaService.processMessage(chatMessage);
        }
    }

}
