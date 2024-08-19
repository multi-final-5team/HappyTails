package com.multi.happytails.patrol.pageable.service;

import com.multi.happytails.dog4cuts.model.dto.Dog4CutsDTO;
import com.multi.happytails.patrol.model.dto.PatrolDTO;
import com.multi.happytails.patrol.model.dto.PrecordDTO;
import com.multi.happytails.patrol.model.dto.PrecordPlaceDTO;
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

    public Page<PatrolDTO> getListPatrol(PatrolDTO dto, Pageable pageable) {

        // 빌더 패턴으로 data, pageable 파라미터에 데이터 주입
        RequestList<?> requestList = RequestList.builder()
                .data(dto)
                .pageable(pageable)
                .build();

        List<PatrolDTO> content = boardDAO.getListPatrol(requestList);
        int total = boardDAO.getListPatrolCount(dto);

        return new PageImpl<>(content, pageable, total);
    }

    public Page<PrecordPlaceDTO> getListPrecordPlace(PrecordPlaceDTO dto, Pageable pageable) {

        // 빌더 패턴으로 data, pageable 파라미터에 데이터 주입
        RequestList<?> requestList = RequestList.builder()
                .data(dto)
                .pageable(pageable)
                .build();

        List<PrecordPlaceDTO> content = boardDAO.getListPrecordPlace(requestList);
        int total = boardDAO.getListPrecordPlaceCount(dto);

        return new PageImpl<>(content, pageable, total);
    }



    public Page<Dog4CutsDTO> getListDog4Cuts(Dog4CutsDTO dto, Pageable pageable) {

        // 빌더 패턴으로 data, pageable 파라미터에 데이터 주입
        RequestList<?> requestList = RequestList.builder()
                .data(dto)
                .pageable(pageable)
                .build();

        List<Dog4CutsDTO> content = boardDAO.getListDog4Cuts(requestList);
        int total = boardDAO.getListDog4CutsCount(dto);

        return new PageImpl<>(content, pageable, total);
    }

}
