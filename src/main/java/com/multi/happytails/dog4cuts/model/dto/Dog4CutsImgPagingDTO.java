package com.multi.happytails.dog4cuts.model.dto;

import com.multi.happytails.upload.model.dto.UploadDto;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : com.multi.happytails.dog4cuts.model.dto
 * fileName       : Dog4CutsImgDTO
 * author         : wss18
 * date           : 2024-08-06
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-06        wss18       최초 생성
 */
@Data
public class Dog4CutsImgPagingDTO {
    List<UploadDto> uploadDtos;
    Page<Dog4CutsDTO> list;;
}
