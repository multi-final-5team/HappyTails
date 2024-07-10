package com.multi.happytails.member.model.dto;

import java.sql.Timestamp;
import java.util.List;

public class MemberDTO {

    private int no;
    private String id;
    private String pwd;
    private String name;
    private String profileIMG;
    private String nickname;
    private int age;
    private String gender;
    private String tel;
    private int mannerCount;
    private int banCount;
    private Timestamp signupDate;
    private Timestamp deleteDate;
    private String deleteAccount;
    private String role;

    private List<MemberDTO> memberRoleList;

    public List<MemberDTO> getMemberRoleList() {
        return memberRoleList;
    }

    public void setMemberRoleList(List<MemberDTO> memberRoleList) {
        this.memberRoleList = memberRoleList;
    }

    public MemberDTO() {

    }

    public MemberDTO(int no, String id, String pwd, String name, String profileIMG, String nickname, int age, String gender, String tel, int mannerCount, int banCount, Timestamp signupDate, Timestamp deleteDate, String deleteAccount, String role) {
        this.no = no;
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.profileIMG = profileIMG;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.tel = tel;
        this.mannerCount = mannerCount;
        this.banCount = banCount;
        this.signupDate = signupDate;
        this.deleteDate = deleteDate;
        this.deleteAccount = deleteAccount;
        this.role = role;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileIMG() {
        return profileIMG;
    }

    public void setProfileIMG(String profileIMG) {
        this.profileIMG = profileIMG;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getMannerCount() {
        return mannerCount;
    }

    public void setMannerCount(int mannerCount) {
        this.mannerCount = mannerCount;
    }

    public int getBanCount() {
        return banCount;
    }

    public void setBanCount(int banCount) {
        this.banCount = banCount;
    }

    public Timestamp getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Timestamp signupDate) {
        this.signupDate = signupDate;
    }

    public Timestamp getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Timestamp deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getDeleteAccount() {
        return deleteAccount;
    }

    public void setDeleteAccount(String deleteAccount) {
        this.deleteAccount = deleteAccount;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", profileIMG='" + profileIMG + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", tel='" + tel + '\'' +
                ", mannerCount=" + mannerCount +
                ", banCount=" + banCount +
                ", signupDate=" + signupDate +
                ", deleteDate=" + deleteDate +
                ", deleteAccount='" + deleteAccount + '\'' +
                ", role='" + role + '\'' +
                ", memberRoleList=" + memberRoleList +
                '}';
    }
}
