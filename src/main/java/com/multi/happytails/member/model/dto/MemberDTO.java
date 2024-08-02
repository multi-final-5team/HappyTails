package com.multi.happytails.member.model.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class MemberDTO {

    private int no;
    private String id;
    private String pwd;
    private String email;
    private String name;
    private String nickname;
    private String gender;
    private String tel;
    private Timestamp signupDate;
    private Timestamp deleteDate;
//    private String deleteAccount;
    private char deleteAccountFlag;
    private String role;
    private String profileImage;
    private String birthday;
    private String birthyear;

//    private List<MemberDTO> memberRoleList;
//
//    public List<MemberDTO> getMemberRoleList() {
//        return memberRoleList;
//    }
//
//    public void setMemberRoleList(List<MemberDTO> memberRoleList) {
//        this.memberRoleList = memberRoleList;
//    }

}
