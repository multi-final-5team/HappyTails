package com.multi.happytails.member.controller;

import com.multi.happytails.authentication.model.dto.CustomUser;
import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.member.service.MemberService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * packageName    : com.multi.happytails.member.controller
 * fileName       : MemberController
 * author         : EUN SOO
 * date           : 2024-07-24
 * description    : 회원, 명예의 전당, SNS 공유 관련 기능 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        EUN SOO       최초 생성
 */

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private UploadService uploadService;

    /**
     * methodName : login
     * author : Eunsoo Lee
     * description : 로그인 페이지 표시
     */
    @RequestMapping("/login")
    public void login() {
    }

    /**
     * methodName : logout
     * author : Eunsoo Lee
     * description : 로그아웃 수행 및 로그인 페이지로 redirect
     *
     * @return 로그인 페이지로 이동
     */
    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/member/login";
    }

    @GetMapping("/kakaoLogin")
    public String kakaoLoginPage() {
        return "member/kakaoLogin";
    }

    @PostMapping("/kakaoLogin")
    @ResponseBody
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> kakaoData) {
        String id = kakaoData.get("id");
        String nickname = kakaoData.get("nickname");

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId("kakao_" + id);
        memberDTO.setNickname(nickname);
        memberDTO.setPwd("1234"); // 카카오 로그인은 비밀번호가 없으므로 임시 비밀번호 설정

        // 회원이 존재하는지 확인
        MemberDTO existingMember = memberService.findMemberById(memberDTO.getId());

        if (existingMember == null) {
            // 새 회원 등록
            memberService.insertMember(memberDTO);
        }

        // 로그인 처리 (여기서는 간단히 성공 메시지만 반환)
        return ResponseEntity.ok().body("{\"message\": \"Login successful\"}");
    }

    /**
     * methodName : getCurrentUser
     * author : Eunsoo Lee
     * description : 현재 로그인한 사용자의 정보를 조회
     *
     * @param customUser 현재 로그인된 사용자 객체
     * @return 현재 사용자의 MemberDTO 객체를 포함한 ResponseEntity
     */
    @GetMapping("/getCurrentUser")
    @ResponseBody
    public ResponseEntity<MemberDTO> getCurrentUser(@AuthenticationPrincipal CustomUser customUser) {
        if (customUser == null) { // null 체크 추가
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        MemberDTO member = memberService.findMemberById(customUser.getId());
        return ResponseEntity.ok(member);
    }

    /**
     * methodName : findUserIdForm
     * author : Eunsoo Lee
     * description : 아이디 찾기 페이지 표시
     *
     * @return findUserId.html의 페이지 경로로 이동
     */
    @GetMapping("/findUserIdForm")
    public String findUserIdForm() {
        return "member/findUserId";
    }

    /**
     * methodName : findUserId
     * author : Eunsoo Lee
     * description : 아이디 찾기 기능
     *
     * @param memberDTO 사용자의 입력 정보를 담은 MemberDTO 객체
     * @return 찾은 id 또는 not found 오류 메세지
     */
    @PostMapping("/findUserId")
    public ResponseEntity<?> findUserId(@RequestBody MemberDTO memberDTO) {
        MemberDTO member = memberService.findUserByDetails(memberDTO);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    /**
     * methodName : findUserPwdForm
     * author : Eunsoo Lee
     * description : 비밀번호 찾기 페이지 표시
     *
     * @return findUserPwd.html의 페이지 경로로 이동
     */
    @GetMapping("/findUserPwdForm")
    public String findUserPwdForm() {
        return "member/findUserPwd";
    }

    /**
     * methodName : changePassword
     * author : Eunsoo Lee
     * description : 비밀번호 변경 기능
     *
     * @param payload 사용자의 정보(id, name, tel, newPassword)를 포함한 Map
     * @return 비밀번호 변경 성공 여부에 대한 Response
     */
    @PostMapping("/changePassword")
    @ResponseBody
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> payload) {
        String id = payload.get("id");
        String name = payload.get("name");
        String tel = payload.get("tel");
        String newPassword = payload.get("newPassword");

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);
        memberDTO.setName(name);
        memberDTO.setTel(tel);

        MemberDTO member = memberService.findUserByDetails(memberDTO);
        if (member != null) {
            memberService.changePassword(id, newPassword);
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    /**
     * methodName : signup
     * author : Eunsoo Lee
     * description : 회원가입 페이지 표시
     */
    @GetMapping("/signup")
    public void signup() {

    }

    /**
     * methodName : handleFileUpload
     * author : Eunsoo Lee
     * description : 회원가입 정보와 프로필 이미지 처리 및 저장
     *
     * @param file 업로드된 프로필 이미지 파일
     * @param id 사용자 아이디
     * @param password 사용자 비밀번호
     * @param name 사용자 이름
     * @param nickname 사용자 닉네임
     * @param age 사용자 나이
     * @param gender 사용자 성별
     * @param tel 사용자 전화번호
     * @return 로그인 페이지로의 리다이렉트를 포함한 ModelAndView
     */
    @PostMapping("/signup")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file, // MultipartFile file은 프로필 이미지 파일을 받기
                                         @RequestParam("id") String id,
                                         @RequestParam("password") String password,
                                         @RequestParam("name") String name,
                                         @RequestParam("nickname") String nickname,
                                         @RequestParam("age") int age,
                                         @RequestParam("gender") String gender,
                                         @RequestParam("tel") String tel) {

        // MemberDTO 객체를 생성 -> 받아온 회원 정보를 설정
        // 데이터를 하나의 객체로 묶어 관리하기 위함
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);
        memberDTO.setPwd(password);
        memberDTO.setName(name);
        memberDTO.setNickname(nickname);
        memberDTO.setAge(age);
        memberDTO.setGender(gender);
        memberDTO.setTel(tel);

        System.out.println(memberDTO);

//            // 회원 정보를 db에 저장
            memberService.insertMember(memberDTO);

            // 회원 번호 이름, 전화번호 받아옴(저장한 회원 정보를 다시 조회)
            // 이는 db에서 자동 생성된 회원 번호(no)를 얻기 위함
            MemberDTO memberDTO2 = new MemberDTO();
            memberDTO2 = memberService.findUserByDetails(memberDTO);
            // 업로드 dto 객체 생성
            UploadDto uploadDto = new UploadDto();
            uploadDto.setFile(file);
            uploadDto.setCategoryCode("P"); // P : 프로필 이미지
            uploadDto.setForeignNo(memberDTO2.getNo());

            uploadService.uploadInsert(uploadDto); // 회원 정보와 프로필 이미지를 연결

            return new ModelAndView("redirect:/member/login");

    }

    /**
     * methodName : checkIdDuplicate
     * author : Eunsoo Lee
     * description : 아이디 중복 확인 기능
     *
     * @param id 파라미터 요청으로 dto에서 id 가져옴
     * @return 아이디 중복 여부에 대한 메시지
     */
    @PostMapping("/checkIdDuplicate")
    @ResponseBody
    public ResponseEntity<String> checkIdDuplicate(@RequestParam("id") String id) {
        boolean isDuplicate = memberService.isIdDuplicate(id);
        if (isDuplicate) {
            return ResponseEntity.ok("중복된 아이디입니다.");
        }
        else {
            return ResponseEntity.ok("사용 가능한 아이디입니다.");
        }
    }

    /**
     * methodName : mypage
     * author : Eunsoo Lee
     * description : 마이페이지 표시
     */
    @GetMapping("/mypage")
    public void mypage(){

    }


