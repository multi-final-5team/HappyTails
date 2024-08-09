package com.multi.happytails.member.model.dao;

import com.multi.happytails.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDAO {

    MemberDTO findMemberById(String id);

    MemberDTO findUserByDetails(MemberDTO memberDTO);

    void updatePassword(@Param("id") String id, @Param("newPassword") String newPassword);

    void insertMember(MemberDTO member);

    void updateMember(MemberDTO member);

    void deleteMember(String id);

    MemberDTO findMemberByUserNo(int no);
    }
