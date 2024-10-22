package com.multi.happytails.shop.controller;

import com.multi.happytails.shop.model.dto.ReviewDTO;
import com.multi.happytails.shop.model.dto.SalesGoodsDTO;
import com.multi.happytails.shop.service.ReviewService;
import com.multi.happytails.shop.service.SalesService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.multi.happytails.shop.controller
 * fileName       : SalesController.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 상품 판매 관리 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Controller
@RequestMapping("/sales")
public class SalesController {
    //글 목록, 글작성, 글수정, 상세
    //리뷰 view, 리뷰 작성, 리뷰 수정, 리뷰 삭제
    @Autowired
    private SalesService salesService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ReviewService reviewService;

    /**
     * The Upload inquiry code.
     */
    final String UPLOAD_INQUIRY_CODE = "S";

    /**
     * methodName : insertform
     * author : Shin HyeonCheol
     * description : "/insertform" URL 이동 메소드
     */
    @RequestMapping("/insertform")
    public void insertForm(){

    }

    /**
     * methodName : updateForm
     * author : Shin HyeonCheol
     * description :
     *
     * @param goodsNo the goods no
     * @param model   the model
     */
    @GetMapping("/updateform")
    public void updateForm(@RequestParam("no") int goodsNo
            , Model model){
        SalesGoodsDTO salesGoodsDTO = new SalesGoodsDTO();
        salesGoodsDTO.setNo(goodsNo);
        SalesGoodsDTO salesDetails = salesService.selectSales(salesGoodsDTO);

        List<UploadDto> uploadDtos = uploadService.uploadSelect(UPLOAD_INQUIRY_CODE, goodsNo);
        System.out.println("uploadDtos >>>>>>>>>>>>" + uploadDtos);
        System.out.println("salesDetails >>>>>>>>>>>" + salesDetails);
        model.addAttribute("uploadDtos", uploadDtos);
        model.addAttribute("salesDetails", salesDetails);
    }

