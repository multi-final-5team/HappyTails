package com.multi.happytails.member.service;


import com.multi.happytails.member.model.dao.MemberDAO;
import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.patrol.pageable.model.dto.RequestList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberDTO findMemberById(String id) {
        return memberDAO.findMemberById(id);
    }

    public MemberDTO findMemberByUserNo(int no) {
        return memberDAO.findMemberByUserNo(no);
    }

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
            if (memberDTO.getSignupPathFlag() != '\0') existingMember.setSignupPathFlag(memberDTO.getSignupPathFlag());

            memberDAO.updateMember(existingMember);
        }
    }

    public void deleteMember(String id) {
        memberDAO.deleteMember(id);
    }

    public void recoverAccount(String id) {
        memberDAO.recoverAccount(id);
    }

    public MemberDTO findMemberByEmail(String email) {
        return memberDAO.findMemberByEmail(email);
    }

    public Page<MemberDTO> findAllMember(MemberDTO memberDTO, Pageable pageable){
        // 빌더 패턴으로 data, pageable 파라미터에 데이터 주입
        RequestList<?> requestList = RequestList.builder()
                .data(memberDTO)
                .pageable(pageable)
                .build();

        List<MemberDTO> content = memberDAO.getListMember(requestList);
        int total = memberDAO.getListMemberCount(memberDTO);

        return new PageImpl<>(content, pageable, total);
    }

    public int updateMemberRole(MemberDTO memberDTO) {

        return memberDAO.updateMemberRole(memberDTO);
    }
}
