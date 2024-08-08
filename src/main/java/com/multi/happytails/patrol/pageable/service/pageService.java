package com.multi.happytails.patrol.pageable.service;

import com.multi.happytails.patrol.model.dao.PatrolPlaceDAO;
import com.multi.happytails.patrol.model.dto.PrecordPlaceDTO;
import com.multi.happytails.patrol.pageable.model.dao.BoardDAO;
import com.multi.happytails.patrol.pageable.model.dto.RequestList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.multi.happytails.patrol.pageable.service
 * fileName       : pageService
 * author         : wss18
 * date           : 2024-08-08
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-08        wss18       최초 생성
 */
public class pageService {

    @Autowired
    BoardDAO boardDAO;

    public Page<Map<String, Object>> getListPlace(PrecordPlaceDTO dto, Pageable pageable) {

        // 빌더 패턴으로 data, pageable 파라미터에 데이터 주입
        RequestList<?> requestList = RequestList.builder()
                .data(dto)
                .pageable(pageable)
                .build();

        List<Map<String, Object>> content = boardDAO.getListPlace(requestList);
        int total = boardDAO.getListPlaceCount(dto);

        return new PageImpl<>(content, pageable, total);
    }
}
