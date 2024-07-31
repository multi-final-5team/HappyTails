package com.multi.happytails.member.service;


import com.multi.happytails.member.model.dao.MemberDAO;
import com.multi.happytails.member.model.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.multi.happytails.member.service.MemberService
 * fileName       : MemberService
 * author         : EUN SOO
 * date           : 2024-07-25
 * description    : 회원 관련 비즈니스 로직을 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-25        EUN SOO       최초 생성
 */

@Service
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * methodName : findMemberById
     * author : Eunsoo Lee
     * description : id로 회원 정보 조회
     *
     * @param id 조회할 회원 id
     * @return 조회된 회원 정보를 담은 MemberDTO 객체
     */
    public MemberDTO findMemberById(String id) {
        return memberDAO.findMemberById(id);
    }

    /**
     * methodName : findUserByDetails
     * author : Eunsoo Lee
     * description : 입력된 회원 정보로 회원의 필요한 정보 찾기
     *
     * @param memberDTO 조회할 회원 정보를 담은 MemberDTO 객체
     * @return 조회된 회원 정보를 담은 MemberDTO 객체
     */
    public MemberDTO findUserByDetails(MemberDTO memberDTO) {
        System.out.println(memberDTO);
        MemberDTO memberDTO2 = memberDAO.findUserByDetails(memberDTO);
        System.out.println(memberDTO2);
        return memberDTO2;
    }

    /**
     * methodName : changePassword
     * author : Eunsoo Lee
     * description : 지정된 id의 비밀번호 변경
     *
     * @param id 비밀번호를 변경할 회원의 아이디
     * @param newPassword 새로운 비밀번호 (평문)
     */
    public void changePassword(String id, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        memberDAO.updatePassword(id, encodedPassword);
    }

    /**
     * methodName : insertMember
     * author : Eunsoo Lee
     * description : 회원 등록
     *
     * @param memberDTO 등록할 새로운 회원 정보를 담은 MemberDTO 객체
     */
    public void insertMember(MemberDTO memberDTO) {
        // 비밀번호 암호화
        memberDTO.setPwd(passwordEncoder.encode(memberDTO.getPwd()));
        memberDAO.insertMember(memberDTO);
    }

    /**
     * methodName : isIdDuplicate
     * author : Eunsoo Lee
     * description : 아이디 중복 여부 확인
     *
     * @param id 중복 확인할 id
     * @return 아이디 중복 여부 (true: 중복, false: 사용 가능)
     */
    public boolean isIdDuplicate(String id) {
        return memberDAO.findMemberById(id) != null;
    }

//    public void deleteMember(MemberDTO memberDTO){
//
//    }

    public void updateMemberInfo(String userId, MemberDTO updatedInfo) {
        MemberDTO existingMember = memberDAO.findMemberById(userId);
        if (existingMember != null) {
            // null이 아닌 필드만 업데이트
            if (updatedInfo.getName() != null) existingMember.setName(updatedInfo.getName());
            if (updatedInfo.getAge() != 0) existingMember.setAge(updatedInfo.getAge());
            if (updatedInfo.getGender() != null) existingMember.setGender(updatedInfo.getGender());
            if (updatedInfo.getTel() != null) existingMember.setTel(updatedInfo.getTel());

            memberDAO.updateMember(existingMember);
        }
    }
}