//    @PostMapping("/delete")
//    public ResponseEntity<String> deleteAccount() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUser customUser = (CustomUser) authentication.getPrincipal();
//        memberService.deleteMember(customUser.getId());
//        return ResponseEntity.ok("Account deleted successfully.");
//    }

    @GetMapping("/updateForm")
    public String showUpdateForm(@AuthenticationPrincipal CustomUser customUser, Model model) {
        if (customUser == null) {
            return "redirect:/member/login";
        }
        MemberDTO member = memberService.findMemberById(customUser.getId());
        model.addAttribute("member", member);
        return "member/updateForm";
    }

    /*
    @PostMapping("/memberinfo")
    public ResponseEntity<?> updateMemberInfo(@AuthenticationPrincipal CustomUser customUser, @RequestBody MemberDTO updatedInfo) {
        if (customUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        memberService.updateMemberInfo(customUser.getId(), updatedInfo);
        return ResponseEntity.ok().build();
    }
     */

    @GetMapping("/memberinfo")
    public String showMemberInfo(Model model, @AuthenticationPrincipal CustomUser customUser) {
        if (customUser != null) {
            String userId = customUser.getUsername();
            MemberDTO member = memberService.findMemberById(userId);
            model.addAttribute("member", member);
            return "member/memberinfo";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/memberinfo")
    public String updateMemberInfo(@ModelAttribute MemberDTO memberDTO, @AuthenticationPrincipal CustomUser customUser) {
        if (customUser != null) {
            String userId = customUser.getUsername();
            memberService.updateMember(userId, memberDTO);
            return "redirect:/member/mypage";
        } else {
            return "redirect:/login";
        }
    }


}