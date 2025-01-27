package com.multi.happytails.shop.controller;

import com.multi.happytails.shop.model.dto.ReviewDTO;
import com.multi.happytails.shop.service.ReviewService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.multi.happytails.shop.controller
 * fileName       : ReviewController.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : Review Management Controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UploadService uploadService;

    /**
     * The Upload inquiry code.
     */
    final String UPLOAD_INQUIRY_CODE = "R";

//    @PostMapping("/reviewList")
//    @ResponseBody
//    public List<ReviewDTO> reviewList(@RequestBody ReviewDTO reviewDTO) {
//        return reviewService.reviewList(reviewDTO);
//    }
    // List

    /**
     * methodName : insertReview
     * author : Shin HyeonCheol
     * description : Write Review
     *
     * @param principal  the principal
     * @param goodsNo    the goods no
     * @param starRating the star rating
     * @param content    the content
     * @param imageFiles the image files
     * @return the string
     */
    @PostMapping("/insertReview")
    public String insertReview(Principal principal,
                               @RequestParam("goodsNo") int goodsNo,
                               @RequestParam("starRating") int starRating,
                               @RequestParam("content") String content,
                               @RequestParam("imageFiles") @Nullable List<MultipartFile> imageFiles) {
        String Id = principal.getName();
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(Id);
        reviewDTO.setGoodsNo(goodsNo);
        reviewDTO.setStarRating(starRating);
        reviewDTO.setContent(content);

        reviewService.insertReview(reviewDTO);

        UploadDto uploadDto = new UploadDto();
        uploadDto.setCategoryCode(UPLOAD_INQUIRY_CODE);
        uploadDto.setForeignNo(reviewService.getReviewNo());

        for (int i = 0; i < imageFiles.size(); i++) {
            uploadDto.setFile(imageFiles.get(i));
            uploadService.uploadInsert(uploadDto);
        }

        return "success"; // adjust the redirect as necessary
    }


    /**
     * methodName : updateReview
     * author : Shin HyeonCheol
     * description :
     *
     * @param principal          the principal
     * @param reviewDTO          the review dto
     * @param imageFiles         the image files
     * @param imageUpdateFiles   the image update files
     * @param imageDeleteImageNo the image delete image no
     * @param imageUpdateImageNo the image update image no
     * @return the string
     */
    @PostMapping("/updateReview")
    @ResponseBody
    public String updateReview(Principal principal,
                               @ModelAttribute ReviewDTO reviewDTO,
                               @RequestParam(value = "imageFiles") @Nullable List<MultipartFile> imageFiles,
                               @RequestParam(value = "imageUpdateFiles") @Nullable List<MultipartFile> imageUpdateFiles,
                               @RequestParam(value = "imageDeleteImageNo") @Nullable List<Long> imageDeleteImageNo,
                               @RequestParam(value = "imageUpdateImageNo") @Nullable List<Long> imageUpdateImageNo) {
        String userId = principal.getName();
        reviewDTO.setId(userId);

        System.out.println(reviewDTO + "aaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(imageFiles);
        System.out.println(imageUpdateFiles);
        System.out.println(imageDeleteImageNo);
        System.out.println(imageUpdateImageNo);

        // imageUpdateFiles 출력
        if (imageUpdateFiles != null) {
            System.out.println("imageUpdateFiles:");
            for (MultipartFile file : imageUpdateFiles) {
                System.out.println(" - " + file.getOriginalFilename());
            }
        } else {
            System.out.println("imageUpdateFiles is null or empty");
        }

        // imageDeleteImageNo 출력
        if (imageDeleteImageNo != null) {
            System.out.println("imageDeleteImageNo:");
            for (Long imageNo : imageDeleteImageNo) {
                System.out.println(" - " + imageNo);
            }
        } else {
            System.out.println("imageDeleteImageNo is null or empty");
        }

        // imageUpdateImageNo 출력
        if (imageUpdateImageNo != null) {
            System.out.println("imageUpdateImageNo:");
            for (Long imageNo : imageUpdateImageNo) {
                System.out.println(" - " + imageNo);
            }
        } else {
            System.out.println("imageUpdateImageNo is null or empty");
        }

        System.out.println(imageFiles + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(imageUpdateFiles);
        System.out.println(imageUpdateImageNo);
        System.out.println(imageDeleteImageNo);

        System.out.println(reviewDTO);

        String id = reviewDTO.getId();
        int goodsNo = reviewDTO.getGoodsNo();

        ReviewDTO selectReview = reviewService.selectReview2(id, goodsNo);

        reviewDTO.setNo(selectReview.getNo());

        System.out.println(id + "bbbbbbbbbbbbbbbbbbbbb");
        System.out.println(goodsNo + "bbbbbbbbbbbbbbbbbbbbb");
        System.out.println(reviewDTO + "bbbbbbbbbbbbbbbbbbbbb");
        int result = reviewService.updateReview(reviewDTO);

        if (result == 1) {

            if (imageDeleteImageNo != null && !imageDeleteImageNo.isEmpty()) {
                for (int i = 0; i < imageDeleteImageNo.size(); i++) {
                    uploadService.uploadDelete(imageDeleteImageNo.get(i));
                }
            }

            if (imageUpdateFiles != null && !imageUpdateFiles.isEmpty()) {
                for (int i = 0; i < imageUpdateFiles.size(); i++) {
                    System.out.println(imageUpdateImageNo.get(i) + imageUpdateFiles.get(i).getOriginalFilename() + "===-=-=-=-=-=-=");
                    uploadService.uploadUpdate(imageUpdateImageNo.get(i), imageUpdateFiles.get(i));
                }
            }

            if (imageFiles != null && !imageFiles.isEmpty()) {
                UploadDto uploadDto = new UploadDto();
                uploadDto.setForeignNo(reviewDTO.getNo());
                uploadDto.setCategoryCode(UPLOAD_INQUIRY_CODE);

                for (int i = 0; i < imageFiles.size(); i++) {
                    uploadDto.setFile(imageFiles.get(i));
                    uploadService.uploadInsert(uploadDto);
                }
            }
        }

        return "리뷰 수정에 성공하였습니다.";
    }
    // Update

    /**
     * methodName : deleteReview
     * author : Shin HyeonCheol
     * description : delete Review
     *
     * @param no   the review no
     * @return the string
     */
    @PostMapping("/deleteReview")
    @ResponseBody
    public String deleteReview(Principal principal,@RequestParam("goodsNo") int goodsNo) {
        ReviewDTO reviewInfo = reviewService.selectReview2(principal.getName(), goodsNo);


        List<UploadDto> uploadDtos= uploadService.uploadSelect(UPLOAD_INQUIRY_CODE,reviewInfo.getNo()); // 리스트로 가져오니까
        // for문으로 써서

        for(int i = 0; i < uploadDtos.size(); i++) {
            uploadService.uploadDelete(uploadDtos.get(i).getImageNo());
        }
        reviewService.deleteReview(reviewInfo);
        return "리뷰 삭제에 성공하였습니다.";
    }
    // Delete

    @GetMapping("/reviewUpdate")
    @ResponseBody
    public ResponseEntity<?> updateForm(@RequestParam("goodsNo") int goodsNo,
                                     Principal principal){
        String id = principal.getName();
        ReviewDTO reviewDetails = reviewService.selectReview(id, goodsNo);

        List<UploadDto> uploadDtos = uploadService.uploadSelect(UPLOAD_INQUIRY_CODE, reviewDetails.getNo());
        System.out.println(uploadDtos + " ===============================================");
        System.out.println(reviewDetails);
        System.out.println(id);
        System.out.println(goodsNo);

        // ResponseEntity에 담을 데이터를 Map으로 구성
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("uploadDtos", uploadDtos);
        responseData.put("reviewDetails", reviewDetails);

        // ResponseEntity로 데이터 반환
        return ResponseEntity.ok(responseData);
    }

    //PosetMan

//    @PostMapping("/reviewList")
//    @ResponseBody
//    public List<ReviewDTO> reviewList(@RequestBody ReviewDTO reviewDTO) {
//        return reviewService.reviewList(reviewDTO);
//    }
    //List

//    @PostMapping("/insertReview")
//    public String insertReview(@RequestBody ReviewDTO reviewDTO) {
//
//        String Id = "admin";
//        reviewDTO.setId(Id);
//        reviewService.insertReview(reviewDTO);
//        return "redirect:/sales/salesList";
//    }
    // Create

//    @PostMapping("/updateReview")
//    public String updateReview(@RequestBody ReviewDTO reviewDTO) {
//        String Id = "admin";
//        reviewDTO.setId(Id);
//        reviewService.updateReview(reviewDTO);
//        return "redirect:/sales/salesList";
//    }
    // Update

//    @GetMapping("/deleteReview")
//    public String deleteReview(@RequestBody ReviewDTO reviewDTO) {
//        String Id = "admin";
//        reviewDTO.setId(Id);
//        reviewService.deleteReview(reviewDTO);
//        return "redirect:/sales/salesList";
//    }
    // Delete
}
