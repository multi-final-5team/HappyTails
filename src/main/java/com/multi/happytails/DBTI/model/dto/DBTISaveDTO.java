package com.multi.happytails.DBTI.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * packageName    : com.multi.happytails.DBTI.model.dto
 * fileName       : DBTIDTO
 * author         : User
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        User       최초 생성
 */
@Getter
@Setter
public class DBTISaveDTO {
    private String name;
    private String breed;
    private LocalDate birthdate;
    private String gender;

}
