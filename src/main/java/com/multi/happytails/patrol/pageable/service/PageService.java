package com.multi.happytails.patrol.pageable.service;

import com.multi.happytails.patrol.model.dto.PrecordDTO;
import com.multi.happytails.patrol.pageable.model.dao.BoardDAO;
import com.multi.happytails.patrol.pageable.model.dto.RequestList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
@Service
public class PageService {

    @Autowired
    BoardDAO boardDAO;


    public Page<PrecordDTO> getListPrecord(PrecordDTO dto, Pageable pageable) {

        // 빌더 패턴으로 data, pageable 파라미터에 데이터 주입
        RequestList<?> requestList = RequestList.builder()
                .data(dto)
                .pageable(pageable)
                .build();

        List<PrecordDTO> content = boardDAO.getListPrecord(requestList);
        int total = boardDAO.getListPrecordCount(dto);

        return new PageImpl<>(content, pageable, total);
    }

}
