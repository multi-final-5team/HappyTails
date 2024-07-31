package com.multi.happytails.patrol.model.dto;

import lombok.Data;

import java.util.List;

/**
 * packageName    : com.multi.happytails.patrol.model.dto
 * fileName       : PlacePoointListDTO
 * author         : wss18
 * date           : 2024-07-27
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-27        wss18       최초 생성
 */
@Data
public class PlacePointListDTO {
    private List<PrecordPlaceHistoryDTO> pointList;
}
