package com.multi.happytails.member.controller;

import com.multi.happytails.authentication.model.dto.CustomUser;
import com.multi.happytails.authentication.model.service.AuthenticationService;
import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.member.service.MemberService;
import com.multi.happytails.patrol.model.dto.PrecordDTO;
import com.multi.happytails.patrol.pageable.service.PageService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Autowired
    PageService pageService;


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
        String profileImage = kakaoData.get("profile_image");

        System.out.println(kakaoData);

        MemberDTO existingMember = memberService.findMemberByEmail(email);

        if (existingMember != null && existingMember.getSignupPathFlag() != 'K') {
            // 기존 웹 회원이 존재하는 경우
            return ResponseEntity.ok().body("{\"status\": \"INTEGRATION_REQUIRED\", \"message\": \"동일한 이메일 존재. 회원 통합을 해야 이용 가능 합니다. 진행하시겠습니까?\"}");
        }

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId("kakao_" + id);
        memberDTO.setPwd("1234"); // 임시 비밀번호 설정
        memberDTO.setEmail(email);
        memberDTO.setName(name);
        memberDTO.setNickname(nickname);
        memberDTO.setGender("male".equals(gender) ? "M" : "F");
        memberDTO.setTel(tel);
        memberDTO.setSignupPathFlag('K');
        memberDTO.setRole("ROLE_MEMBER");

        if (existingMember == null) {
            // 새 회원 등록
            memberService.insertMember(memberDTO);
            MemberDTO newMember = memberService.findMemberById(memberDTO.getId());
            UploadDto uploadDto = new UploadDto();
            uploadDto.setForeignNo(newMember.getNo());
            uploadDto.setCategoryCode("P");
            uploadDto.setImageName(profileImage);

            uploadService.uploadInsert(uploadDto);

        } else {
            // 기존 카카오 회원 정보 업데이트
            memberService.updateMember(existingMember.getId(), memberDTO);
        }

        MemberDTO customUserInfo = memberService.findMemberById(memberDTO.getId());

        CustomUser customUser = (CustomUser) authenticationService.loadUserByUsername(memberDTO.getId());

        // Spring Security 인증 처리
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(customUserInfo.getRole()));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customUser, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(token);

        // 세션 생성
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        return ResponseEntity.ok().body("{\"redirect\": \"/main/main\"}");
    }


    @PostMapping("/integrateAccount")
    @ResponseBody
    public ResponseEntity<?> integrateAccount(@RequestBody Map<String, String> integrationData, HttpServletRequest request) {
        String email = integrationData.get("email");
        boolean integrate = Boolean.parseBoolean(integrationData.get("integrate"));

        if (integrate) {
            MemberDTO existingMember = memberService.findMemberByEmail(email);
            if (existingMember != null) {
                existingMember.setSignupPathFlag('K');
                memberService.updateMember(existingMember.getId(), existingMember);

                CustomUser customUser = (CustomUser) authenticationService.loadUserByUsername(existingMember.getId());

                // Spring Security 인증 처리
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(existingMember.getRole()));
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customUser, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(token);

                // 세션 생성
                HttpSession session = request.getSession(true);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext());

                return ResponseEntity.ok().body("{\"status\": \"SUCCESS\", \"message\": \"회원통합 성공\", \"redirect\": \"/main/main\"}");
            }
        }

        return ResponseEntity.ok().body("{\"status\": \"FAILURE\", \"message\": \"회원통합 실패. 로그인 페이지로 이동합니다.\", \"redirect\": \"/member/login\"}");
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
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file,
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
        memberDTO.setSignupPathFlag('W');
        memberDTO.setRole("ROLE_MEMBER");

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
    public String myPage(Model model, @AuthenticationPrincipal CustomUser customUser) {

        if (customUser == null) {
            return "redirect:/member/login";
        }

        MemberDTO member = memberService.findMemberById(customUser.getId());
        List<UploadDto> profileImages = uploadService.uploadSelect("P", member.getNo());

        System.out.println(profileImages);
        System.out.println(member);

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

    @GetMapping(value="findAllMember")
    @ResponseBody
    public Page<MemberDTO> findAllPatrol(MemberDTO memberDTO, @PageableDefault(size = 10) Pageable pageable){

        Page<MemberDTO> list = memberService.findAllMember(memberDTO,pageable);

        System.out.println("list >> " + list);

        return list;
    }

    @PostMapping(value="updateMemberRole")
    @ResponseBody
    public String updateMemberRole(@RequestParam("no") int no, @RequestParam("role") String role){

        System.out.println("no >>" + no + " , role >>" + role);

        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setNo(no);
        memberDTO.setRole(role);

        System.out.println("memberDTO >> " + memberDTO);

        memberService.updateMemberRole(memberDTO);

        return "/admin/memberRoleAdmin";
    }
}