package com.multi.happytails.member.model.dao;


import com.multi.happytails.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : com.multi.happytails.member.model.dao.MemberDAO
 * fileName       : MemberDAO
 * author         : EUN SOO
 * date           : 2024-07-25
 * description    : 회원 정보에 대한 DB 접근 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-25        EUN SOO       최초 생성
 */

@Mapper
public interface MemberDAO {

    /**
     * methodName : findMemberById
     * author : Eunsoo Lee
     * description : id로 회원 정보 조회
     *
     * @param memberId 조회할 회원 id
     * @return 조회된 회원 정보를 담은 MemberDTO 객체, 없으면 null
     */
    MemberDTO findMemberById(String memberId);

    /**
     * methodName : findUserByDetails
     * author : Eunsoo Lee
     * description : 입력된 회원 정보로 회원의 필요한 정보 찾기
     *
     * @param memberDTO 조회할 회원 정보를 담은 MemberDTO 객체
     * @return 조회된 회원 정보를 담은 MemberDTO 객체, 없으면 null
     */
    MemberDTO findUserByDetails(MemberDTO memberDTO);

    /**
     * methodName : updatePassword
     * author : Eunsoo Lee
     * description : 지정된 id의 비밀번호 update
     *
     * @param id 비밀번호 변경할 회원의 id
     * @param newPassword 새로운 비밀번호 (암호화된 상태)
     */
    void updatePassword(@Param("id") String id, @Param("newPassword") String newPassword);

    /**
     * methodName : insertMember
     * author : Eunsoo Lee
     * description : 새로운 회원 정보를 DB에 insert
     *
     * @param member 등록할 새로운 회원 정보를 담은 MemberDTO 객체
     */
    void insertMember(MemberDTO member);

    void updateMember(MemberDTO member);

    }
