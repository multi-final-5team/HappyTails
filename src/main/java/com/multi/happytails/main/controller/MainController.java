package com.multi.happytails.main.controller;

import com.multi.happytails.community.model.dto.ChatDogDTO;
import com.multi.happytails.community.model.dto.DogloveDTO;
import com.multi.happytails.community.service.ChatDogService;
import com.multi.happytails.community.service.DogloveService;
import com.multi.happytails.main.dto.RssNewsDTO;
import com.multi.happytails.main.service.MainService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    @Autowired
    private ChatDogService chatDogService;

    @Autowired
    private DogloveService dogloveService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/main/main"; // 뷰 리졸버가 index.html 또는 index.jsp 등을 찾습니다.
    }

    @RequestMapping("/main/main")
    public String homePage(Model model) {

        //인기글 ( 한달이내 글 중에 추천순으로 5개)
        List<ChatDogDTO> chatDogUpList = chatDogService.mainChatDogList();

        List<String> chatDogStoredImages = new ArrayList<String>();

        for (int i = 0; i < chatDogUpList.size(); i++) {
            UploadDto uploadDto = null;
            List<UploadDto> uploadDtos = uploadService.uploadSelect("C",chatDogUpList.get(i).getChatdogNo());

            if (!uploadDtos.isEmpty()) {
                chatDogStoredImages.add(uploadDtos.get(0).getStoredFileName());
            } else {
                chatDogStoredImages.add("");
            }
        }


        //뉴스
        String rssUrl = "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fwww.newsunion.kr%2F%3Fr%3Ds134556%26m%3Dnews%26mod%3Dgoogle_rss%26mid%3D32";
        List<RssNewsDTO> rssNews = mainService.GetRssNewsData(rssUrl);

        //내 새끼 자랑 (최신글 5개)
        List<DogloveDTO> dogloveDTOS = dogloveService.mainDogLoveList();

        List<String> dogLoveStoredImages = new ArrayList<String>();

        for (int i = 0; i < dogloveDTOS.size(); i++) {
            UploadDto uploadDto = null;
            List<UploadDto> uploadDtos = uploadService.uploadSelect("L",dogloveDTOS.get(i).getDogloveNo());

            if (!uploadDtos.isEmpty()) {
                dogLoveStoredImages.add(uploadDtos.get(0).getStoredFileName());
            } else {
                dogLoveStoredImages.add("");
            }
        }

        // 화면 전송
        model.addAttribute("rssNews", rssNews);
        model.addAttribute("chatDogUpList", chatDogUpList);
        model.addAttribute("chatDogStoredImages", chatDogStoredImages);
        model.addAttribute("dogLoveStoredImages", dogLoveStoredImages);

        return "/main/main";
    }
}
