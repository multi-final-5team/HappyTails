package com.multi.happytails.member.service;


import com.multi.happytails.member.model.dao.MemberDAO;
import com.multi.happytails.member.model.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberDTO findMemberById(String id) {
        System.out.println("________________33");
        return memberDAO.findMemberById(id);
    }

    //넘버 > 유저정보 필요해서 임의로 작성
    public MemberDTO findMemberByUserNo(int no) {
        return memberDAO.findMemberByUserNo(no);
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

    public void changePassword(String id, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        memberDAO.updatePassword(id, encodedPassword);
    }

    public void insertMember(MemberDTO memberDTO) {
        // 비밀번호 암호화
        memberDTO.setPwd(passwordEncoder.encode(memberDTO.getPwd()));
        memberDAO.insertMember(memberDTO);
    }

    public boolean isIdDuplicate(String id) {
        return memberDAO.findMemberById(id) != null;
    }

    public void updateMember(String userId, MemberDTO memberDTO) {
        MemberDTO existingMember = memberDAO.findMemberById(userId);
        if (existingMember != null) {
            if (memberDTO.getEmail() != null) existingMember.setEmail(memberDTO.getEmail());
            if (memberDTO.getName() != null) existingMember.setName(memberDTO.getName());
            if (memberDTO.getNickname() != null) existingMember.setNickname(memberDTO.getNickname());
            if (memberDTO.getGender() != null) existingMember.setGender(memberDTO.getGender());
            if (memberDTO.getTel() != null) existingMember.setTel(memberDTO.getTel());

            memberDAO.updateMember(existingMember);
        }
    }

    public void deleteMember(String id) {
        memberDAO.deleteMember(id);
    }
}