    /**
     * methodName : salesList
     * author : Shin HyeonCheol
     * description : 상품 목록 호출 메소드
     *
     * @param page  the page
     * @param model the model
     * @return the string
     */
    @RequestMapping("/salesList")
    public String salesList(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 9;
        List<SalesGoodsDTO> salesGoodsList = salesService.salesList(page, pageSize);
        int totalSalesCount = salesService.salesPageCount();
        int totalPages = (int) Math.ceil((double) totalSalesCount / pageSize);

        Map<Integer, List<UploadDto>> salesGoodsMap = new HashMap<>();

        for (SalesGoodsDTO SalesGoods : salesGoodsList) {
            int goodsNo = SalesGoods.getNo();
            salesGoodsMap.put(goodsNo, uploadService.uploadSelect("S", goodsNo));
        }

        model.addAttribute("salesGoodsMap", salesGoodsMap);
        model.addAttribute("salesGoodsList", salesGoodsList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "sales/salesList";
    }
    //List

    /**
     * methodName : salesListBusiness
     * author : Shin HyeonCheol
     * description : 상품 목록 호출 메소드 _ 사업자
     *
     * @param page      the page
     * @param principal the principal
     * @param model     the model
     * @return the string
     */
    @RequestMapping("/salesListBusiness")
    public String salesListBusiness(@RequestParam(name = "page", defaultValue = "1") int page, Principal principal, Model model) {
        int pageSize = 9;
        String id = principal.getName();
        List<SalesGoodsDTO> salesGoodsList = salesService.salesListBusiness(page, pageSize, id);
        int totalSalesCount = salesService.salesPageCountForUser(id);
        int totalPages = (int) Math.ceil((double) totalSalesCount / pageSize);

        Map<Integer, List<UploadDto>> salesGoodsMap = new HashMap<>();

        for (SalesGoodsDTO SalesGoods : salesGoodsList) {
            int goodsNo = SalesGoods.getNo();
            salesGoodsMap.put(goodsNo, uploadService.uploadSelect("S", goodsNo));
        }
        model.addAttribute("salesGoodsMap", salesGoodsMap);

        model.addAttribute("salesGoodsList", salesGoodsList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "sales/salesListBusiness";
    }
    //List - Business

    /**
     * methodName : salesListAdmin
     * author : Shin HyeonCheol
     * description : 상품 목록 호출 메소드 _ 관리자
     *
     * @param page  the page
     * @param model the model
     * @return the string
     */
    @RequestMapping("/salesListAdmin")
    public String salesListAdmin(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        List<SalesGoodsDTO> salesGoodsList = salesService.salesListAdmin(page, pageSize);
        int totalSalesCount = salesService.salesPageCount();
        int totalPages = (int) Math.ceil((double) totalSalesCount / pageSize);

        Map<Integer, List<UploadDto>> salesGoodsMap = new HashMap<>();

        for (SalesGoodsDTO SalesGoods : salesGoodsList) {
            int goodsNo = SalesGoods.getNo();
            salesGoodsMap.put(goodsNo, uploadService.uploadSelect("S", goodsNo));
        }
        model.addAttribute("salesGoodsMap", salesGoodsMap);

        model.addAttribute("salesGoodsList", salesGoodsList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "sales/salesListAdmin";
    }
    //List - Admin

    /**
     * methodName : selectGoods
     * author : Shin HyeonCheol
     * description : 상품 상세 페이지 호출 메소드, 해당 상품 리뷰 목록 호출 메소드
     *
     * @param no        the no
     * @param model     the model
     * @return the string
     */
    @GetMapping("/selectGoods")
    public String selectGoods(@RequestParam("no") int no,
                              Model model) {

        SalesGoodsDTO salesGoodsDTO = new SalesGoodsDTO();
        salesGoodsDTO.setNo(no);
        SalesGoodsDTO salesDetails = salesService.selectSales(salesGoodsDTO);

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setGoodsNo(no); // 상품 번호를 설정
        List<ReviewDTO> reviewList = reviewService.reviewList(reviewDTO);

        model.addAttribute("salesDetails", salesDetails);
        model.addAttribute("uploadDtoList", uploadService.uploadSelect(UPLOAD_INQUIRY_CODE, no));

        Map<Integer, List<UploadDto>> reviewMap = new HashMap<>();

        for (ReviewDTO review : reviewList) {
            int reviewNo = review.getNo();
            reviewMap.put(reviewNo, uploadService.uploadSelect("R", reviewNo));
        }
        model.addAttribute("reviewMap", reviewMap);

        //==========================
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("goodsNo", no);
//        model.addAttribute("principalName", principal.getName());
        return "sales/salesDetails";
    }
    //Read

    /**
     * methodName : insertGoods
     * author : Shin HyeonCheol
     * description : 상품 등록 메소드
     *
     * @param principal    the principal
     * @param name         the name
     * @param price        the price
     * @param quantity     the quantity
     * @param content      the content
     * @param categoryCode the category code
     * @param imageFile    the image file
     * @param imageFiles   the image files
     * @return the string
     */
    @PostMapping("/insertGoods")
    public String insertGoods (Principal principal,
                               @RequestParam("name") String name,
                               @RequestParam("price") int price,
                               @RequestParam("quantity") int quantity,
                               @RequestParam("content") String content,
                               @RequestParam("categoryCode") String categoryCode,
                               @RequestParam("imageFile") MultipartFile imageFile,
                               @RequestParam("imageFiles")List<MultipartFile> imageFiles
    ) {

        SalesGoodsDTO salesGoodsDTO = new SalesGoodsDTO();
        String Id = principal.getName();
        System.out.println("custom get id = " + Id);
        salesGoodsDTO.setId(Id);
        //adress = 사업자 주소 추가해야함
        // 가져온 Id > 사업자 정보 조회 > 사업자 주소 >
        salesGoodsDTO.setName(name);
        salesGoodsDTO.setPrice(price);
        salesGoodsDTO.setQuantity(quantity);
        //이미지 저장 = 추가해야함
        salesGoodsDTO.setContent(content);
        salesGoodsDTO.setCategoryCode(categoryCode);

        salesService.insertSales(salesGoodsDTO);

        UploadDto uploadDto = new UploadDto();
        uploadDto.setCategoryCode(UPLOAD_INQUIRY_CODE);
        uploadDto.setForeignNo(salesService.getSalesNo());

        uploadDto.setFile(imageFile);
        uploadService.uploadInsert(uploadDto);

        for (int i = 0; i < imageFiles.size(); i++) {
            uploadDto.setFile(imageFiles.get(i));
            uploadService.uploadInsert(uploadDto);
        }

        return "redirect:/sales/salesListBusiness";
    }
    // Create

    /**
     * methodName : updateGoods
     * author : Shin HyeonCheol
     * description : 상품 수정 메소드
     *
     * @param principal          the principal
     * @param salesGoodsDTO      the sales goods dto
     * @param imageFiles         the image files
     * @param imageUpdateFiles   the image update files
     * @param imageDeleteImageNo the image delete image no
     * @param imageUpdateImageNo the image update image no
     * @return the string
     */
    @PostMapping("/updateGoods")
    @ResponseBody
    public String updateGoods (Principal principal,
                               @ModelAttribute SalesGoodsDTO salesGoodsDTO,
                               @RequestParam(value = "imageFiles") @Nullable List<MultipartFile> imageFiles,
                               @RequestParam(value = "imageUpdateFiles") @Nullable List<MultipartFile> imageUpdateFiles,
                               @RequestParam(value = "imageDeleteImageNo") @Nullable List<Long> imageDeleteImageNo,
                               @RequestParam(value = "imageUpdateImageNo") @Nullable List<Long> imageUpdateImageNo
    ) {

        String Id = principal.getName();
        System.out.println("custom get id = " + Id);
        salesGoodsDTO.setId(Id);
        //address = 사업자 주소 추가 해야함

        int result = salesService.updateSales(salesGoodsDTO);

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
                uploadDto.setForeignNo(salesGoodsDTO.getNo());
                uploadDto.setCategoryCode(UPLOAD_INQUIRY_CODE);

                for (int i = 0; i < imageFiles.size(); i++) {
                    uploadDto.setFile(imageFiles.get(i));
                    uploadService.uploadInsert(uploadDto);
                }
            }
        }

        return "상품 수정에 성공하였습니다.";
    }

    // Update

    /**
     * methodName : deleteGoods
     * author : Shin HyeonCheol
     * description : 상품 삭제 메소드
     *
     * @param no      the no
     * @param request the request
     * @return the string
     */
    @GetMapping("/deleteGoods")
    public String deleteGoods (@RequestParam("no") int no, HttpServletRequest request){

        SalesGoodsDTO salesGoodsDTO = new SalesGoodsDTO();
        salesGoodsDTO.setNo(no);
        List<UploadDto> uploadDtos= uploadService.uploadSelect(UPLOAD_INQUIRY_CODE,no); // 리스트로 가져오니까
        // for문으로 써서

        for(int i = 0; i < uploadDtos.size(); i++) {
            uploadService.uploadDelete(uploadDtos.get(i).getImageNo());
        }
        salesService.deleteSales(salesGoodsDTO);

        String referer = request.getHeader("Referer"); // 이전 페이지 URL 가져오기
        return "redirect:" + referer;
    }
    // Delete

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<SalesGoodsDTO> results = salesService
                 .search(keyword);

        Map<Integer, List<UploadDto>> salesGoodsMap = new HashMap<>();

        for (SalesGoodsDTO SalesGoods : results) {
            int goodsNo = SalesGoods.getNo();
            salesGoodsMap.put(goodsNo, uploadService.uploadSelect("S", goodsNo));
        }

        model.addAttribute("salesGoodsMap", salesGoodsMap);
        model.addAttribute("salesGoodsList", results);

        return "sales/salesList";
    }

