package com.multi.happytails.patrol.model.dto;



import com.multi.happytails.upload.model.dto.UploadDto;
import lombok.Data;

import java.util.List;

@Data
public class OnePatrolRecordImgDTO {
    List<UploadDto> uploadDtos;
    PrecordDTO precordDTO;
}
