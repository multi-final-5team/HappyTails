package com.multi.happytails.shop.controller;

import com.multi.happytails.authentication.model.dto.CustomUser;
import com.multi.happytails.shop.model.dto.ReviewDTO;
import com.multi.happytails.shop.service.ReviewService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * @param customUser the custom user
     * @param goodsNo    the goods no
     * @param starRating the star rating
     * @param content    the content
     * @param imageFiles the image files
     * @return the string
     */
    @PostMapping("/insertReview")
    public String insertReview(@AuthenticationPrincipal CustomUser customUser,
                               @RequestParam("goodsNo") int goodsNo,
                               @RequestParam("starRating") int starRating,
                               @RequestParam("content") String content,
                               @RequestParam("imageFiles")List<MultipartFile> imageFiles) {
        String Id = customUser.getId();
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

        return "redirect:/sales/salesList"; // adjust the redirect as necessary
    }


    /**
     * methodName : updateReview
     * author : Shin HyeonCheol
     * description : Modify Review
     *
     * @param customUser the custom user
     * @param reviewDTO  the review dto
     * @return the string
     */
    @PostMapping("/updateReview")
    public String updateReview(@AuthenticationPrincipal CustomUser customUser, @RequestBody ReviewDTO reviewDTO) {
        String userId = customUser.getId();
        reviewDTO.setId(userId);
        reviewService.updateReview(reviewDTO);
        return "redirect:/sales/salesList";
    }
    // Update

    /**
     * methodName : deleteReview
     * author : Shin HyeonCheol
     * description : delete Review
     *
     * @param reviewNo   the review no
     * @param goodsNo    the goods no
     * @param customUser the custom user
     * @return the string
     */
    @GetMapping("/deleteReview")
    public String deleteReview(@RequestParam("reviewNo") int reviewNo,
                               @RequestParam("goodsNo") int goodsNo,
                               @AuthenticationPrincipal CustomUser customUser
                                ) {
        ReviewDTO reviewDTO = new ReviewDTO();
        String Id = customUser.getId();
        reviewDTO.setId(Id);
        reviewDTO.setNo(reviewNo);
        reviewDTO.setGoodsNo(goodsNo);
        List<UploadDto> uploadDtos= uploadService.uploadSelect(UPLOAD_INQUIRY_CODE,reviewNo); // 리스트로 가져오니까
        // for문으로 써서

        for(int i = 0; i < uploadDtos.size(); i++) {
            uploadService.uploadDelete(uploadDtos.get(i).getImageNo());
        }
        reviewService.deleteReview(reviewDTO);
        return "redirect:/sales/salesList";
    }
    // Delete


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
