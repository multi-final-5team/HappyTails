package com.multi.happytails.main.controller;

import com.multi.happytails.community.model.dto.ChatDogDTO;
import com.multi.happytails.community.model.dto.ConferenceDTO;
import com.multi.happytails.community.model.dto.DogloveDTO;
import com.multi.happytails.community.service.ChatDogService;
import com.multi.happytails.community.service.ConferenceService;
import com.multi.happytails.community.service.DogloveService;
import com.multi.happytails.finddog.service.FindDogService;
import com.multi.happytails.help.service.HelpService;
import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.member.service.MemberService;
import com.multi.happytails.patrol.model.dao.PatrolRecordDAO;
import com.multi.happytails.patrol.model.dto.PatrolDTO;
import com.multi.happytails.patrol.model.dto.PrecordDTO;
import com.multi.happytails.patrol.service.PatrolRecordService;
import com.multi.happytails.patrol.service.PatrolService;
import com.multi.happytails.shop.model.dto.SalesGoodsDTO;
import com.multi.happytails.shop.service.SalesService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.multi.happytails.main.controller
 * fileName       : AdminController
 * author         : EUN SOO
 * date           : 2024-08-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-16        EUN SOO       최초 생성
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private DogloveService dogloveService;

    @Autowired
    private FindDogService findDogService;

    @Autowired
    private ChatDogService chatDogService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private HelpService helpService;

    @Autowired
    private PatrolRecordService patrolRecordService;

    @Autowired
    private PatrolService patrolService;

    @Autowired
    private MemberService memberService;


    @RequestMapping("/index")
    public String adminMain(Principal principal) {
        if (principal == null || !principal.getName().equals("admin")) {
            return "redirect:/member/login";
        }
        return "/admin/index";
    }

    @RequestMapping("/tables2")
    public void test() {}

    @RequestMapping("/tables4")
    public void test2() {}

    @RequestMapping("/patrolAdmin")
    public void patrolAdmin(Model model) {
        List<PatrolDTO> list = patrolService.findAllPatrol();

        for(PatrolDTO dto : list){
            MemberDTO memberDTO = memberService.findMemberByUserNo(dto.getUserNo());

            dto.setUserId(memberDTO.getId());
        }

        model.addAttribute("patrolList",list);
    }

    @RequestMapping("/patrolRecordAdmin")
    public void patrolRecordAdmin(Model model) {
        List<PrecordDTO> list = patrolRecordService.findAllPatrolRecord();

        for(PrecordDTO dto : list){
            MemberDTO memberDTO = memberService.findMemberByUserNo(dto.getUserNo());

            String userName = memberDTO.getName();

            dto.setUserId(memberDTO.getId());
            dto.setUserName(userName);
        }

        model.addAttribute("precordList",list);
    }

    @RequestMapping("/salesListAdmin")
    public String salesListAdmin(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 88888888;
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

        return "/admin/salesListAdmin";
    }

    @RequestMapping("/dogLoveAdmin")
    public String dogLoveAdmin(  @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "99") int size,
                                 @RequestParam(value = "sort", defaultValue = "date") String sort,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 Model model) {

        Page<DogloveDTO> doglovePage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            doglovePage = dogloveService.dlsearch(keyword.trim(), page - 1, size, sort);
        } else {
            // 검색어가 없는 경우
            if ("recommendCount".equals(sort)) {
                doglovePage = dogloveService.findAllSortedByRecommendation(page - 1, size);
            } else {
                doglovePage = dogloveService.findAllSortedByDate(page - 1, size);
            }
        }

        Map<Long, List<UploadDto>> dogloveImages = new HashMap<>();
        for (DogloveDTO doglove : doglovePage.getContent()) {
            List<UploadDto> imageFiles = uploadService.uploadSelect("L", doglove.getDogloveNo());
            dogloveImages.put(doglove.getDogloveNo(), imageFiles);
        }

        model.addAttribute("dogloves", doglovePage.getContent());
        model.addAttribute("dogloveImages", dogloveImages);
        model.addAttribute("sort", sort);
        model.addAttribute("currentPage", doglovePage.getNumber() + 1);
        model.addAttribute("totalPages", doglovePage.getTotalPages());
        model.addAttribute("totalItems", doglovePage.getTotalElements());

        return "/admin/dogLoveAdmin";
    }

    @GetMapping("/findDogAdmin")
    public void findDogAdmin(Model model) {
        model.addAttribute("findDogList",findDogService.findDogList());
    }
  
    @RequestMapping("/memberRoleAdmin")
    public void memberRoleAdmin(Model model, @PageableDefault(size = 100000) Pageable pageable) {
        MemberDTO memberDTO = new MemberDTO();

        Page<MemberDTO> list = memberService.findAllMember(memberDTO,pageable);

        List<MemberDTO> content = list.getContent();

        model.addAttribute("memberList",content);
    }

    @RequestMapping("/chatdogAdmin")
    public String chatdogAdmin(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "99") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        Page<ChatDogDTO> chatdogPage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            chatdogPage = chatDogService.cdsearch(keyword.trim(), page - 1, size, sort);
        } else {
            // 검색어가 없는 경우
            if ("recommendCount".equals(sort)) {
                chatdogPage = chatDogService.findAllSortedByRecommendation(page - 1, size);
            } else {
                chatdogPage = chatDogService.findAllSortedByDate(page - 1, size);
            }
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("chatdog", chatdogPage.getContent());
        model.addAttribute("sort", sort);
        model.addAttribute("currentPage", chatdogPage.getNumber() + 1);
        model.addAttribute("totalPages", chatdogPage.getTotalPages());
        model.addAttribute("totalItems", chatdogPage.getTotalElements());

        return "/admin/chatdogAdmin";
    }

    @RequestMapping("/conferenceAdmin")
    public String conferenceAdmin(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "99") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        Page<ConferenceDTO> conferencePage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            conferencePage = conferenceService.cfsearch(keyword.trim(), page - 1, size, sort);
        } else {
            // 검색어가 없는 경우
            if ("recommendCount".equals(sort)) {
                conferencePage = conferenceService.findAllSortedByRecommendation(page - 1, size);
            } else {
                conferencePage = conferenceService.findAllSortedByDate(page - 1, size);
            }
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("conference", conferencePage.getContent());
        model.addAttribute("sort", sort);
        model.addAttribute("currentPage", conferencePage.getNumber() + 1);
        model.addAttribute("totalPages", conferencePage.getTotalPages());
        model.addAttribute("totalItems", conferencePage.getTotalElements());

        return "/admin/conferenceAdmin";
    }

    @GetMapping("/questionAdmin")
    public void questionAdminPage(Model model) {
        model.addAttribute("questionList",helpService.questionList(null));
    }

    @GetMapping("/inquiryAdmin")
    public void inquiryAdminPage(Model model) {
        model.addAttribute("inquiryList",helpService.inquiryList());
    }

}
