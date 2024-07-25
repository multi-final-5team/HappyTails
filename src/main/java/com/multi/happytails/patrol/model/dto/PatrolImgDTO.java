package com.multi.happytails.patrol.model.dto;

/**
 * packageName    : com.multi.happytails.patrol.model.dto
 * fileName       : PatrolImgDTO
 * author         : wss18
 * date           : 2024-07-23
 * 설명    : 순찰대 이미지 리스트 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        wss18       최초 생성
 */

import com.multi.happytails.upload.model.dto.UploadDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PatrolImgDTO {
    List<UploadDto> uploadDtos;
    List<PatrolDTO> list;;
}
