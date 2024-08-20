package com.multi.happytails.member.model.dao;

import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.patrol.pageable.model.dto.RequestList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberDAO {

    MemberDTO findMemberById(String id);

    MemberDTO findUserByDetails(MemberDTO memberDTO);

    void updatePassword(@Param("id") String id, @Param("newPassword") String newPassword);

    void insertMember(MemberDTO member);

    void updateMember(MemberDTO member);

    void deleteMember(String id);

    MemberDTO findMemberByUserNo(int no);

    MemberDTO findMemberByEmail(String email);


    List<MemberDTO> getListMember(RequestList<?> requestList);

    int getListMemberCount(MemberDTO memberDTO);

    int updateMemberRole(MemberDTO memberDTO);
}
