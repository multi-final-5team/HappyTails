package com.multi.happytails.member.controller;

import com.multi.happytails.authentication.model.dto.CustomUser;
import com.multi.happytails.authentication.model.service.AuthenticationService;
import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.member.service.MemberService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private AuthenticationService authenticationService;


    @RequestMapping("/login")
    public void login() {
    }

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
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> kakaoData, HttpServletRequest request) {
        String id = kakaoData.get("id");
        String email = kakaoData.get("email");
        String name = kakaoData.get("name");
        String nickname = kakaoData.get("nickname");
        String gender = kakaoData.get("gender");
        String tel = kakaoData.get("tel");

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId("kakao_" + id);
        memberDTO.setPwd(("1234")); // 임시 비밀번호 설정
        memberDTO.setEmail(email);
        memberDTO.setName(name);
        memberDTO.setNickname(nickname);
        memberDTO.setGender(gender); // 선택 동의 항목
        memberDTO.setTel(tel);
        memberDTO.setRole("ROLE_MEMBER");

        // 전화번호(tel) 데이터 처리
        if (tel != null) {
            // 특수문자 제거
            tel = tel.replaceAll("[^0-9]", "");
            // 길이 제한 (예: 15자)
            if (tel.length() > 30) {
                tel = tel.substring(0, 30);
            }
        }

        // 성별 변환
        if ("male".equals(gender)) {
            memberDTO.setGender("M");
        } else if ("female".equals(gender)) {
            memberDTO.setGender("F");
        }

        // 회원이 존재하는지 확인
        MemberDTO member = memberService.findMemberById(memberDTO.getId());

        System.out.println(member + "________________00");
        if (member == null) {
            // 새 회원 등록
            memberService.insertMember(memberDTO);
            member = memberService.findMemberById(memberDTO.getId());
        } else {
            // 기존 회원 정보 업데이트
            memberService.updateMember(memberDTO.getId(), memberDTO);
            member = memberService.findMemberById(memberDTO.getId());
        }

        MemberDTO customUserInfo = new MemberDTO();
        customUserInfo = memberService.findMemberById(member.getId());
        System.out.println(customUserInfo + "=customUserInfo");
        System.out.println(member.getId() + "=getId");

        List<GrantedAuthority> authorities = new ArrayList<>();

        // role 변수를 이용해 권한을 추가합니다.
        if (customUserInfo.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(customUserInfo.getRole()));
        }

        CustomUser customUser = (CustomUser) authenticationService.loadUserByUsername(member.getId());


        // Spring Security 인증 처리
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customUser, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(token);

        // 세션 생성
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        // 인증 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok().body("{\"redirect\": \"/main/main\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Authentication failed\"}");
        }
    }

    @GetMapping("/getCurrentUser")
    @ResponseBody
    public ResponseEntity<MemberDTO> getCurrentUser(@AuthenticationPrincipal CustomUser customUser) {
        if (customUser == null) { // null 체크 추가
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        MemberDTO member = memberService.findMemberById(customUser.getId());
        return ResponseEntity.ok(member);
    }

    @GetMapping("/findUserIdForm")
    public String findUserIdForm() {
        return "member/findUserId";
    }

    @PostMapping("/findUserId")
    public ResponseEntity<?> findUserId(@RequestBody MemberDTO memberDTO) {
        MemberDTO member = memberService.findUserByDetails(memberDTO);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/findUserPwdForm")
    public String findUserPwdForm() {
        return "member/findUserPwd";
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> payload) {
        String id = payload.get("id");
        String email = payload.get("email");
        String newPassword = payload.get("newPassword");

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);
        memberDTO.setEmail(email);

        MemberDTO member = memberService.findUserByDetails(memberDTO);
        if (member != null) {
            memberService.changePassword(id, newPassword);
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/signup")
    public void signup() {

    }

    @PostMapping("/signup")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file, // MultipartFile file은 프로필 이미지 파일을 받기
                                         @RequestParam("id") String id,
                                         @RequestParam("password") String password,
                                         @RequestParam("email") String email,
                                         @RequestParam("name") String name,
                                         @RequestParam("nickname") String nickname,
                                         @RequestParam("gender") String gender,
                                         @RequestParam("tel") String tel) {

        // MemberDTO 객체를 생성 -> 받아온 회원 정보를 설정
        // 데이터를 하나의 객체로 묶어 관리하기 위함
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);
        memberDTO.setPwd(password);
        memberDTO.setEmail(email);
        memberDTO.setName(name);
        memberDTO.setNickname(nickname);
        memberDTO.setGender(gender);
        memberDTO.setTel(tel);
        memberDTO.setRole("ROLE_MEMBER");


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

    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal CustomUser customUser, Principal principal) {
        System.out.println(principal.getName() + "=================88");
        System.out.println(customUser + "====================99");
        if (customUser == null) {
            return "redirect:/member/login";
        }

        MemberDTO member = memberService.findMemberById(customUser.getId());
        List<UploadDto> profileImages = uploadService.uploadSelect("P", member.getNo());

        model.addAttribute("member", member);
        model.addAttribute("profileImage", profileImages.isEmpty() ? null : profileImages.get(0));

        return "member/mypage";
    }

    @GetMapping("/memberinfo")
    public String showMemberInfo(Model model, @AuthenticationPrincipal CustomUser customUser) {
        if (customUser == null) {
            return "redirect:/member/login";
        }
        String userId = customUser.getUsername();
        MemberDTO member = memberService.findMemberById(userId);
        model.addAttribute("member", member);
        return "member/memberinfo";
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

    @PostMapping("/deleteMemberAccount")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal CustomUser customUser, HttpServletRequest request, HttpServletResponse response) {
        if (customUser != null) {
            memberService.deleteMember(customUser.getUsername());

            // 완전한 로그아웃 처리
            new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

            return ResponseEntity.ok().body("계정이 성공적으로 삭제되었습니다.");
        }
        return ResponseEntity.badRequest().body("계정 삭제에 실패했습니다.");
    }
}