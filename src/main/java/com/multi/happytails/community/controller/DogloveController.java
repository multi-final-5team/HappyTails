package com.multi.happytails.community.controller;

import com.multi.happytails.community.model.dto.DogloveDTO;
import com.multi.happytails.community.reply.model.dto.ReplyDTO;
import com.multi.happytails.community.reply.service.ReplyService;
import com.multi.happytails.community.service.DogloveService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * packageName    : com.multi.happytails.community.controller
 * fileName       : test
 * author         : User
 * date           : 2024-07-24
 * description    : The type Doglove controller.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        User       최초 생성
 */

@Controller
@RequestMapping("/community")
public class DogloveController {


    @Autowired
    private DogloveService dogloveService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ReplyService replyService;

    /**
     * 댓글코드(내 새꾸 자랑)
     */
    final String replyCategoryCode = "L";


    /**
     * 이미지코드(내 새꾸 자랑)
     */
    final String IMAGE_CODE = "L";

    /**
     * 게시판 카테고리 코드(내 새꾸 자랑)
     */
    final String categoryCode = "DOGLOVE_CODE";

    /**
     * methodName : list
     * author : Nayoung Yeo
     * description : 게시판 목록을 보여줌(임시)
     *
     * @return string
     */
    @GetMapping
    public String list() {
        return "community/list";
    }

    /**
     *
     * Controller 메서드에 데이터 제공
     *
     * @param dogloveService the doglove service
     */
    public DogloveController(DogloveService dogloveService) {
        this.dogloveService = dogloveService;

    }

    /**
     * methodName : doglovelist
     * author : Nayoung Yeo
     * description : 게시판 목록을 보여주며 (작성자, 제목, 내용, 날짜, 추천순) 추천순과 최신순 정렬
     *
     * @param sort 정렬 기준을 나타냄 'recommendCount'인 경우 추천 수 기준으로 정렬하고 아닐 경우 최신순 정렬
     * @param model
     * @return 게시판 목록을 보여주는 뷰 이름 반환
     */

    @GetMapping("/doglove")
    public String dogloveList(
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            Model model) {

        List<DogloveDTO> dogloves;
        if ("recommendCount".equals(sort)) {
            dogloves = dogloveService.findAllSortedByRecommendation();
        } else {
            dogloves = dogloveService.findAllSortedByDate();
        }

        model.addAttribute("dogloves", dogloves);
        model.addAttribute("sort", sort);
        return "community/doglovelist";
    }

    /**
     * methodName : dogloveDetail
     * author : Nayoung Yeo
     * description : 상세 페이지를 보여줌 작성자, 이미지, 제목, 내용, 댓글, 추천순을 보여줌
     * 댓글 작성 가능(dogloveNo을 가져오고 커뮤니티 카테고리 코드를 하드코딩하여 가져옴
     * @param dogloveNo 상세 페이지의 게시글 No
     * @param model
     * @return 게시글을 찾을 수 없으면 게시판 목록 페이지로 redirect
     */
    @GetMapping("/doglove/{dogloveNo}")
    public String dogloveDetail(@PathVariable("dogloveNo") Long dogloveNo, Model model) {
        DogloveDTO doglove = dogloveService.findById(dogloveNo);
        List<ReplyDTO> reply = replyService.getReplyByForeignNo(replyCategoryCode, Math.toIntExact(dogloveNo));

        System.out.println(uploadService.uploadSelect(IMAGE_CODE, dogloveNo));
        if (doglove != null) {
            model.addAttribute("doglove", doglove);
            model.addAttribute("reply", reply);
            return "community/dogloveDetail";
        } else {
            return "redirect:/community/doglove";
        }
    }

    /*
     * methodName : dogloveCreateForm
     * author : Nayoung Yeo
     * description : 글 생성 페이지를 보여줌
     */
    @GetMapping("/doglove/create")
    public String dogloveCreateForm() {
        return "community/doglovecreate";
    }

    /**
     * methodName : savePost
     * author : Nayoung Yeo
     * description : 게시글 저장 (제목, 내용, 이미지 업로드)
     *
     * @param imageFiles  업로드 파일 목록 / 여러 개 선택 가능
     * @param dogloveDTO 게시글 정보
     * @param principal  현재 로그인한 사용자 정보
     */
    @PostMapping("/doglove")
    public String savePost(@ModelAttribute DogloveDTO dogloveDTO,
                           @RequestParam("imageFiles") @Nullable List<MultipartFile> imageFiles,
                           HttpSession session, Principal principal) {

        String userId = principal.getName();
        // String userId = (String) session.getAttribute("USER_ID");
        if (userId == null) {
            return "redirect:/member/login";
        }
        dogloveDTO.setUserId(userId);

        //게시판 카테고리 코드
        dogloveDTO.setCategoryCode(categoryCode);
        dogloveDTO.setCreateTime(LocalDateTime.now());

        UploadDto uploadDto = new UploadDto();
        uploadDto.setForeignNo(dogloveService.dogloveInsert(dogloveDTO));

        uploadDto.setCategoryCode(IMAGE_CODE); // 이미지 카테고리 코드
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (int i = 0; i < imageFiles.size(); i++) {
                uploadDto.setFile(imageFiles.get(i));
                uploadService.uploadInsert(uploadDto);
            }
        }
        return "redirect:/community/doglove";
    }

    /**
     * methodName : recommendPost
     * author : Nayoung Yeo
     * description : 추천순 증가
     *
     * @param dogloveNo
     * @param session
     * @return string
     */
    @PostMapping("/doglove/{dogloveNo}/recommend")
    public String recommendPost(@PathVariable("dogloveNo") Long dogloveNo, HttpSession session) {
        dogloveService.incrementRecommendCount(dogloveNo);
        return "redirect:/community/doglove/" + dogloveNo;
    }
}
