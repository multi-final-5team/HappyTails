package com.multi.happytails.dogNum.controller;

import com.multi.happytails.dogNum.dto.DogNumDTO;
import com.multi.happytails.dogNum.service.DogNumService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dogNum")
public class DogNumController {

    private final DogNumService dogNumService;
    private final UploadService uploadService;
    private final String IMAGE_CODE = "N";
    private final String SERVICE_KEY = "WO6eYkS2IohC2ttnnnk4oJVRUweEG%2F9Pvi9HokXptwYM1PokLcexnxHVoThQZk%2FAl2mAfd1WltIinmxbGpmodQ%3D%3D";
    private final String API_URL = "http://apis.data.go.kr/1543061/animalInfoSrvc/animalInfo";

    @Autowired
    public DogNumController(DogNumService dogNumService, UploadService uploadService) {
        this.dogNumService = dogNumService;
        this.uploadService = uploadService;
    }

    @GetMapping("/lookup")
    @ResponseBody
    public ResponseEntity<String> lookupEndpoint(@RequestParam String dog_reg_no, @RequestParam String owner_nm) {
        String result = lookUp(dog_reg_no, owner_nm);
        return ResponseEntity.ok(result);
    }

    private String lookUp(String dog_reg_no, String owner_nm) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?serviceKey=%s&dog_reg_no=%s&owner_nm=%s", API_URL, SERVICE_KEY, dog_reg_no, owner_nm);
        return restTemplate.getForObject(url, String.class);
    }

    @PostMapping("/save")
    public String saveDogNum(@RequestParam String dognum,
                             @RequestParam String dogname,
                             @RequestParam String dogbreed,
                             @RequestParam String doggender,
                             @RequestParam String neutering,
                             @RequestParam("imageFiles") @Nullable List<MultipartFile> imageFiles,
                             RedirectAttributes redirectAttributes) {

        DogNumDTO dogNumDTO = new DogNumDTO();
        dogNumDTO.setDognum(dognum);
        dogNumDTO.setDogname(dogname);
        dogNumDTO.setBreed(dogbreed);
        dogNumDTO.setDoggender(doggender);
        dogNumDTO.setNeutering(neutering);

        Long id = dogNumService.insert(dogNumDTO);

        UploadDto uploadDto = new UploadDto();
        uploadDto.setForeignNo(id);
        uploadDto.setCategoryCode(IMAGE_CODE);
        if (imageFiles != null && !imageFiles.isEmpty()) {
        for (int i = 0; i < imageFiles.size(); i++) {
            uploadDto.setFile(imageFiles.get(i));
            uploadService.uploadInsert(uploadDto);
        }
    }
        return "댕댕이 등록증 정보가 저장 되었습니다.";
    }


}
