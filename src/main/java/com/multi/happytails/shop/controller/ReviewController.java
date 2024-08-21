package com.multi.happytails.shop.controller;

import com.multi.happytails.shop.model.dto.ReviewDTO;
import com.multi.happytails.shop.service.ReviewService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
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
                               @RequestParam("imageFiles")List<MultipartFile> imageFiles) {
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

        return "redirect:/sales/salesList"; // adjust the redirect as necessary
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
    public String updateReview(Principal principal,
                               @ModelAttribute ReviewDTO reviewDTO,
                               @RequestParam(value = "imageFiles") @Nullable List<MultipartFile> imageFiles,
                               @RequestParam(value = "imageUpdateFiles") @Nullable List<MultipartFile> imageUpdateFiles,
                               @RequestParam(value = "imageDeleteImageNo") @Nullable List<Long> imageDeleteImageNo,
                               @RequestParam(value = "imageUpdateImageNo") @Nullable List<Long> imageUpdateImageNo) {
        String userId = principal.getName();
        reviewDTO.setId(userId);

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

        return "/sales/salesList";
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
    @GetMapping("/deleteReview")
    public String deleteReview(@RequestParam("reviewNo") int no) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setNo(no);
        System.out.println("dd" + no);
        System.out.println("dd" + reviewDTO);
        List<UploadDto> uploadDtos= uploadService.uploadSelect(UPLOAD_INQUIRY_CODE,no); // 리스트로 가져오니까
        // for문으로 써서

        for(int i = 0; i < uploadDtos.size(); i++) {
            uploadService.uploadDelete(uploadDtos.get(i).getImageNo());
        }
        reviewService.deleteReview(reviewDTO);
        return "redirect:/sales/salesList";
    }
    // Delete

    @GetMapping("/reviewUpdatePopup")
    public String updateForm(@RequestParam("goodsNo") int goodsNo
            , Model model, Principal principal){
        String id = principal.getName();
        ReviewDTO reviewDetails = reviewService.selectReview(id, goodsNo);

        List<UploadDto> uploadDtos = uploadService.uploadSelect(UPLOAD_INQUIRY_CODE, goodsNo);
        System.out.println(uploadDtos + " ===============================================");
        System.out.println(reviewDetails);
        System.out.println(id);
        System.out.println(goodsNo);

        model.addAttribute("uploadDtos", uploadDtos);
        model.addAttribute("reviewDetails", reviewDetails);

        return "sales/reviewUpdatePopup";
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
