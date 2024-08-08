package com.multi.happytails.authentication.model.dto;

import com.multi.happytails.member.model.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.sql.Timestamp;
import java.util.Collection;

//호출 방법 : @AuthenticationPrincipal CustomUser customUser
//customUser.getId()

@Getter
@Setter
@ToString
public class CustomUser extends User {

    private long no;
    private String id;
    private String pwd;
    private String email;
    private String name;
    private String nickname;
    private String gender;
    private String tel;
    private Timestamp signupDate;
    private Timestamp deleteDate;
    private char deleteAccountFlag;
    private String role;

    public CustomUser(MemberDTO member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getId(), member.getPwd(), authorities);
        setDetails(member);
    }

    private void setDetails(MemberDTO member){
        this.no = member.getNo();
        this.id = member.getId();
        this.pwd = member.getPwd();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.gender = member.getGender();
        this.tel = member.getTel();
        this.signupDate = member.getSignupDate();
        this.deleteDate = member.getDeleteDate();
        this.deleteAccountFlag = member.getDeleteAccountFlag();
        this.role = member.getRole();
    }
}
