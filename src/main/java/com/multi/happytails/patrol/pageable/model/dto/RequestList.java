package com.multi.happytails.patrol.pageable.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

/**
 * packageName    : com.multi.happytails.patrol.pageable.model.dto
 * fileName       : RequestList
 * author         : wss18
 * date           : 2024-08-08
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-08        wss18       최초 생성
 */
@Builder
@Data
public class RequestList<T> {

    private T data;
    private Pageable pageable;
}
