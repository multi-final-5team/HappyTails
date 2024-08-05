package com.multi.happytails.dogNum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * packageName    : com.multi.happytails.dogNum
 * fileName       : DogNumController
 * author         : User
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        User       최초 생성
 */
@RestController
@RequestMapping("/dogNum")
public class DogNumController {

    private final String SERVICE_KEY = "WO6eYkS2IohC2ttnnnk4oJVRUweEG%2F9Pvi9HokXptwYM1PokLcexnxHVoThQZk%2FAl2mAfd1WltIinmxbGpmodQ%3D%3D";
    private final String API_URL = "http://apis.data.go.kr/1543061/animalInfoSrvc/animalInfo";

    @GetMapping("/lookUp")
    public String lookUp(@RequestParam String dog_reg_no, @RequestParam String owner_nm) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?serviceKey=%s&dog_reg_no=%s&owner_nm=%s", API_URL, SERVICE_KEY, dog_reg_no, owner_nm);
        return restTemplate.getForObject(url, String.class);
    }

}