    @GetMapping("/reviewPopup")
    public String reviewPopup(@RequestParam("goodsNo") int goodsNo,
                                          Model model) {
        model.addAttribute("goodsNo", goodsNo);
        return "/sales/reviewPopup"; // 팝업 창 HTML 파일 이름
    }

// PostMan

//    @GetMapping("/salesList")
//    @ResponseBody
//    public List<SalesGoodsDTO> getSalesList() {
//        return salesService.salesList();
//    }
    // List

//    @GetMapping("/selectGoods")
//    @ResponseBody
//    public SalesGoodsDTO selectGoods(@RequestBody SalesGoodsDTO salesGoodsDTO) {
//        return salesService.selectSales(salesGoodsDTO);
//    }
    //Read

//    @PostMapping("/insertGoods")
//    public String insertGoods (@RequestBody SalesGoodsDTO salesGoodsDTO) {
//        String Id = "admin";
//        salesGoodsDTO.setId(Id);
//        //adress = 사업자 주소 추가해야함
//        //이미지 저장 = 추가해야함
//
//        salesService.insertSales(salesGoodsDTO);
//
//        return "redirect:/member/login";
//    }
    // Create

//    @PostMapping("/updateGoods")
//    public String updateGoods (@RequestBody SalesGoodsDTO salesGoodsDTO){
//
//        String Id = "admin";
//        salesGoodsDTO.setId(Id);
//        //adress = 사업자 주소 추가해야함
//        //이미지 저장 = 추가해야함
//
//        salesService.updateSales(salesGoodsDTO);
//
//        return "redirect:/sales/salesList";
//    }
    // Update

//    @GetMapping("/deleteGoods")
//    public String deleteGoods (@RequestBody SalesGoodsDTO salesGoodsDTO){
//
//        salesService.deleteSales(salesGoodsDTO);
//
//        return "redirect:/sales/salesList";
//    }
    //Delete

}