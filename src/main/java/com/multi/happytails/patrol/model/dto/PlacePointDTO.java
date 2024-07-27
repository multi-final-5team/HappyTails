package com.multi.happytails.patrol.model.dto;

import lombok.Data;

/**
 * packageName    : com.multi.happytails.patrol.model.dto
 * fileName       : PlacePointDTO
 * author         : wss18
 * date           : 2024-07-27
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-27        wss18       최초 생성
 */
@Data
public class PlacePointDTO {
    private int precordPlaceHistoryNo;
    private int precordPlaceNo;
    private double latitude;
    private double longitude;
}
