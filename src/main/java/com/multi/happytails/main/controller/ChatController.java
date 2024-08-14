package com.multi.happytails.main.controller;

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
    public String sendMessage(@Payload String chatMessage) {
        try {
            System.out.printf(clovaService.processMessage(chatMessage));
            return clovaService.processMessage(chatMessage);
        } catch (Exception e) {
            // 로깅 추가
            return "죄송합니다. 오류가 발생했습니다: " + e.getMessage();
        }
    }

}
